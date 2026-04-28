package com.parking.repository;

import com.parking.entities.ParkingTicket;
import com.parking.interfaces.IParkingTicketRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryParkingTicketRepository implements IParkingTicketRepository {

    private final ConcurrentHashMap<String, ParkingTicket> store = new ConcurrentHashMap<>();

    @Override
    public void save(ParkingTicket ticket) {
        store.put(ticket.getVehicle().getLicensePlate(), ticket);
    }

    @Override
    public Optional<ParkingTicket> findActiveByLicensePlate(String licensePlate) {
        return Optional.ofNullable(store.get(licensePlate.toUpperCase().trim()));
    }

    @Override
    public void remove(String licensePlate) {
        store.remove(licensePlate.toUpperCase().trim());
    }

    @Override
    public int countActive() {
        return store.size();
    }
}
