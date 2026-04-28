package com.parking.display;

import com.parking.models.AvailabilityModel;
import com.parking.service.ParkingService;

/**
 * Observer-style display board that shows real-time spot availability.
 *
 * In a real deployment this would push updates to a WebSocket endpoint
 * or a message broker (e.g., Kafka topic) consumed by LED boards at
 * each floor entrance.
 */
public class DisplayBoard {

    private final ParkingService parkingService;

    public DisplayBoard(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    /** Print current availability to stdout (simulates a physical display). */
    public void display() {
        AvailabilityModel avail = parkingService.getAvailability();

        System.out.println();
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│         PARKING AVAILABILITY             │");
        System.out.println("├──────────────────┬────────────────────── ┤");
        System.out.printf( "│ 🏍  SMALL  (Bike) │  %-20d│%n", avail.getSmallAvailable());
        System.out.printf( "│ 🚗  MEDIUM (Car)  │  %-20d│%n", avail.getMediumAvailable());
        System.out.printf( "│ 🚌  LARGE  (Bus)  │  %-20d│%n", avail.getLargeAvailable());
        System.out.println("└──────────────────┴──────────────────────┘");
    }
}
