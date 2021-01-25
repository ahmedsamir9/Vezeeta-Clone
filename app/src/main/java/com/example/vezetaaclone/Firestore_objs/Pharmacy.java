package com.example.vezetaaclone.Firestore_objs;

public class Pharmacy extends User {
    private String SecondPhone;
    private Location location;
    public Pharmacy() {
    }


    public Pharmacy(String ID, String Email, String Phone, String Name, String SecondPhone, Location Loc) {
        super(ID, Email, Phone, Name);
        this.SecondPhone = SecondPhone;
        location = Loc;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSecondPhone() {
        return SecondPhone;
    }

    public void setSecondPhone(String SecondPhone) {
        this.SecondPhone = SecondPhone;
    }
}
