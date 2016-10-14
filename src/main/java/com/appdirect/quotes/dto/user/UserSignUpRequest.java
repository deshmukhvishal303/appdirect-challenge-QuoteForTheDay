package com.appdirect.quotes.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Data
@AllArgsConstructor
public class UserSignUpRequest {
    @NotNull
    private String emailId;
    @NotNull
    private String password;
    @NotNull
    private String name;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
