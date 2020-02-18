package com.android.myapplication;


public class CasualUser {
    String id, email, pass, name, city, phone, photo;
    boolean isActive, isMan;

    public CasualUser(String id, String email, String pass, String name, String city, String phone, String photo, boolean isActive, boolean isMan) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.photo = photo;
        this.isActive = isActive;
        this.isMan = isMan;
    }

    public CasualUser() {
        this.id = "";
        this.email = "";
        this.pass = "";
        this.name = "";
        this.city = "";
        this.phone = "";
        this.photo = "";
        this.isActive = true;
        this.isMan = true;
    }

    public boolean isMan() {
        return isMan;
    }

    public void setMan(boolean man) {
        isMan = man;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
