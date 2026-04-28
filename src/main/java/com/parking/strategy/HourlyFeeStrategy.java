package com.parking.strategy;

import com.parking.enums.VehicleType;
import com.parking.interfaces.FeeCalculationStrategy;

import java.util.Map;

public class HourlyFeeStrategy implements FeeCalculationStrategy {

    private static final Map<VehicleType, Double> HOURLY_RATES = Map.of(
            VehicleType.MOTORCYCLE,  20.0,
            VehicleType.CAR,         40.0,
            VehicleType.BUS,        100.0
    );

    // In HourlyFeeStrategy.java
    public double calculateFee(VehicleType vehicleType, long durationMinutes) {
        if (durationMinutes <= 0) return 0.0;
        double hours = Math.max(1.0, Math.ceil(durationMinutes / 60.0)); // minimum 1 hour
        return hours * HOURLY_RATES.getOrDefault(vehicleType, 40.0);
    }

    @Override
    public String describe() {
        return "Hourly Flat Rate (Bike: Rs.20/hr | Car: Rs.40/hr | Bus: Rs.100/hr)";
    }
}
