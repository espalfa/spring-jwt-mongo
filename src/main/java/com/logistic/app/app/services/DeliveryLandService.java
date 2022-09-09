package com.logistic.app.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistic.app.app.controllers.errors.BadRequestException;
import com.logistic.app.app.controllers.errors.InternalServerException;
import com.logistic.app.app.controllers.errors.ResourceNotFoundException;
import com.logistic.app.app.dtos.DeliveryPlanLandDto;
import com.logistic.app.app.models.Client;
import com.logistic.app.app.models.DeliveryPlanLand;
import com.logistic.app.app.repositories.ClientRepository;
import com.logistic.app.app.repositories.DeliveryPlanRepository;
import com.logistic.app.app.utils.AlphaNumericUtil;
import com.logistic.app.app.utils.PlatesRegister;

@Service
public class DeliveryLandService {

    /**
     *
     */
    private static final String DELIVERY_PLAN_LAND_WITH_ID = "Delivery Plan Land with id = ";
    @Autowired
    private DeliveryPlanRepository deliveryRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<DeliveryPlanLandDto> getAll() {
        List<DeliveryPlanLandDto> items = new ArrayList<>();
        List<DeliveryPlanLand> found = deliveryRepository.findAll();

        found.forEach(delivery -> {
            DeliveryPlanLandDto post = modelMapper.map(delivery, DeliveryPlanLandDto.class);
            items.add(post);
        });
        return items;
    }

    public DeliveryPlanLandDto getById(String id) throws ResourceNotFoundException {
        Optional<DeliveryPlanLand> found = deliveryRepository.findById(id);
        try {
            if (!found.isPresent()) {
                throw new ResourceNotFoundException(DELIVERY_PLAN_LAND_WITH_ID + id);
            } else {
                DeliveryPlanLandDto post = modelMapper.map(found.get(), DeliveryPlanLandDto.class);
                return post;
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    public DeliveryPlanLandDto createDeliveryPlanLand(DeliveryPlanLandDto dpDto) throws BadRequestException, InternalServerException {
        try {
            DeliveryPlanLand newDeliveryPlanLand = modelMapper.map(dpDto, DeliveryPlanLand.class);
            if(!checkIfPlateisValid(dpDto.getVehiclePlate())){
                throw new BadRequestException("Vehicle plate must be 3 letters followed by 3 numbers");
            }
            DeliveryPlanLand newDeliveryPlanLandWithTrackingNumber = checkIfTrackingExits(newDeliveryPlanLand);
            DeliveryPlanLand createdDeliveryPlanLand = deliveryRepository.insert(newDeliveryPlanLandWithTrackingNumber);
            updateClient(dpDto, createdDeliveryPlanLand);
            return modelMapper.map(createdDeliveryPlanLand, DeliveryPlanLandDto.class);
        } catch (InternalServerException e) {
            throw new InternalServerException("Internal Server Error");
        }

    }

    private void updateClient(DeliveryPlanLandDto dpDto, DeliveryPlanLand createdDeliveryPlanLand) {
        Optional<Client> client = clientRepository.findById(dpDto.getClientId());
        Client foundClient = client.get();
        List<DeliveryPlanLand> ldpland = foundClient.getDeliveriesLand() == null ? new ArrayList<>() : foundClient.getDeliveriesLand();
        ldpland.add(createdDeliveryPlanLand);
        foundClient.setDeliveriesLand(ldpland);
        clientRepository.save(foundClient);
    }

    private DeliveryPlanLand checkIfTrackingExits(DeliveryPlanLand newDeliveryPlanLand) {
        newDeliveryPlanLand.setTrackingNumber(new AlphaNumericUtil().generateRandomString(10));
        Optional<DeliveryPlanLand> exits = deliveryRepository
                .findByTrackingNumber(newDeliveryPlanLand.getTrackingNumber());
        if (exits.isPresent()) {
            newDeliveryPlanLand.setTrackingNumber(new AlphaNumericUtil().generateRandomString(10));
            checkIfTrackingExits(newDeliveryPlanLand);
        }
        return newDeliveryPlanLand;
    }

    private boolean checkIfPlateisValid(String plate){
        return new PlatesRegister().checkVehiclePlate(plate);
    }

    public DeliveryPlanLandDto updateDeliveryPlanLand(DeliveryPlanLandDto dpDto) throws ResourceNotFoundException {
        Optional<DeliveryPlanLand> existingItemOptional = deliveryRepository.findById(dpDto.getId());
        if (existingItemOptional.isPresent()) {
            if(!checkIfPlateisValid(dpDto.getVehiclePlate())){
                throw new BadRequestException("Vehicle plate must be 3 letters followed by 3 numbers");
            }
            dpDto.setTrackingNumber(existingItemOptional.get().getTrackingNumber());
            DeliveryPlanLand updatedDeliveryPlanLand = deliveryRepository.save(modelMapper.map(dpDto, DeliveryPlanLand.class));
            updateClient(dpDto, updatedDeliveryPlanLand);
            return modelMapper.map(updatedDeliveryPlanLand, DeliveryPlanLandDto.class);
        } else {
            throw new ResourceNotFoundException(DELIVERY_PLAN_LAND_WITH_ID + dpDto.getId());
        }
    }

    public boolean deleteDeliveryPlanLand(DeliveryPlanLandDto dpDto) throws ResourceNotFoundException {
        Optional<DeliveryPlanLand> existingItemOptional = deliveryRepository.findById(dpDto.getId());
        if (existingItemOptional.isPresent()) {
            DeliveryPlanLand existingItem = existingItemOptional.get();
            updateClientDelete(dpDto, existingItem);
            deliveryRepository.deleteById(existingItem.getId());
            return true;
        } else {
            throw new ResourceNotFoundException(DELIVERY_PLAN_LAND_WITH_ID + dpDto.getId());
        }
    }

    private void updateClientDelete(DeliveryPlanLandDto dpDto, DeliveryPlanLand planToRemove) {
        Optional<Client> client = clientRepository.findById(dpDto.getClientId());
        Client foundClient = client.get();
        List<DeliveryPlanLand> ldpland = foundClient.getDeliveriesLand();
        ldpland.remove(planToRemove);
        foundClient.setDeliveriesLand(ldpland);
        clientRepository.save(foundClient);
    }

}
