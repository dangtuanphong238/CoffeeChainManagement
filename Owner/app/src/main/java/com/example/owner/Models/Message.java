package com.example.owner.Models;

public class Message {
    public String imgUser;
    public String messageUser;
    public String messageText;
    public String messageTime;

    public Message(String imgUser, String messageUser, String messageText, String messageTime) {
        this.imgUser = imgUser;
        this.messageUser = messageUser;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public Message() {
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
