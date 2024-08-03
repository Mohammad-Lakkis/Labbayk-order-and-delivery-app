package com.example.labbayk.Domain;

import java.io.Serializable;

public class Order implements Serializable {
    private double total;
    private String customerName,customerPhone;


    public Order(double total, String customerName, String customerPhone) {
        this.total = total;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
