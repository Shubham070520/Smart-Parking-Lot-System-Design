package com.parking.entities;

import com.parking.enums.SpotStatus;
import com.parking.enums.SpotType;

import java.util.concurrent.locks.ReentrantLock;

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
