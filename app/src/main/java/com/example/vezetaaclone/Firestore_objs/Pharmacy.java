package com.example.vezetaaclone.Firestore_objs;

public class Pharmacy extends User {
    private String SecondPhone;

    public Pharmacy() {
    }

    public Pharmacy(String Email, String Phone, String Name,String SecondPhone) {
        super(Email, Phone, Name);
        this.SecondPhone = SecondPhone;
    }


    public String getSecondPhone() {
        return SecondPhone;
    }

    public void setSecondPhone(String SecondPhone) {
        this.SecondPhone = SecondPhone;
    }
}
