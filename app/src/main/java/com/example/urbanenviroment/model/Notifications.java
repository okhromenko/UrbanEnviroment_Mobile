package com.example.urbanenviroment.model;

public class Notifications {
    int id;
    String name_org, img_org, type_notifications;

    public Notifications(int id, String name_org, String img_org, String type_notifications) {
        this.id = id;
        this.name_org = name_org;
        this.img_org = img_org;
        this.type_notifications = type_notifications;
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

    public String getType_notifications() {
        return type_notifications;
    }

    public void setType_notifications(String type_notifications) {
        this.type_notifications = type_notifications;
    }
}
