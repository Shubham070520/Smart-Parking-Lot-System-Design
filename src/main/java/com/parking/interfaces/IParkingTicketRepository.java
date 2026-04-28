package com.parking.interfaces;

import com.parking.entities.ParkingTicket;

import java.util.Optional;

/**
 * Repository contract for ParkingTicket persistence.
 */
public interface IParkingTicketRepository {

    /** Save or update a ticket. */
    void save(ParkingTicket ticket);

    /** Find the ACTIVE ticket for a given license plate. */
    Optional<ParkingTicket> findActiveByLicensePlate(String licensePlate);

    /** Remove (archive) an active ticket on check-out. */
    void remove(String licensePlate);

    /** Total number of currently active (parked) tickets. */
    int countActive();
}
