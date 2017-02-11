package com.example.tungmai.footballfiled.main.model;

import android.widget.ImageView;

/**
 * Created by tungmai on 08/02/2017.
 */

public class ItemSearchAddMessage {
    private String idUser;
    private String urlImage;
    private String name;


    public ItemSearchAddMessage(String idUser, String urlImage, String name) {
        this.idUser = idUser;
        this.urlImage = urlImage;
        this.name = name;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
