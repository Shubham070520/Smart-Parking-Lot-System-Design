package com.parking.models;


public class AvailabilityModel {

    private final long smallAvailable;
    private final long mediumAvailable;
    private final long largeAvailable;

    public AvailabilityModel(long smallAvailable, long mediumAvailable, long largeAvailable) {
        this.smallAvailable  = smallAvailable;
        this.mediumAvailable = mediumAvailable;
        this.largeAvailable  = largeAvailable;
    }

    public long getSmallAvailable()  { return smallAvailable; }
    public long getMediumAvailable() { return mediumAvailable; }
    public long getLargeAvailable()  { return largeAvailable; }

    @Override
    public String toString() {
        return String.format(
            "Availability{SMALL=%d, MEDIUM=%d, LARGE=%d}",
            smallAvailable, mediumAvailable, largeAvailable
        );
    }
}
