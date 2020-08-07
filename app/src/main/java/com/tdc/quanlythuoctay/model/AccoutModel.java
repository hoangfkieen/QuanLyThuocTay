package com.tdc.quanlythuoctay.model;

public class AccoutModel {
    private int id;
    private String User;
    private String Pass;
    private String Avatar;

    public AccoutModel() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public AccoutModel(int id,String user,  String pass, String avatar) {
        this.id = id;
        User = user;
        Pass = pass;
        Avatar = avatar;
    }

    public AccoutModel(String user, String pass, String avatar) {
        User = user;
        Pass = pass;
        Avatar = avatar;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
