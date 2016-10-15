package com.appdirect.quotes.db.model.entities;

import com.appdirect.quotes.util.JodaDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Vishal Deshmukh on 15/10/16.
 */
@Data
@Entity
@JsonSnakeCase
@Table(name = "session_master")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Session {

    @Id
    @Column(name = "session_id")
    @JsonProperty(value = "session_id")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(value = "user_id")
    private User user;

    @Column(name = "expiration_time")
    @JsonProperty(value = "expiration_time")
    @Convert(converter = JodaDateTimeConverter.class)
    private DateTime expirationTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public DateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(DateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
