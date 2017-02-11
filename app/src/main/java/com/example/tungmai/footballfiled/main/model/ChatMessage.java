package com.example.tungmai.footballfiled.main.model;

/**
 * Created by tungmai on 25/01/2017.
 */

public class ChatMessage {

    private String idRecipient;
    private String nameRecipient;
    private String idSender;
    private String nameSender;
    private String content;
    private String time;
    private String urlImage;

    private int mRecipientOrSenderStatus;

    public ChatMessage(String idRecipient, String nameRecipient, String idSender, String nameSender, String content, String time, String urlImage, int mRecipientOrSenderStatus) {
        this.idRecipient = idRecipient;
        this.nameRecipient = nameRecipient;
        this.idSender = idSender;
        this.nameSender = nameSender;
        this.content = content;
        this.time = time;
        this.urlImage = urlImage;
        this.mRecipientOrSenderStatus = mRecipientOrSenderStatus;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getIdRecipient() {
        return idRecipient;
    }

    public String getNameRecipient() {
        return nameRecipient;
    }

    public String getIdSender() {
        return idSender;
    }

    public String getNameSender() {
        return nameSender;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getmRecipientOrSenderStatus() {
        return mRecipientOrSenderStatus;
    }

    public void setmRecipientOrSenderStatus(int mRecipientOrSenderStatus) {
        this.mRecipientOrSenderStatus = mRecipientOrSenderStatus;
    }
}
