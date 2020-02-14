package com.example.Omotecconnect;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String location;
    private long contact;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoc() {
        return location;
    }

    public void setLoc(String id) {
        this.location = id;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long id) {
        this.contact = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}