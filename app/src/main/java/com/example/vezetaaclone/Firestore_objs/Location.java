package com.example.vezetaaclone.Firestore_objs;

public class Location {
    private String Location;
    private Double Longitude;
    private Double Latitude;

    public Location(String Location, Double Longitude, Double Latitude) {
        this.Location = Location;
        this.Longitude = Longitude;
        this.Latitude = Latitude;
    }
    public Location()
    {

    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }
}
