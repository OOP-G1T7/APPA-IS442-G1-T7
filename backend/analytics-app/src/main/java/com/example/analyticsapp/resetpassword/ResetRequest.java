package com.example.analyticsapp.resetpassword;

public class ResetRequest {
    private String email;

    public ResetRequest() {
    }

    public ResetRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}