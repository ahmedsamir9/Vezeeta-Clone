package com.example.vezetaaclone.Firestore_objs;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private String LastMessage;

    public Chat(String sender, String receiver, String message, String LastMessage) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.LastMessage = LastMessage;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String lastMessage) {
        LastMessage = lastMessage;
    }

    public Chat() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
