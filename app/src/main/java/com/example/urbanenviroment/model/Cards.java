package com.example.urbanenviroment.model;

public class Cards {

    int id;
    String name_org, kind, name_animal, img_animal, description;

    public Cards(int id, String name_org, String kind, String name_animal, String img_animal, String description) {
        this.id = id;
        this.name_org = name_org;
        this.kind = kind;
        this.name_animal = name_animal;
        this.img_animal = img_animal;
        this.description = description;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName_animal() {
        return name_animal;
    }

    public void setName_animal(String name_animal) {
        this.name_animal = name_animal;
    }

    public String getImg_animal() {
        return img_animal;
    }

    public void setImg_animal(String img_animal) {
        this.img_animal = img_animal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
