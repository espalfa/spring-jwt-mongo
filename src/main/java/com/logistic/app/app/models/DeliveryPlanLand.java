package com.logistic.app.app.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class DeliveryPlanLand {

    @Id
    private String id;
    private String productType;
    private int cant;
    private Date registerDate;
    private Date deliveryDate;
    private String warehouse;
    private BigDecimal price;
    private String vehiclePlate;
    private String trackingNumber;
    private BigDecimal discount;
}
