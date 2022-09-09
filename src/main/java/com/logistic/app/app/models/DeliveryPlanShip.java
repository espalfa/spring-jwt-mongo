package com.logistic.app.app.models;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class DeliveryPlanShip {

    @Id
    private String id;
    private String productType;
    private int cant;
    private Date registerDate;
    private Date deliveryDate;
    private String port;
    private BigDecimal price;
    private String fleetNum;
    private String trackingNumber;
    private BigDecimal discount;
    
}
