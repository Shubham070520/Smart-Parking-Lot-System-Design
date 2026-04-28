package com.parking.interfaces;

import com.parking.entities.ParkingTicket;

import java.util.Optional;


public interface IParkingTicketRepository {

    void save(ParkingTicket ticket);

    Optional<ParkingTicket> findActiveByLicensePlate(String licensePlate);

    void remove(String licensePlate);

    int countActive();
}
