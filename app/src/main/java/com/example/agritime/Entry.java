package com.example.agritime;

public class Entry {
    private String id;
    private String name;
    private String category;
    private String time;

    public Entry() {
    }

    public Entry(String name, String category, String time) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
