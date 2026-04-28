package com.parking;

import com.parking.entities.ParkingFloor;
import com.parking.interfaces.FeeCalculationStrategy;
import com.parking.interfaces.IParkingSpotRepository;
import com.parking.interfaces.IParkingTicketRepository;
import com.parking.repository.InMemoryParkingSpotRepository;
import com.parking.repository.InMemoryParkingTicketRepository;
import com.parking.service.ParkingService;
import com.parking.service.SpotAllocationService;
import com.parking.strategy.HourlyFeeStrategy;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private static volatile ParkingLot instance;

    private final ParkingService parkingService;

    private ParkingLot() {
        List<ParkingFloor>       floors     = initializeFloors();
        IParkingSpotRepository   spotRepo   = new InMemoryParkingSpotRepository(floors);
        IParkingTicketRepository ticketRepo = new InMemoryParkingTicketRepository();
        FeeCalculationStrategy   feeStrat   = new HourlyFeeStrategy();

        SpotAllocationService allocationSvc = new SpotAllocationService(spotRepo);
        this.parkingService = new ParkingService(allocationSvc, ticketRepo, feeStrat);
    }

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    private List<ParkingFloor> initializeFloors() {
        List<ParkingFloor> floors = new ArrayList<>();
        floors.add(new ParkingFloor(1, 20, 30, 5));
        floors.add(new ParkingFloor(2, 15, 40, 5));
        floors.add(new ParkingFloor(3, 25, 20, 0));
        return floors;
    }

    public ParkingService getParkingService() {
        return parkingService;
    }
}
