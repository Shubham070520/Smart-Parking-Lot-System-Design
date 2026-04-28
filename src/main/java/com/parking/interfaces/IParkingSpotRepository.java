package com.parking.interfaces;

import com.parking.entities.ParkingSpot;
import com.parking.enums.SpotType;

import java.util.List;
import java.util.Optional;

public interface IParkingSpotRepository {

    void save(ParkingSpot spot);

        Optional<ParkingSpot> findAndOccupySpot(SpotType type, int floorNumber);
        long countAvailable(SpotType type);
        List<ParkingSpot> findByFloor(int floorNumber);
}
