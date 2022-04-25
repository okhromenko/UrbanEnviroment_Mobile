package com.example.urbanenviroment.model;

public class Organizations {
    String id, name_org, img_org, phone, address, email, description, count_animal, count_ads, count_photo, date;

    public Organizations(String id, String name_org, String img_org, String phone, String address, String email,
                         String description, String count_animal, String count_ads, String count_photo, String date) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.description = description;
        this.count_animal = count_animal;
        this.count_ads = count_ads;
        this.count_photo = count_photo;
        this.date = date;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCount_ads() {
        return count_ads;
    }

    public void setCount_ads(String count_ads) {
        this.count_ads = count_ads;
    }

    public String getCount_photo() {
        return count_photo;
    }

    public void setCount_photo(String count_photo) {
        this.count_photo = count_photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
