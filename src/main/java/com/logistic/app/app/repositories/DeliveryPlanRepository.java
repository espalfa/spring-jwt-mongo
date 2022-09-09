package com.logistic.app.app.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.logistic.app.app.models.DeliveryPlanLand;

public interface DeliveryPlanRepository extends MongoRepository<DeliveryPlanLand, String> {
    Optional<DeliveryPlanLand>  findByTrackingNumber(String trackingNumbeString);
    Optional<DeliveryPlanLand> findById(String id);
}
