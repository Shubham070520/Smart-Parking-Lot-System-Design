package com.parking.entities;

import com.parking.enums.SpotStatus;
import com.parking.enums.SpotType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Domain entity representing a single floor in the parking lot.
 * Owns a list of ParkingSpot objects and exposes spot-finding
 * logic scoped to this floor.
 *
 * Maps to DB view/query: SELECT * FROM parking_spots WHERE floor_number = ?
 */
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

    /**
     * Scans this floor for an available spot of the required type
     * and atomically claims it via ParkingSpot#tryOccupy().
     */
    public Optional<ParkingSpot> findAndOccupySpot(SpotType type) {
        return spots.stream()
                .filter(s -> s.getType() == type && s.getStatus() == SpotStatus.AVAILABLE)
                .filter(ParkingSpot::tryOccupy)
                .findFirst();
    }

    /** Count of AVAILABLE spots of the given type on this floor. */
    public long countAvailable(SpotType type) {
        return spots.stream()
                .filter(s -> s.getType() == type && s.getStatus() == SpotStatus.AVAILABLE)
                .count();
    }

    public int              getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getSpots()      { return Collections.unmodifiableList(spots); }
}
