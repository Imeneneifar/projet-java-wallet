package com.example.gestionwallet;

import com.example.gestionwallet.interfaces.services.Iservicetransaction;
import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicetransaction implements Iservicetransaction {

    Connection cnx;

    public servicetransaction() {
        cnx = database.getInstance().getConnection();
    }

    // CREATE
    @Override
    public void ajouter(transaction t) {

        if (exists(t)) {
            System.out.println(" Transaction déjà existante — ajout annulé");
            return;
        }

        String sql = """
        INSERT INTO transaction
        (type, montant, date_transaction, source, user_id, category_id)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getType());
            ps.setDouble(2, t.getMontant());
            ps.setDate(3, t.getDate_transaction());
            ps.setString(4, t.getSource());
            ps.setInt(5, t.getUser_id());
            ps.setInt(6, t.getCategory_id());
            ps.executeUpdate();

            System.out.println(" Transaction ajoutée");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // UPDATE (MANUAL)
    @Override
    public void modifier(transaction t) {
        String sql = """
            UPDATE transaction
            SET type=?, montant=?, date_transaction=?, category_id=?
            WHERE id_transaction=? AND source='MANUAL'
        """;

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getType());
            ps.setDouble(2, t.getMontant());
            ps.setDate(3, t.getDate_transaction());
            ps.setInt(4, t.getCategory_id());
            ps.setInt(5, t.getId_transaction());
            ps.executeUpdate();

            System.out.println(" Transaction modifiée");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE (MANUAL)
    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM transaction WHERE id_transaction=? AND source='MANUAL'";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println(" Transaction supprimée");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // READ
    @Override
    public List<transaction> afficher() {
        List<transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transaction";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                transaction t = new transaction(
                        rs.getInt("id_transaction"),
                        rs.getString("type"),
                        rs.getDouble("montant"),
                        rs.getDate("date_transaction"),
                        rs.getString("source"),
                        rs.getInt("user_id"),
                        rs.getInt("category_id")
                );
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean exists(transaction t) {
        String sql = """
        SELECT COUNT(*) 
        FROM transaction 
        WHERE type=? 
          AND montant=? 
          AND date_transaction=? 
          AND source=? 
          AND user_id=? 
          AND category_id=?
    """;

        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, t.getType());
            ps.setDouble(2, t.getMontant());
            ps.setDate(3, t.getDate_transaction());
            ps.setString(4, t.getSource());
            ps.setInt(5, t.getUser_id());
            ps.setInt(6, t.getCategory_id());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
