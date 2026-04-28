package com.parking.service;

import com.parking.entities.ParkingSpot;
import com.parking.entities.ParkingTicket;
import com.parking.interfaces.FeeCalculationStrategy;
import com.parking.interfaces.IParkingTicketRepository;
import com.parking.models.AvailabilityModel;
import com.parking.models.CheckInResponse;
import com.parking.models.CheckOutResponse;
import com.parking.models.Vehicle;

import java.util.concurrent.atomic.AtomicLong;

public class ParkingService {

    private final SpotAllocationService    allocationService;
    private final IParkingTicketRepository ticketRepository;
    private FeeCalculationStrategy         feeStrategy;
    private final AtomicLong               ticketCounter = new AtomicLong(1000);

    public ParkingService(SpotAllocationService allocationService,
                          IParkingTicketRepository ticketRepository,
                          FeeCalculationStrategy feeStrategy) {
        this.allocationService = allocationService;
        this.ticketRepository  = ticketRepository;
        this.feeStrategy       = feeStrategy;
    }

    public CheckInResponse checkIn(Vehicle vehicle) {
        String plate = vehicle.getLicensePlate();

        if (ticketRepository.findActiveByLicensePlate(plate).isPresent()) {
            throw new IllegalStateException(
                plate + " is already parked. Please check out first."
            );
        }

        ParkingSpot spot = allocationService.allocate(vehicle.getType());

        String ticketId = "TKT-" + ticketCounter.getAndIncrement();
        ParkingTicket ticket  = new ParkingTicket(ticketId, vehicle, spot, spot.getFloorNumber());
        ticketRepository.save(ticket);

        System.out.printf("CHECK-IN  | %-15s → Spot %-12s (Floor %d)%n",
                plate, spot.getSpotId(), spot.getFloorNumber());

        return new CheckInResponse(
            ticketId, plate, spot.getSpotId(),
            spot.getFloorNumber(), ticket.getEntryTime().toString()
        );
    }

    public CheckOutResponse checkOut(String licensePlate) {
        String plate = licensePlate.toUpperCase().trim();

        ParkingTicket ticket = ticketRepository
                .findActiveByLicensePlate(plate)
                .orElseThrow(() -> new IllegalArgumentException(
                    plate + " is not currently parked in this lot."
                ));

        long   durationMins = ticket.getDurationMinutes();
        double fee = feeStrategy.calculateFee(ticket.getVehicle().getType(), durationMins);

        ticket.complete(fee);
        allocationService.release(ticket.getSpot());
        ticketRepository.remove(plate);

        System.out.printf("CHECK-OUT | %-15s | %3d mins | Fee: Rs.%.2f%n",
                plate, durationMins, fee);

        return new CheckOutResponse(
            ticket.getTicketId(), plate, ticket.getSpot().getSpotId(),
            ticket.getEntryTime().toString(),
            ticket.getExitTime().toString(),
            durationMins, fee
        );
    }


    public AvailabilityModel getAvailability() {
        return allocationService.getAvailability();
    }

    public void setFeeStrategy(FeeCalculationStrategy strategy) {
        this.feeStrategy = strategy;
        System.out.println("💡 Fee strategy updated → " + strategy.describe());
    }

    public FeeCalculationStrategy getFeeStrategy()
        { return feeStrategy; }
    public int getActiveCount()
        { return ticketRepository.countActive(); }
}
