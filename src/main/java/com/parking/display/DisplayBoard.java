package com.parking.display;

import com.parking.models.AvailabilityModel;
import com.parking.service.ParkingService;

public class DisplayBoard {

    private final ParkingService parkingService;

    public DisplayBoard(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

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
