package org.esprit.services;

import java.util.List;

public interface IService<T> {

    void Ajouter (T t) ;
    List<T> Afficher() ;
    void Modifier (T t) ;
    void Supprimer (int id) ;


}
