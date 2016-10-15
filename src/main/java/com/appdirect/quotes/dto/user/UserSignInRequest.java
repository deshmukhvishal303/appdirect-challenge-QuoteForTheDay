package com.appdirect.quotes.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Data
@JsonSnakeCase
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignInRequest {
    @NotNull
    @JsonProperty(value = "user_name")
    private String userName;

    @NotNull
    @JsonProperty(value = "password")
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
