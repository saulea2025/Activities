package org.example.models;

public class Person {
    private int id;
    private String name;
    private String surname;
    private String role;
    private int currentActivity;


    public Person(int id, String name, String surname, int currentActivity) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.currentActivity = currentActivity;
    }

    public Person(String name, String surname, int currentActivity) {
        this.name = name;
        this.surname = surname;
        this.currentActivity = currentActivity;
    }

    public Person() {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
