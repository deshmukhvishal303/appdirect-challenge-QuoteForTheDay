package com.appdirect.quotes.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 15/10/16.
 */
@Data
@JsonSnakeCase
public class UserSignInResponse {

    @NotNull
    @JsonProperty(value = "session_id")
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
