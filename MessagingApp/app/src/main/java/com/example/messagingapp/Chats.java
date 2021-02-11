package com.example.messagingapp;

public class Chats {
    private String sender;
    private String receiver;
    private String message;
    private String image_receiver;

    public String getImage_receiver() {
        return image_receiver;
    }

    public void setImage_receiver(String image_receiver) {
        this.image_receiver = image_receiver;
    }

    private String image_sender;

    public String getImage_sender() {
        return image_receiver;
    }

    public void setImage_sender(String image_sender) {
        this.image_receiver = image_sender;
    }

    public Chats(String sender, String receiver, String message, String image_receiver,String image_sender) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.image_receiver=image_receiver;
        this.image_sender=image_sender;
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
    public Chats(){

    }
}
