package com.example.urbanenviroment.model;

public class Organizations {
    int id;
    String name_org, img_org, description, count_animal, date;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount_animal() {
        return count_animal;
    }

    public void setCount_animal(String count_animal) {
        this.count_animal = count_animal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Organizations(int id, String name_org, String img_org, String description, String count_animal, String date) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.description = description;
        this.count_animal = count_animal;
        this.date = date;
    }
}
