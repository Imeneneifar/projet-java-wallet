package com.example.gestionwallet.test;

import com.example.gestionwallet.models.categorie;
import com.example.gestionwallet.services.servicecategorie;

public class testcategorie {

    public static void main(String[] args) {

        servicecategorie sc = new servicecategorie();

        // ADD
        categorie c1 = new categorie("Transport", "MOYENNE");
        categorie c3 = new categorie("car", "HAUTE");

        sc.ajouter(c3);


        // UPDATE
        categorie c2 = new categorie(1, "Groceries", "HAUTE");
        sc.modifier(c2);

        // READ
        sc.afficher().forEach(System.out::println);

        // DELETE
        sc.supprimer(9);

        // sc.supprimer(3);
    }
}
