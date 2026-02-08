package com.example.gestionwallet.models;

public class categorie {

    private int id_category;
    private String nom;
    private String priorite; // HAUTE | MOYENNE | BASSE

    public categorie() {}

    public categorie(String nom, String priorite) {
        this.nom = nom;
        this.priorite = priorite;
    }

    public categorie(int id_category, String nom, String priorite) {
        this.id_category = id_category;
        this.nom = nom;
        this.priorite = priorite;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    @Override
    public String toString() {
        return nom + " | " + priorite;
    }
}
