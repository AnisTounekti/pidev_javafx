package org.esprit.entities;

import java.time.LocalDateTime;

public class Reservation {

    private int id;
    private String trip;
    private String user;
    private LocalDateTime date;
    private String Status;

    public Reservation() {
    }

    public Reservation(int id, String trip, String user, LocalDateTime date, String status) {
        this.id = id;
        this.trip = trip;
        this.user = user;
        this.date = date;
        Status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrip() {
        return trip;
    }

    public void setTrip(String trip) {
        this.trip = trip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
