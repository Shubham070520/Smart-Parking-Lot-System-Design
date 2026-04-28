package com.parking.models;


public class CheckOutResponse {

    private final String ticketId;
    private final String licensePlate;
    private final String spotId;
    private final String entryTime;
    private final String exitTime;
    private final long   durationMinutes;
    private final double fee;

    public CheckOutResponse(String ticketId, String licensePlate, String spotId,
                            String entryTime, String exitTime,
                            long durationMinutes, double fee) {
        this.ticketId        = ticketId;
        this.licensePlate    = licensePlate;
        this.spotId          = spotId;
        this.entryTime       = entryTime;
        this.exitTime        = exitTime;
        this.durationMinutes = durationMinutes;
        this.fee             = fee;
    }

    public String getTicketId()        { return ticketId; }
    public String getLicensePlate()    { return licensePlate; }
    public String getSpotId()          { return spotId; }
    public String getEntryTime()       { return entryTime; }
    public String getExitTime()        { return exitTime; }
    public long   getDurationMinutes() { return durationMinutes; }
    public double getFee()             { return fee; }

    @Override
    public String toString() {
        return String.format(
            "╔══════════════════════════════════════╗%n" +
            "║          PARKING RECEIPT             ║%n" +
            "╠══════════════════════════════════════╣%n" +
            "║ Ticket   : %-27s║%n" +
            "║ Vehicle  : %-27s║%n" +
            "║ Spot     : %-27s║%n" +
            "║ Entry    : %-27s║%n" +
            "║ Exit     : %-27s║%n" +
            "║ Duration : %-20s mins  ║%n" +
            "║ Fee      : Rs.%-24.2f║%n" +
            "╚══════════════════════════════════════╝",
            ticketId, licensePlate, spotId,
            entryTime, exitTime, durationMinutes, fee
        );
    }
}
