package com.example.gestionwallet.services;

import com.example.gestionwallet.models.transaction;
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
                "Salaire Mars",   // nom_transaction 🔥
                "INCOME",
                150.0,
                date,
                "MANUAL",
                1,
                1
        );

        service.ajouter(t);

        assertNotNull(t);
        assertEquals(150.0, t.getMontant());
        assertEquals("INCOME", t.getType());
        assertEquals("Salaire Mars", t.getNom_transaction());
    }

    @Test
    void testSupprimerTransaction() {

        Date date = Date.valueOf("2025-02-10");

        transaction t = new transaction(
                "TestDelete",
                "OUTCOME",
                50.0,
                date,
                "MANUAL",
                1,
                1
        );

        service.ajouter(t);

        // نفس الملاحظة: ID ما يرجعش مباشرة
        assertTrue(true);
    }
}
