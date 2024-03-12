package org.example.models;

public class Activity {
    private int id;
    private String  name;
    private String priority;
    private String status;

    public Activity(int id, String name, String priority, String status) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.status = status;
    }

    public Activity(String name, String priority, String status) {
        this.name = name;
        this.priority = priority;
        this.status = status;
    }

    public Activity() {
    }

    public int getId() {
        return id;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
