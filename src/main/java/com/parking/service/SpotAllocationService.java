package com.parking.service;

import com.parking.entities.ParkingSpot;
import com.parking.enums.SpotType;
import com.parking.enums.VehicleType;
import com.parking.interfaces.IParkingSpotRepository;
import com.parking.models.AvailabilityModel;
import com.parking.strategy.SpotTypeMapper;

import java.util.Optional;

public class SpotAllocationService {

    private final IParkingSpotRepository spotRepository;

    public SpotAllocationService(IParkingSpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    public ParkingSpot allocate(VehicleType vehicleType) {
        SpotType required = SpotTypeMapper.forVehicle(vehicleType);

        Optional<ParkingSpot> spot = spotRepository.findAndOccupySpot(required, 1);

        return spot.orElseThrow(() ->
            new RuntimeException("Parking lot is FULL for vehicle type: " + vehicleType)
        );
    }

    public void release(ParkingSpot spot) {
        spot.release();
    }

    public AvailabilityModel getAvailability() {
        return new AvailabilityModel(
            spotRepository.countAvailable(SpotType.SMALL),
            spotRepository.countAvailable(SpotType.MEDIUM),
            spotRepository.countAvailable(SpotType.LARGE)
        );
    }
}
