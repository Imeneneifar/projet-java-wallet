package com.example.gestionwallet.test;

import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.services.servicetransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TestTransaction {

    private servicetransaction service;

    @BeforeEach
    void setUp() {
        service = new servicetransaction();
    }

    @Test
    void testAjouterTransaction() {

        Date date = Date.valueOf("2025-02-10");

        transaction t = new transaction(
                "INCOME",      // type
                150.0,         // montant
                date,          // date_transaction
                "MANUAL",      // source
                1,             // user_id
                1              // category_id
        );

        service.ajouter(t);

        assertNotNull(t);
        assertEquals(150.0, t.getMontant());
        assertEquals("INCOME", t.getType());
    }

    @Test
    void testSupprimerTransaction() {

        Date date = Date.valueOf("2025-02-10");

        transaction t = new transaction(
                "OUTCOME",
                50.0,
                date,
                "MANUAL",
                1,
                1
        );

        service.ajouter(t);
        service.supprimer(t.getId_transaction());

        // test réussi s'il n'y a pas d'exception
        assertTrue(true);
    }
}
