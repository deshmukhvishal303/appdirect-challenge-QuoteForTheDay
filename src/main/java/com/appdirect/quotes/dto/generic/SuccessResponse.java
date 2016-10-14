package com.appdirect.quotes.dto.generic;

import lombok.Data;

/**
 * Created by Vishal Deshmukh on 13/10/16.
 */
@Data
public class SuccessResponse {
    private String message;

    public SuccessResponse(String message){
        this.message = message;
    }
}
