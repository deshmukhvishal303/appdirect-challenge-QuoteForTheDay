package com.appdirect.quotes.dto.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Vishal Deshmukh on 14/10/16.
 */
@Data
public class QuoteResponse {
    @JsonProperty(value = "quote")
    private List<String> quote;

    public List<String> getQuote() {
        return quote;
    }

    public void setQuote(List<String> quote) {
        this.quote = quote;
    }
}
