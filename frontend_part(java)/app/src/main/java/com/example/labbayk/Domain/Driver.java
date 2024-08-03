package com.example.labbayk.Domain;

import java.io.Serializable;

public class Driver implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
