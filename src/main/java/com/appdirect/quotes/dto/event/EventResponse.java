package com.appdirect.quotes.dto.event;

import lombok.Data;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
@Data
public class EventResponse {
    private boolean success = true;
    private String message = "Welcome to Quotes";
    private int errorCode;
    private String accountIdentifier = "QuoteForTheDay";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }
}
