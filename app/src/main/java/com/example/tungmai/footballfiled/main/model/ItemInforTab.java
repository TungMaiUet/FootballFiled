package com.example.tungmai.footballfiled.main.model;

import java.util.ArrayList;

/**
 * Created by tungmai on 11/01/2017.
 */

public class ItemInforTab {
    private String title;
    private String idUser;
    private String nameGroup;
    private String phone;
    private String address;
    private String time;
    private String describe;
    private String typeWrite;
    private String userName;
    private String images;

    public ItemInforTab() {
    }

    public ItemInforTab(String title, String idUser, String nameGroup, String phone, String address, String time, String describe, String typeWrite, String images) {
        this.title = title;
        this.idUser = idUser;
        this.nameGroup = nameGroup;
        this.phone = phone;
        this.address = address;
        this.time = time;
        this.describe = describe;
        this.typeWrite = typeWrite;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public String getDescribe() {
        return describe;
    }

    public String getTypeWrite() {
        return typeWrite;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImages() {
        return images;
    }
}
