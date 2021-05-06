package com.example.petcare.db;

public class Notification {
    private final String id;
    private final String petID;
    private final String text;
    private final String date;

    public Notification(String id, String petID, String text, String date) {
        this.id = id;
        this.petID = petID;
        this.text = text;
        this.date = date;
    }
    public Notification() {
        this.id = null;
        this.petID = null;
        this.text = null;
        this.date = null;
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
