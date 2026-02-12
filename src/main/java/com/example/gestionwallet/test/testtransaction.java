package com.example.gestionwallet.test;

import com.example.gestionwallet.models.categorie;
import com.example.gestionwallet.models.transaction;
import com.example.gestionwallet.services.servicecategorie;
import com.example.gestionwallet.services.servicetransaction;

import java.sql.Date;
import java.util.List;

public class testtransaction {

    public static void main(String[] args) {

        servicecategorie sc = new servicecategorie();
        servicetransaction st = new servicetransaction();

        // 1️⃣ نعمل Category جديدة
        categorie cat = new categorie("TestCategory", "HAUTE", "INCOME");
        sc.ajouter(cat);

        // 2️⃣ نجيب آخر Category ID موجود
        List<categorie> categories = sc.afficher();
        int lastCategoryId = categories.get(categories.size() - 1).getId_category();

        // 3️⃣ نعمل Transaction مربوطة بيها
        transaction t1 = new transaction(
                "Freelance Work",      // nom_transaction
                "INCOME",
                200.0,
                Date.valueOf("2026-02-12"),
                "MANUAL",
                1,
                lastCategoryId   // ✅ ID صحيح موجود
        );

        st.ajouter(t1);

        // 4️⃣ نعرض Transactions
        System.out.println("===== LISTE TRANSACTIONS =====");
        st.afficher().forEach(System.out::println);

        // 5️⃣ Update
        transaction t2 = new transaction(
                1,
                "Freelance Updated",
                "INCOME",
                250.0,
                Date.valueOf("2026-02-13"),
                "MANUAL",
                1,
                lastCategoryId
        );

        st.modifier(t2);

        System.out.println("===== APRES UPDATE =====");
        st.afficher().forEach(System.out::println);

        // 6️⃣ Delete (اختياري)
        // st.supprimer(t2.getId_transaction());
    }
}
