package com.example.petcare.db;

public class Notification {
    private final String id;
    private final String petID;
    private final String text;
    private final String date;
    private final String ownerID;
    private final String petName;

    public Notification(String id, String petID, String text, String date, String ownerID, String petName) {
        this.id = id;
        this.petID = petID;
        this.text = text;
        this.date = date;
        this.ownerID = ownerID;
        this.petName = petName;
    }



    public Notification() {
        this.petName = null;
        this.ownerID = null;
        this.id = null;
        this.petID = null;
        this.text = null;
        this.date = null;
    }

    public String getPetName() {
        return petName;
    }
    public String getOwnerID() {
        return ownerID;
    }
    public String getId() {
        return id;
    }

    public String getPetID() {
        return petID;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
