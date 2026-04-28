package com.parking.entities;

import com.parking.enums.TicketStatus;
import com.parking.models.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Domain entity representing a single parking session.
 * Created at check-in; completed (with fee) at check-out.
 *
 * Maps to DB table: parking_tickets
 *   ticket_id     VARCHAR PK
 *   license_plate VARCHAR FK → vehicles
 *   spot_id       VARCHAR FK → parking_spots
 *   entry_time    DATETIME
 *   exit_time     DATETIME  (nullable until checkout)
 *   fee           DECIMAL   (nullable until checkout)
 *   status        ENUM      DEFAULT 'ACTIVE'
 */
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

    /**
     * Closes the ticket by recording exit time and calculated fee.
     * Called exclusively by ParkingService#checkOut().
     */
    public void complete(double calculatedFee) {
        this.exitTime = LocalDateTime.now();
        this.fee      = calculatedFee;
        this.status   = TicketStatus.COMPLETED;
    }

    /** Duration from entry to now (or to exit if completed). */
    public long getDurationMinutes() {
        LocalDateTime end = (exitTime != null) ? exitTime : LocalDateTime.now();
        return ChronoUnit.MINUTES.between(entryTime, end);
    }

    // ── Getters ──────────────────────────────────────────
    public String        getTicketId()    { return ticketId; }
    public Vehicle       getVehicle()     { return vehicle; }
    public ParkingSpot   getSpot()        { return spot; }
    public int           getFloorNumber() { return floorNumber; }
    public LocalDateTime getEntryTime()   { return entryTime; }
    public LocalDateTime getExitTime()    { return exitTime; }
    public double        getFee()         { return fee; }
    public TicketStatus  getStatus()      { return status; }
}
