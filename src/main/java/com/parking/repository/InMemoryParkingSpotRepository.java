package com.parking.repository;

import com.parking.entities.ParkingFloor;
import com.parking.entities.ParkingSpot;
import com.parking.enums.SpotType;
import com.parking.interfaces.IParkingSpotRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InMemoryParkingSpotRepository implements IParkingSpotRepository {

    private final List<ParkingFloor> floors;

    public InMemoryParkingSpotRepository(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    @Override
    public void save(ParkingSpot spot) {
    }

    @Override
    public Optional<ParkingSpot> findAndOccupySpot(SpotType type, int preferredFloor) {
        // Sort: put preferred floor first, then ascending
        List<ParkingFloor> sorted = new ArrayList<>(floors);
        sorted.sort((a, b) -> {
            if (a.getFloorNumber() == preferredFloor) return -1;
            if (b.getFloorNumber() == preferredFloor) return 1;
            return Integer.compare(a.getFloorNumber(), b.getFloorNumber());
        });

        for (ParkingFloor floor : sorted) {
            Optional<ParkingSpot> spot = floor.findAndOccupySpot(type);
            if (spot.isPresent()) return spot;
        }
        return Optional.empty();
    }

    @Override
    public long countAvailable(SpotType type) {
        return floors.stream()
                .mapToLong(f -> f.countAvailable(type))
                .sum();
    }

    @Override
    public List<ParkingSpot> findByFloor(int floorNumber) {
        return floors.stream()
                .filter(f -> f.getFloorNumber() == floorNumber)
                .findFirst()
                .map(ParkingFloor::getSpots)
                .orElse(Collections.emptyList());
    }
}
