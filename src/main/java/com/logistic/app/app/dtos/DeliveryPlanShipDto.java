package com.logistic.app.app.dtos;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class DeliveryPlanShipDto {
    
    private String id;
    private String productType;
    private int cant;
    private Date registerDate;
    private Date deliveryDate;
    private String port;
    private BigDecimal price;
    private String fleetNum;
    @Pattern(regexp = "[a-zA-Z]{3}\\d{4}")
    private String trackingNumber;
    private BigDecimal discount;
}
