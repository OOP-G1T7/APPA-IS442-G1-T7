package com.example.analyticsapp.user.util;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to represent the change password request
 * 
 */

public class ChangePasswordRequest {

    private String email;
    @JsonProperty("passwordCurrent")
    private String passwordCurrent;
    @JsonProperty("passwordNew")
    private String passwordNew;
    @JsonProperty("passwordConfirm")
    private String passwordConfirm;

    // Getters & Setters 
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCurrentPassword() {
        return passwordCurrent;
    }
    public void setCurrentPassword(String passwordCurrent) {
        this.passwordCurrent = passwordCurrent;
    }
    public String getNewPassword() {
        return passwordNew;
    }
    public void setNewPassword(String passwordNew) {
        this.passwordNew = passwordNew;
    }
    public String getConfirmPassword() {
        return passwordConfirm;
    }
    public void setConfirmPassword(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    
    
}
