package com.example.petcare.db;

import android.content.Intent;

public class Pet {
    /*
    intent.putExtra("isSterilised",isSterilised);
        intent.putExtra("isMeds", isMeds);
        intent.putExtra("medsHowOften", medsHowOften);
        intent.putExtra("isMale", getIntent().getBooleanExtra("isMale", false));;
        intent.putExtra("isDog", getIntent().getBooleanExtra("isDog", false));
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("dateOfBirth",  getIntent().getStringExtra("dateOfBirth"));
        intent.putExtra("breed",  getIntent().getStringExtra("breed"));
        intent.putExtra("weight",  getIntent().getStringExtra("weight"));
     */
    public static Pet create(Intent intent, String id, String ownerId, String photo){
        Pet pet = new Pet(id,
                ownerId,
                intent.getBooleanExtra("isSterilised", false),
                intent.getBooleanExtra("isMeds", false),
                intent.getBooleanExtra("isMale", false),
                intent.getBooleanExtra("isDog", false),
                intent.getStringExtra("name"),
                intent.getStringExtra("breed"),
                intent.getStringExtra("dateOfBirth"),
                intent.getIntExtra("weight", 0),
                intent.getStringExtra("medsHowOften"),

                photo);
        return pet;
    }
    private final String id;
    private final String ownerId;
    private final boolean isSterlilised;
    private final boolean isMeds;
    private final boolean isMale;
    private final boolean isDog;
    private final String name;
    private final String breed;
    private final String dateOfBirth;
    private final int weight;
    private final String medsHowOften;
    private final String photo;

    public String getPhoto() {
        return photo;
    }

    public Pet(String id, String ownerId, boolean isSterlilised, boolean isMeds, boolean isMale, boolean isDog, String name, String breed, String dateOfBirth, int weight, String medsHowOften, String photo) {
        this.id = id;
        this.ownerId = ownerId;
        this.isSterlilised = isSterlilised;
        this.isMeds = isMeds;
        this.isMale = isMale;
        this.isDog = isDog;
        this.name = name;
        this.breed = breed;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.medsHowOften = medsHowOften;
        this.photo = photo;
    }
    public Pet() {
        this.id = "";
        this.ownerId = "";
        this.isSterlilised = false;
        this.isMeds = false;
        this.isMale = false;
        this.isDog = false;
        this.name = "";
        this.breed = "";
        this.dateOfBirth = "";
        this.weight = 0;
        this.medsHowOften = "";
        this.photo = "";
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public boolean isIsSterlilised() {
        return isSterlilised;
    }

    public boolean isIsMeds() {
        return isMeds;
    }

    public boolean isIsMale() {
        return isMale;
    }

    public boolean isIsDog() {
        return isDog;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getWeight() {
        return weight;
    }

    public String getMedsHowOften() {
        return medsHowOften;
    }
}
