package com.logistic.app.app.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Client {
    @Id
    private String id;
    private String name;
    private List<DeliveryPlanLand> deliveriesLand;
    private List<DeliveryPlanShip> deliveriesShip;
}
