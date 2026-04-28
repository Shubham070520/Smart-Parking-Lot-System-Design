package com.parking;

import com.parking.display.DisplayBoard;
import com.parking.enums.VehicleType;
import com.parking.models.CheckInResponse;
import com.parking.models.CheckOutResponse;
import com.parking.models.Vehicle;
import com.parking.service.ParkingService;
import com.parking.strategy.TieredFeeStrategy;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // ── Bootstrap ──────────────────────────────────────
        ParkingLot   lot     = ParkingLot.getInstance();
        ParkingService svc   = lot.getParkingService();
        DisplayBoard   board = new DisplayBoard(svc);

        System.out.println("Strategy → " + svc.getFeeStrategy().describe());
        board.display();


        System.out.println("\n─── CHECK-IN PHASE ──────────────────────────");

        CheckInResponse r1 = svc.checkIn(new Vehicle("MH04AA0001", VehicleType.MOTORCYCLE));
        CheckInResponse r2 = svc.checkIn(new Vehicle("MH04BB1234", VehicleType.CAR));
        CheckInResponse r3 = svc.checkIn(new Vehicle("MH04CC5678", VehicleType.CAR));
        CheckInResponse r4 = svc.checkIn(new Vehicle("MH12BUS999", VehicleType.BUS));

        board.display();

        System.out.println("\n─── DUPLICATE ENTRY TEST ─────────────────────");
        try {
            svc.checkIn(new Vehicle("MH04BB1234", VehicleType.CAR));
        } catch (IllegalStateException e) {
            System.out.println("Blocked: " + e.getMessage());
        }

        System.out.println("Simulating parking duration...");
        Thread.sleep(1500);

        System.out.println("\n─── CHECK-OUT (Hourly Rate) ──────────────────");
        CheckOutResponse out1 = svc.checkOut("MH04BB1234");
        System.out.println(out1);

        System.out.println("\n─── SWITCHING TO TIERED FEE STRATEGY ────────");
        svc.setFeeStrategy(new TieredFeeStrategy());

        CheckOutResponse out2 = svc.checkOut("MH12BUS999");
        System.out.println(out2);

        svc.checkOut("MH04AA0001");
        svc.checkOut("MH04CC5678");

        board.display();

        System.out.println("\n─── INVALID CHECK-OUT TEST ───────────────────");
        try {
            svc.checkOut("MH04AA9999");
        } catch (IllegalArgumentException e) {
            System.out.println( e.getMessage());
        }

        ConcurrencySimulator.run(svc);

        board.display();

    }
}
