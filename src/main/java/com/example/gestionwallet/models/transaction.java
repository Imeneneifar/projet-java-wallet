package com.example.gestionwallet.models;

import java.sql.Date;

public class transaction {

    private int id_transaction;
    private String type;      // INCOME / OUTCOME
    private double montant;
    private Date date_transaction;
    private String source;    // MANUAL / BOURSE / INVESTISSEMENT / OFFRE
    private int user_id;
    private int category_id;

    public transaction() {}

    // INSERT
    public transaction(String type, double montant, Date date_transaction,
                       String source, int user_id, int category_id) {
        this.type = type;
        this.montant = montant;
        this.date_transaction = date_transaction;
        this.source = source;
        this.user_id = user_id;
        this.category_id = category_id;
    }

    // FULL
    public transaction(int id_transaction, String type, double montant, Date date_transaction,
                       String source, int user_id, int category_id) {
        this.id_transaction = id_transaction;
        this.type = type;
        this.montant = montant;
        this.date_transaction = date_transaction;
        this.source = source;
        this.user_id = user_id;
        this.category_id = category_id;
    }

    // Getters & Setters
    public int getId_transaction() { return id_transaction; }
    public void setId_transaction(int id_transaction) { this.id_transaction = id_transaction; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Date getDate_transaction() { return date_transaction; }
    public void setDate_transaction(Date date_transaction) { this.date_transaction = date_transaction; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }

    @Override
    public String toString() {
        return id_transaction + " | " + type + " | " + montant + " DT | " + source;
    }
}
