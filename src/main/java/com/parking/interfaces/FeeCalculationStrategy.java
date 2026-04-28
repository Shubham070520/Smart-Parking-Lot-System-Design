package com.parking.interfaces;

import com.parking.enums.VehicleType;


public interface FeeCalculationStrategy {

    double calculateFee(VehicleType vehicleType, long durationMinutes);

        String describe();
}
