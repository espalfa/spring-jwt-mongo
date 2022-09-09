package com.logistic.app.app.utils;

public class PlatesRegister {

    public boolean checkVehiclePlate(String plate){
        return plate.matches("[a-zA-Z]{3}\\d{3}");
    }

    public boolean checkFleetNum(String plate){
        return plate.matches("[a-zA-Z]{3}\\d{4}");
    }
    
}
