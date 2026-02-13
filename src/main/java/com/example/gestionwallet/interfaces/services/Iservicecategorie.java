package com.example.gestionwallet.interfaces.services;

import com.example.gestionwallet.models.categorie;
import java.util.List;

public interface Iservicecategorie {

    void ajouter(categorie c);
    void modifier(categorie c);
    void supprimer(int id);
    List<categorie> afficher();

}
