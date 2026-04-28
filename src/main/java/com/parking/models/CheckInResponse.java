package com.parking.models;


public class CheckInResponse {

    private final String ticketId;
    private final String licensePlate;
    private final String spotId;
    private final int    floorNumber;
    private final String entryTime;

    public CheckInResponse(String ticketId, String licensePlate,
                           String spotId, int floorNumber, String entryTime) {
        this.ticketId     = ticketId;
        this.licensePlate = licensePlate;
        this.spotId       = spotId;
        this.floorNumber  = floorNumber;
        this.entryTime    = entryTime;
    }

    public String getTicketId()     { return ticketId; }
    public String getLicensePlate() { return licensePlate; }
    public String getSpotId()       { return spotId; }
    public int    getFloorNumber()  { return floorNumber; }
    public String getEntryTime()    { return entryTime; }

    @Override
    public String toString() {
        return String.format(
            "CheckInResponse{ticketId='%s', plate='%s', spot='%s', floor=%d, entry='%s'}",
            ticketId, licensePlate, spotId, floorNumber, entryTime
        );
    }
}
