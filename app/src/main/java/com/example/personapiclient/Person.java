package com.example.personapiclient;

import java.io.Serializable;

public class Person implements Serializable {
    int id=-1;
    String name="";
    boolean favorit=false;	// Checkbox
    int hairColor=0;		// Radiobuttons
    String address="";
    String phone="";
    String note="";




    public Person(int id, String name, boolean favorit, int hairColor, String address, String phone, String note) {
        super();
        this.id = id;
        this.name = name;
        this.favorit = favorit;
        this.hairColor = hairColor;
        this.address = address;
        this.phone = phone;
        this.note = note;
    }

    public Person() {}

    public Person(String name, boolean favorit, int hairColor, String address, String phone, String note) {

        this.name = name;
        this.favorit = favorit;
        this.hairColor = hairColor;
        this.address = address;
        this.phone = phone;
        this.note = note;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isFavorit() {
        return favorit;
    }
    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }
    public int getHairColor() {
        return hairColor;
    }
    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
