package com.parking.interfaces;

import com.parking.entities.ParkingSpot;
import com.parking.enums.SpotType;

import java.util.List;
import java.util.Optional;

/**
 * Repository contract for ParkingSpot persistence.
 * The implementation can be in-memory (default) or
 * backed by a real DB via JDBC / JPA.
 */
public interface IParkingSpotRepository {

    /** Persist a new spot (called during system init). */
    void save(ParkingSpot spot);

    /** Find and atomically occupy the first AVAILABLE spot of the given type. */
    Optional<ParkingSpot> findAndOccupySpot(SpotType type, int floorNumber);

    /** Return available spot count grouped by type across all floors. */
    long countAvailable(SpotType type);

    /** Retrieve every spot on a given floor. */
    List<ParkingSpot> findByFloor(int floorNumber);
}
