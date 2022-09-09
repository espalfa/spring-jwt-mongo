package com.logistic.app.app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logistic.app.app.controllers.errors.BadRequestException;
import com.logistic.app.app.controllers.errors.InternalServerException;
import com.logistic.app.app.controllers.errors.ResourceNotFoundException;
import com.logistic.app.app.dtos.DeliveryPlanLandDto;
import com.logistic.app.app.services.DeliveryLandService;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/delivery-land")
public class DeliveryLandController {

    @Autowired
    DeliveryLandService dLandService;

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryPlanLandDto>> getAll() {
        List<DeliveryPlanLandDto> items = new ArrayList<>();
        dLandService.getAll().forEach(items::add);
        if (items.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<DeliveryPlanLandDto> getById(@RequestBody DeliveryPlanLandDto dpDto)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(dLandService.getById(dpDto.getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeliveryPlanLandDto> create(@Valid @RequestBody DeliveryPlanLandDto item)
            throws BadRequestException, InternalServerException {
        DeliveryPlanLandDto savedItem = dLandService.createDeliveryPlanLand(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<DeliveryPlanLandDto> update(@RequestBody DeliveryPlanLandDto item) {
        DeliveryPlanLandDto updatedItem =  dLandService.updateDeliveryPlanLand(item);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<DeliveryPlanLandDto> delete(@RequestBody DeliveryPlanLandDto item) {
        dLandService.deleteDeliveryPlanLand(item);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
