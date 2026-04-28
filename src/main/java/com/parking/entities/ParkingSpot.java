package com.parking.entities;

import com.parking.enums.SpotStatus;
import com.parking.enums.SpotType;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Core domain entity representing a physical parking spot.
 *
 * Concurrency: each spot owns a ReentrantLock so that concurrent
 * vehicle entries only contend over the same spot — not the entire
 * floor or lot. tryOccupy() is a check-and-set operation that is
 * fully atomic within the lock boundary.
 *
 * Maps to DB table: parking_spots
 *   spot_id      VARCHAR PK
 *   floor_number INT
 *   spot_type    ENUM
 *   status       ENUM  DEFAULT 'AVAILABLE'
 */
public class ParkingSpot {

    private final String spotId;
    private final int    floorNumber;
    private final SpotType type;

    private volatile SpotStatus status;          // volatile for cross-thread visibility
    private final ReentrantLock lock = new ReentrantLock();

    public ParkingSpot(String spotId, int floorNumber, SpotType type) {
        this.spotId      = spotId;
        this.floorNumber = floorNumber;
        this.type        = type;
        this.status      = SpotStatus.AVAILABLE;
    }

    /**
     * Atomically claims this spot.
     * @return true  if successfully occupied by the calling thread
     *         false if already taken by another thread
     */
    public boolean tryOccupy() {
        lock.lock();
        try {
            if (status == SpotStatus.AVAILABLE) {
                status = SpotStatus.OCCUPIED;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /** Releases the spot — called on vehicle check-out. */
    public void release() {
        lock.lock();
        try {
            status = SpotStatus.AVAILABLE;
        } finally {
            lock.unlock();
        }
    }

    public String     getSpotId()      { return spotId; }
    public int        getFloorNumber() { return floorNumber; }
    public SpotType   getType()        { return type; }
    public SpotStatus getStatus()      { return status; }

    @Override
    public String toString() {
        return String.format("ParkingSpot[%s | %s | %s]", spotId, type, status);
    }
}
