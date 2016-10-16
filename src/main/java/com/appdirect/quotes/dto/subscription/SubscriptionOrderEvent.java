package com.appdirect.quotes.dto.subscription;

import lombok.Data;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
@Data
public class SubscriptionOrderEvent {

    private SubscriptionOrderPayload payload;
    private EventType type;
    private Marketplace marketplace;
    private Creator creator;


    public SubscriptionOrderEvent() {
        this.type = EventType.SUBSCRIPTION_ORDER;
    }

    public SubscriptionOrderPayload getPayload() {
        return payload;
    }

    public void setPayload(SubscriptionOrderPayload payload) {
        this.payload = payload;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}
