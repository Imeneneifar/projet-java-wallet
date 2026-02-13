package com.example.gestionwallet.services;

import com.example.gestionwallet.interfaces.services.Iservicetransaction;
import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicetransaction implements Iservicetransaction {

    Connection cnx;

    //Singleton
    public servicetransaction() {
        cnx = database.getInstance().getConnection();
    }
    //existance
    public boolean exists(transaction t) {

        //requête SQL
        String sql = """
            SELECT COUNT(*)
            FROM transaction
            WHERE nom_transaction=?
              AND type=?
              AND montant=?
              AND date_transaction=?
              AND source=?
              AND user_id=?
              AND category_id=?
        """;

        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getNom_transaction());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getMontant());
            ps.setDate(4, t.getDate_transaction());
            ps.setString(5, t.getSource());
            ps.setInt(6, t.getUser_id());
            ps.setInt(7, t.getCategory_id());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    //ajout
    @Override
    public void ajouter(transaction t) {
        //existance
        if (exists(t)) {
            System.out.println(" Transaction déjà existante ");
            return;
        }

        String sql = """
            INSERT INTO transaction
            (nom_transaction, type, montant, date_transaction, source, user_id, category_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getNom_transaction());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getMontant());
            ps.setDate(4, t.getDate_transaction());
            ps.setString(5, t.getSource());
            ps.setInt(6, t.getUser_id());
            ps.setInt(7, t.getCategory_id());

            ps.executeUpdate();

            System.out.println("Transaction ajoutée");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //update
    @Override
    public void modifier(transaction t) {

        String sql = """
            UPDATE transaction
            SET nom_transaction=?, type=?, montant=?, date_transaction=?, category_id=?
            WHERE id_transaction=? AND source='MANUAL'
        """;

        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getNom_transaction());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getMontant());
            ps.setDate(4, t.getDate_transaction());
            ps.setInt(5, t.getCategory_id());
            ps.setInt(6, t.getId_transaction());

            ps.executeUpdate();

            System.out.println("Transaction modifiée");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //remove
    @Override
    public void supprimer(int id) {

        String sql = "DELETE FROM transaction WHERE id_transaction=? AND source='MANUAL'";

        try {

            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Transaction supprimée");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //affichage
    @Override
    public List<transaction> afficher() {
        //new list
        List<transaction> list = new ArrayList<>();

        String sql = "SELECT t.id_transaction, t.nom_transaction, t.type, t.montant, " +
                "t.date_transaction, t.source, t.user_id, t.category_id, c.nom AS nom_categorie " +
                "FROM transaction t " +
                "JOIN category c ON t.category_id = c.id_category";

        try {

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                transaction t = new transaction(
                        rs.getInt("id_transaction"),
                        rs.getString("nom_transaction"),
                        rs.getString("type"),
                        rs.getDouble("montant"),
                        rs.getDate("date_transaction"),
                        rs.getString("source"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id")
                );

                // nom categorie entre ()
                String categorie = rs.getString("nom_categorie");
                t.setNom_transaction(t.getNom_transaction() + " (" + categorie + ")");

                list.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace(); //erreur BD
        }

        return list;
    }
}
