package com.example.staff.Activity;

public class Message {
    public String userID;
    public String messageText;
    public String messageTime;

    public Message( String messageUser, String messageText, String messageTime) {
        this.userID = messageUser;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public Message() {
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
