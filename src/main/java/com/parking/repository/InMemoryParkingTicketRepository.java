package com.parking.repository;

import com.parking.entities.ParkingTicket;
import com.parking.interfaces.IParkingTicketRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of IParkingTicketRepository.
 *
 * Uses ConcurrentHashMap — plate → ticket — as the backing store.
 * All operations are O(1) average.
 *
 * Thread-safety:
 *   ConcurrentHashMap provides safe concurrent reads + writes.
 *   Atomic check-then-act (e.g. "is already parked?") is handled
 *   at the service layer, not here, to keep repos simple.
 */
public class InMemoryParkingTicketRepository implements IParkingTicketRepository {

    // licensePlate (uppercase) → active ParkingTicket
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
