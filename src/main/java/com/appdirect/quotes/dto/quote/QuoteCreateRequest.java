package com.appdirect.quotes.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 14/10/16.
 */
@Data
public class QuoteCreateRequest {
    @NotNull
    @JsonProperty(value = "quote")
    private String quote;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
