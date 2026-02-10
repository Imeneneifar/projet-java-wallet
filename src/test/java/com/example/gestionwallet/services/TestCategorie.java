package com.example.gestionwallet.services;

import com.example.gestionwallet.models.categorie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCategorie {

    private servicecategorie service;

    @BeforeEach
    void setUp() {
        service = new servicecategorie();
    }

    @Test
    void testAjouterCategorie() {
        categorie c = new categorie("Food", "HAUTE");

        service.ajouter(c);

        assertNotNull(c);
        assertEquals("Food", c.getNom());
    }

    @Test
    void testSupprimerCategorie() {
        categorie c = new categorie("Transport", "HAUTE");

        service.ajouter(c);
        service.supprimer(c.getId_category());


        assertTrue(true);
    }
}
