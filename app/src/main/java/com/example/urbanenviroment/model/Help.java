package com.example.urbanenviroment.model;

public class Help {
    int id;
    String name_org, img_org, type_help, description, date, status;

    public Help(int id, String name_org, String img_org, String type_help, String description, String date, String status) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.type_help = type_help;
        this.description = description;
        this.date = date;
        this.status = status;
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

    public String getType_help() {
        return type_help;
    }

    public void setType_help(String type_help) {
        this.type_help = type_help;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
