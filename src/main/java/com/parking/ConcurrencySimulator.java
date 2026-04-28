package com.parking;

import com.parking.enums.VehicleType;
import com.parking.models.Vehicle;
import com.parking.service.ParkingService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencySimulator {

    private static final String[][] TEST_VEHICLES = {
        {"MH04AB1111", "CAR"},
        {"MH04AB2222", "MOTORCYCLE"},
        {"MH04AB3333", "BUS"},
        {"MH04AB4444", "CAR"},
        {"MH04AB5555", "CAR"},
        {"MH04AB6666", "MOTORCYCLE"}
    };

    public static void run(ParkingService service) throws InterruptedException {
        System.out.println("\n══════════════════════════════════════════════════");
        System.out.println("  CONCURRENCY TEST — " + TEST_VEHICLES.length + " simultaneous check-ins");
        System.out.println("══════════════════════════════════════════════════");

        ExecutorService pool  = Executors.newFixedThreadPool(TEST_VEHICLES.length);
        CountDownLatch  latch = new CountDownLatch(TEST_VEHICLES.length);

        for (String[] v : TEST_VEHICLES) {
            pool.submit(() -> {
                try {
                    VehicleType type    = VehicleType.valueOf(v[1]);
                    Vehicle     vehicle = new Vehicle(v[0], type);
                    service.checkIn(vehicle);
                } catch (Exception e) {
                    System.out.println("Thread error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        pool.shutdown();

        System.out.println("\nConcurrency test done. Active tickets: " + service.getActiveCount());
    }
}
