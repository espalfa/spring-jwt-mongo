package com.logistic.app.app.dtos;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class DeliveryPlanLandDto {

    private String id;
    private String clientId;
    private String productType;
    private int cant;
    private Date registerDate;
    private Date deliveryDate;
    private String warehouse;
    private BigDecimal price;
    @Pattern(regexp = "[a-zA-Z]{3}\\d{3}")
    private String vehiclePlate;
    private String trackingNumber;
    private BigDecimal discount;

    public DeliveryPlanLandDto(String productType, int cant, Date registerDate, Date deliveryDate, String warehouse,
            BigDecimal price, String vehiclePlate) {
        this.productType = productType;
        this.cant = cant;
        this.registerDate = registerDate;
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
        this.price = price;
        this.vehiclePlate = vehiclePlate;
    }

    public DeliveryPlanLandDto(String id, String productType, int cant, Date registerDate, Date deliveryDate,
            String warehouse, BigDecimal price, String vehiclePlate, String trackingNumber, BigDecimal discount) {
        this.id = id;
        this.productType = productType;
        this.cant = cant;
        this.registerDate = registerDate;
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
        this.price = price;
        this.vehiclePlate = vehiclePlate;
        this.trackingNumber = trackingNumber;
        this.discount = discount;
    }

    

    public DeliveryPlanLandDto(String id, String clientId, String productType, int cant, Date registerDate,
            Date deliveryDate, String warehouse, BigDecimal price, String vehiclePlate, String trackingNumber,
            BigDecimal discount) {
        this.id = id;
        this.clientId = clientId;
        this.productType = productType;
        this.cant = cant;
        this.registerDate = registerDate;
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
        this.price = price;
        this.vehiclePlate = vehiclePlate;
        this.trackingNumber = trackingNumber;
        this.discount = discount;
    }

    public DeliveryPlanLandDto() {
    }

}
