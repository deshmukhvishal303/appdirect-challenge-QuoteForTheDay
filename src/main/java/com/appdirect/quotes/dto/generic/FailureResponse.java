package com.appdirect.quotes.dto.generic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.ws.rs.DefaultValue;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Data
public class FailureResponse {

    @DefaultValue("Some Exception Occurred!!! Please contact Admin")
    @JsonProperty(value = "error_message")
    private String errorMessage;

    public FailureResponse(){
    }

    public FailureResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
