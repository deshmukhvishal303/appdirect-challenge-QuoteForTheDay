package com.appdirect.quotes.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Data
@AllArgsConstructor
public class UserSignInRequest {
    @NotNull
    private String userName;

    @NotNull
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
