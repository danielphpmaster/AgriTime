package com.example.agritime;

public class Entry {
    private String id;
    private String name;
    private int category;
    private String time;

    public Entry() {
    }

    public Entry(String name, int category, String time) {
        this.name = name;
        this.category = category;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
