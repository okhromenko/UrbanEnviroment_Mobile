package com.example.urbanenviroment.model;

public class Animals {

    String id;
    String name_org;
    String img_org;
    String name_animal;
    String img_animal;
    String age;
    String state;
    String kind;
    String species;
    String description;
    String sex;
    String reg_data;
    String address;

    public Animals(String id, String name_org, String img_org, String address, String name_animal, String img_animal,
                   String age, String state, String kind, String species, String description, String sex, String reg_data) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.name_animal = name_animal;
        this.img_animal = img_animal;
        this.age = age;
        this.state = state;
        this.kind = kind;
        this.species = species;
        this.description = description;
        this.sex = sex;
        this.reg_data = reg_data;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReg_data() {
        return reg_data;
    }

    public void setReg_data(String reg_data) {
        this.reg_data = reg_data;
    }
}
