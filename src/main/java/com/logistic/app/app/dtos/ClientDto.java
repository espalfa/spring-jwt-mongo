package com.logistic.app.app.dtos;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public class ClientDto {
    private String id;
    @NonNull
    private String name;
    private List<DeliveryPlanLandDto> deliveriesLand;
    private List<DeliveryPlanShipDto> deliveriesShip;

    public ClientDto(String id, String name, List<DeliveryPlanLandDto> deliveriesLand,
            List<DeliveryPlanShipDto> deliveriesShip) {
        this.id = id;
        this.name = name;
        this.deliveriesLand = deliveriesLand;
        this.deliveriesShip = deliveriesShip;
    }

    public ClientDto(String name, List<DeliveryPlanLandDto> deliveriesLand, List<DeliveryPlanShipDto> deliveriesShip) {
        this.name = name;
        this.deliveriesLand = deliveriesLand;
        this.deliveriesShip = deliveriesShip;
    }

    public ClientDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientDto(String name) {
        this.name = name;
    }

    public ClientDto() {
    }

}
