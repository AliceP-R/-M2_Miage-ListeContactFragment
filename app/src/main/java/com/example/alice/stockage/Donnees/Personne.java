package com.example.alice.stockage.Donnees;

import com.orm.SugarRecord;

/**
 * Created by Alice on 19/12/2015.
 */

public class Personne extends SugarRecord {

    private String nom;
    private String prenom;
    private String numero;


    public Personne(){}

    public Personne(String nom, String numero, String prenom) {
        this.nom = nom;
        this.numero = numero;
        this.prenom = prenom;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
