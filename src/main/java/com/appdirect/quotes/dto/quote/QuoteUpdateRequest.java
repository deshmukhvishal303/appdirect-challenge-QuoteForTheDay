package com.appdirect.quotes.dto.quote;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Vishal Deshmukh on 14/10/16.
 */
@Data
public class QuoteUpdateRequest {

    @NotNull
    private long quoteId;

    @NotNull
    private String quote;

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
