package com.example.gestionwallet.interfaces.services;

import com.example.gestionwallet.models.transaction;
import java.util.List;

public interface Iservicetransaction {

    void ajouter(transaction t);
    void modifier(transaction t);
    void supprimer(int id);
    List<transaction> afficher();
}
