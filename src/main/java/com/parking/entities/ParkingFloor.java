package com.parking.entities;

import com.parking.enums.SpotStatus;
import com.parking.enums.SpotType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {

    private final int              floorNumber;
    private final List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber, int smallCount, int mediumCount, int largeCount) {
        this.floorNumber = floorNumber;
        this.spots       = new ArrayList<>();
        initSpots(smallCount, mediumCount, largeCount);
    }

    private void initSpots(int small, int medium, int large) {
        int idx = 1;
        for (int i = 0; i < small;  i++)
            spots.add(new ParkingSpot(String.format("F%d-SM%03d", floorNumber, idx++), floorNumber, SpotType.SMALL));
        for (int i = 0; i < medium; i++)
            spots.add(new ParkingSpot(String.format("F%d-MD%03d", floorNumber, idx++), floorNumber, SpotType.MEDIUM));
        for (int i = 0; i < large;  i++)
            spots.add(new ParkingSpot(String.format("F%d-LG%03d", floorNumber, idx++), floorNumber, SpotType.LARGE));
    }

    public Optional<ParkingSpot> findAndOccupySpot(SpotType type) {
        return spots.stream()
                .filter(s -> s.getType() == type && s.getStatus() == SpotStatus.AVAILABLE)
                .filter(ParkingSpot::tryOccupy)
                .findFirst();
    }

    public long countAvailable(SpotType type) {
        return spots.stream()
                .filter(s -> s.getType() == type && s.getStatus() == SpotStatus.AVAILABLE)
                .count();
    }

    public int              getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getSpots()      { return Collections.unmodifiableList(spots); }
}
