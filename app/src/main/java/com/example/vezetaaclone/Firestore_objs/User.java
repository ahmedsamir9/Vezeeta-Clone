package com.example.vezetaaclone.Firestore_objs;

public class User {
    private String Email;
    private String Phone;
    private String Name;
    private String Id;
    private String image;


    public User() {
    }

    public User(String ID, String Email, String Phone, String fName) {
        this.Email = Email;
        this.Phone = Phone;
        this.Name = fName;

    }
    public String getImage() {
        return image;
    }

    public void setImage(String photo) {
        image = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String ID) {
        this.Id= ID;
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
