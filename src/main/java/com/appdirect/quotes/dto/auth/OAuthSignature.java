package com.appdirect.quotes.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
@Data
public class OAuthSignature {
    @NotNull
    private String accessToken;

    @NotNull
    private String secret;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
