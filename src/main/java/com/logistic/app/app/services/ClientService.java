package com.logistic.app.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logistic.app.app.controllers.errors.ResourceNotFoundException;
import com.logistic.app.app.dtos.ClientDto;
import com.logistic.app.app.dtos.DeliveryPlanLandDto;
import com.logistic.app.app.dtos.DeliveryPlanShipDto;
import com.logistic.app.app.models.Client;
import com.logistic.app.app.models.DeliveryPlanLand;
import com.logistic.app.app.models.DeliveryPlanShip;
import com.logistic.app.app.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ClientDto> getAll() {
        List<ClientDto> items = new ArrayList<ClientDto>();
        List<Client> found = clientRepository.findAll();

        found.forEach(client -> {
            List<DeliveryPlanLandDto> dplanDtoLand = new ArrayList<DeliveryPlanLandDto>();
            List<DeliveryPlanShipDto> dplanDtoShip = new ArrayList<DeliveryPlanShipDto>();
            client.setDeliveriesLand(client.getDeliveriesLand() == null ? new ArrayList<DeliveryPlanLand>() : client.getDeliveriesLand());
            client.setDeliveriesShip(client.getDeliveriesShip() == null ? new ArrayList<DeliveryPlanShip>() : client.getDeliveriesShip());
            client.getDeliveriesLand().forEach(delivery -> {
                DeliveryPlanLandDto post = modelMapper.map((DeliveryPlanLand) delivery, DeliveryPlanLandDto.class);
                dplanDtoLand.add(post);
            });
            client.getDeliveriesShip().forEach(delivery -> {
                DeliveryPlanShipDto post = modelMapper.map((DeliveryPlanShip) delivery, DeliveryPlanShipDto.class);
                dplanDtoShip.add(post);
            });
            items.add(new ClientDto(client.getId(), client.getName(), dplanDtoLand, dplanDtoShip));
        });
        return items;
    }

    public ClientDto createClient(ClientDto cDto) {
        Client newClient = modelMapper.map(cDto, Client.class);
        Client createdClient = clientRepository.insert(newClient);
        return modelMapper.map(createdClient, ClientDto.class);
    }

    public ClientDto updateClient(ClientDto cDto) throws ResourceNotFoundException {
        Optional<Client> existingItemOptional = clientRepository.findById(cDto.getId());
        if (existingItemOptional.isPresent()) {
            Client updatedClient = clientRepository.save(modelMapper.map(cDto, Client.class));
            return modelMapper.map(updatedClient, ClientDto.class);
        } else {
            throw new ResourceNotFoundException("Client with id = " + cDto.getId());
        }
    }

    public boolean deleteClient(ClientDto cDto) {
        Optional<Client> existingItemOptional = clientRepository.findById(cDto.getId());
        if (existingItemOptional.isPresent()) {
            Client existingItem = existingItemOptional.get();
            clientRepository.deleteById(existingItem.getId());
            return true;
        } else {
            throw new ResourceNotFoundException("Client with id = " + cDto.getId());
        }
    }
}
