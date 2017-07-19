package com.graphysis.gst;

/**
 * Created by pritesh on 12/7/17.
 */

public class Calculator {
    String item;
    String costprice;
    String quantity;
    String Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }



    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCostprice() {
        return costprice;
    }

    public void setCostprice(String costprice) {
        this.costprice = costprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
