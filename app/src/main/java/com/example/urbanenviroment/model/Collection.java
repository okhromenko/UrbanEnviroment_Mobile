package com.example.urbanenviroment.model;

public class Collection {
    String id;
    String img_collection;
    String name_org;
    String img_org;
    String id_animal;
    String kind;
    String reg_date;

    public Collection(String id, String img_collection, String name_org, String img_org, String id_animal, String kind, String reg_date) {
        this.id = id;
        this.img_collection = img_collection;
        this.name_org = name_org;
        this.img_org = img_org;
        this.id_animal = id_animal;
        this.kind = kind;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_collection() {
        return img_collection;
    }

    public void setImg_collection(String img_collection) {
        this.img_collection = img_collection;
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

    public String getId_animal() {
        return id_animal;
    }

    public void setId_animal(String id_animal) {
        this.id_animal = id_animal;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}
