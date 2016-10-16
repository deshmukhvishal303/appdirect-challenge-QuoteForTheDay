package com.appdirect.quotes.dto.subscription;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Vishal Deshmukh on 16/10/16.
 */
public class Order {

    @XmlElement
    private String editionCode;

    @XmlElement(name = "item")
    private List<OrderItem> orderItem;

}
