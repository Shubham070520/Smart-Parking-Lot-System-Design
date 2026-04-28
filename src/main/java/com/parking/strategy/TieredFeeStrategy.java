package com.parking.strategy;

import com.parking.enums.VehicleType;
import com.parking.interfaces.FeeCalculationStrategy;

import java.util.Map;

public class TieredFeeStrategy implements FeeCalculationStrategy {

    private static final Map<VehicleType, Double> BASE_RATES = Map.of(
            VehicleType.MOTORCYCLE,  20.0,
            VehicleType.CAR,         40.0,
            VehicleType.BUS,        100.0
    );

    @Override
    public double calculateFee(VehicleType vehicleType, long durationMinutes) {
        if (durationMinutes <= 0) return 0.0;

        double base       = BASE_RATES.getOrDefault(vehicleType, 40.0);
        double hoursLeft  = durationMinutes / 60.0;
        double totalFee   = 0.0;

        // Tier 1: first 2 hours at 1.5×
        double tier1Hours = Math.min(hoursLeft, 2.0);
        totalFee  += tier1Hours * base * 1.5;
        hoursLeft -= tier1Hours;

        // Tier 2: next 4 hours at 1.0×
        if (hoursLeft > 0) {
            double tier2Hours = Math.min(hoursLeft, 4.0);
            totalFee  += tier2Hours * base;
            hoursLeft -= tier2Hours;
        }

        // Tier 3: beyond 6 hours at 0.75×
        if (hoursLeft > 0) {
            totalFee += hoursLeft * base * 0.75;
        }

        return Math.ceil(totalFee);
    }

    @Override
    public String describe() {
        return "Tiered Rate (first 2hrs: 1.5x | next 4hrs: 1x | beyond 6hrs: 0.75x)";
    }
}
