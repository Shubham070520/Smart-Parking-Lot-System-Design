package com.parking.strategy;

import com.parking.enums.SpotType;
import com.parking.enums.VehicleType;

import java.util.Map;

public final class SpotTypeMapper {

    private static final Map<VehicleType, SpotType> MAPPING = Map.of(
            VehicleType.MOTORCYCLE, SpotType.SMALL,
            VehicleType.CAR,        SpotType.MEDIUM,
            VehicleType.BUS,        SpotType.LARGE
    );

    private SpotTypeMapper() {}

    public static SpotType forVehicle(VehicleType vehicleType) {
        SpotType type = MAPPING.get(vehicleType);
        if (type == null)
            throw new IllegalArgumentException("No spot mapping defined for: " + vehicleType);
        return type;
    }
}
