package com.parking.entities;

import com.parking.enums.TicketStatus;
import com.parking.models.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingTicket {

    private final String        ticketId;
    private final Vehicle       vehicle;
    private final ParkingSpot   spot;
    private final int           floorNumber;
    private final LocalDateTime entryTime;

    private LocalDateTime exitTime;
    private double        fee;
    private TicketStatus  status;

    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot, int floorNumber) {
        this.ticketId    = ticketId;
        this.vehicle     = vehicle;
        this.spot        = spot;
        this.floorNumber = floorNumber;
        this.entryTime   = LocalDateTime.now();
        this.status      = TicketStatus.ACTIVE;
    }

    public void complete(double calculatedFee) {
        this.exitTime = LocalDateTime.now();
        this.fee      = calculatedFee;
        this.status   = TicketStatus.COMPLETED;
    }

    public long getDurationMinutes() {
        LocalDateTime end = (exitTime != null) ? exitTime : LocalDateTime.now();
        return ChronoUnit.MINUTES.between(entryTime, end);
    }

    public String        getTicketId()    { return ticketId; }
    public Vehicle       getVehicle()     { return vehicle; }
    public ParkingSpot   getSpot()        { return spot; }
    public int           getFloorNumber() { return floorNumber; }
    public LocalDateTime getEntryTime()   { return entryTime; }
    public LocalDateTime getExitTime()    { return exitTime; }
    public double        getFee()         { return fee; }
    public TicketStatus  getStatus()      { return status; }
}
