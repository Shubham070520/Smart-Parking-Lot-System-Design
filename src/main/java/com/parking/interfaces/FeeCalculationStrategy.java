package com.parking.interfaces;

import com.parking.enums.VehicleType;

/**
 * Strategy interface for parking fee calculation.
 * Implement this to define different pricing models
 * (hourly flat, tiered, daily cap, etc.) without
 * modifying ParkingService.
 */
public interface FeeCalculationStrategy {
    /**
     * @param vehicleType     type of the vehicle being charged
     * @param durationMinutes total parked duration in minutes
     * @return calculated fee in INR (₹)
     */
    double calculateFee(VehicleType vehicleType, long durationMinutes);

    /** Human-readable description of this pricing model. */
    String describe();
}
