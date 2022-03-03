package com.example.urbanenviroment.model;

public class Animals {
    int id;
    String name_org, img_org, img_animal, date;

    public Animals(int id, String name_org, String img_org, String img_animal, String date) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.img_animal = img_animal;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_org() {
        return name_org;
    }

    public void setName_org(String name_org) {
        this.name_org = name_org;
    }

    public String getImg_org() {
        return img_org;
    }

    public void setImg_org(String img_org) {
        this.img_org = img_org;
    }

    public String getImg_animal() {
        return img_animal;
    }

    public void setImg_animal(String img_animal) {
        this.img_animal = img_animal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
