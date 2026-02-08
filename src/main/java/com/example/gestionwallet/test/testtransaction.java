package com.example.gestionwallet.test;

import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.services.servicetransaction;

import java.sql.Date;

public class testtransaction {

    public static void main(String[] args) {

        servicetransaction st = new servicetransaction();

        // ADD
        transaction t1 = new transaction(
                "OUTCOME",
                50,
                Date.valueOf("2024-04-25"),
                "MANUAL",
                1,  // user_id
                1   // category_id
        );
        st.ajouter(t1);

        // READ
        st.afficher().forEach(System.out::println);

        // UPDATE
        transaction t2 = new transaction(
                1,
                "OUTCOME",
                70,
                Date.valueOf("2024-04-26"),
                "MANUAL",
                1,
                2
        );
        st.modifier(t2);

        // DELETE
        // st.supprimer(1);
    }
}
