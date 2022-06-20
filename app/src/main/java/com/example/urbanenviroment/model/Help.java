package com.example.urbanenviroment.model;

public class Help {
    String id, id_org, name_org, img_org, type_help, description, date_last, date_first, status;

    public Help(String id, String id_org, String name_org, String img_org, String type_help, String description, String date_first, String date_last, String status) {
        this.id = id;
        this.id_org = id_org;
        this.name_org = name_org;
        this.img_org = img_org;
        this.type_help = type_help;
        this.description = description;
        this.date_first = date_first;
        this.date_last = date_last;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_org() {
        return id_org;
    }

    public void setId_org(String id_org) {
        this.id_org = id_org;
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

    public String getDate_last() {
        return date_last;
    }

    public void setDate_last(String date_last) {
        this.date_last = date_last;
    }

    public String getDate_first() {
        return date_first;
    }

    public void setDate_first(String date_first) {
        this.date_first = date_first;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
