package org.esprit.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Trip {

    private int id;
    private String name;
    private LocalDate date;
    private int nbSejour;
    private float prix;
    private int guide;

    public Trip() {
    }

    public Trip(int id, String name, LocalDate date, int nbSejour, float prix, int guide) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.nbSejour = nbSejour;
        this.prix = prix;
        this.guide = guide;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNbSejour() {
        return nbSejour;
    }

    public void setNbSejour(int nbSejour) {
        this.nbSejour = nbSejour;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getGuide() {
        return guide;
    }

    public void setGuide(int guide) {
        this.guide = guide;
    }
}
