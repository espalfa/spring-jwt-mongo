package com.logistic.app.app.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.logistic.app.app.models.Client;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findUserByName(String name);
}