package com.example.reservations;

public class Reservation {
    private String name;
    private String date;
    private String time;
    private int people;

    public Reservation(String name, String date, String time, int people) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.people = people;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %d)", name, date, time, people);
    }
}