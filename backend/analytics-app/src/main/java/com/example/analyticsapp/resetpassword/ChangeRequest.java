package com.example.analyticsapp.resetpassword;

public class ChangeRequest {
    private String password;

    public ChangeRequest() {
    }

    public ChangeRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}