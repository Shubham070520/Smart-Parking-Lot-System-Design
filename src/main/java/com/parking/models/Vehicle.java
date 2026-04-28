package com.parking.models;

import com.parking.enums.VehicleType;

/**
 * Immutable value object representing a vehicle entering the lot.
 * Validates the license plate on construction.
 */
public final class Vehicle {

    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        if (licensePlate == null || licensePlate.isBlank())
            throw new IllegalArgumentException("License plate cannot be empty.");
        if (type == null)
            throw new IllegalArgumentException("Vehicle type must be specified.");

        this.licensePlate = licensePlate.toUpperCase().trim();
        this.type         = type;
    }

    public String      getLicensePlate() { return licensePlate; }
    public VehicleType getType()         { return type; }

    @Override
    public String toString() {
        return String.format("Vehicle[%s | %s]", licensePlate, type);
    }
}
