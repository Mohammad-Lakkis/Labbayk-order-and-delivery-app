package com.example.labbayk.tracking;

public class Customer {

    private String Customer_Name;
    private double latitude;
    private double longitude;

    public Customer() {

    }

    public Customer(String Customer_Name, double latitude, double longitude) {
        this.Customer_Name = Customer_Name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
