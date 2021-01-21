package com.example.vezetaaclone.Firestore_objs;

public class User {
    private String Email;
    private String Phone;
    private String Name;

    public User() {
    }

    public User(String Email, String Phone, String Name) {
        this.Email = Email;
        this.Phone = Phone;
        this.Name = Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
