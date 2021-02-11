package com.example.messagingapp;

public class Users {

   private String Username;
    private String Password;
    private String Email;
    private String ID;

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String imageSrc) {
        ImageSrc = imageSrc;
    }

    private String ImageSrc;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Users(String username, String password, String email, String ID, String imageSrc) {
        Username = username;
        Password = password;
        Email = email;
        this.ID = ID;
        ImageSrc = imageSrc;
    }

    public Users(String username, String password, String email, String ID) {
        Username = username;
        Password = password;
        Email = email;
        this.ID = ID;

    }
    public Users(){

    }
}
