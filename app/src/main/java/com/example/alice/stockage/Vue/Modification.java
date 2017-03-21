package com.example.alice.stockage.Vue;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.R;

import java.util.List;

import static android.support.v7.app.AlertDialog.*;

/**
 * Created by alice on 17/01/2017.
 */

public class Modification extends AppCompatActivity
{

    Long idPersonne;
    Personne base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification);
        Intent reception = getIntent();
        idPersonne = Long.parseLong(reception.getStringExtra("select-id"));

        List<Personne> res = Personne.find(Personne.class, "id = " + idPersonne.toString());
        base = res.get(0);

        EditText etNom = (EditText) findViewById(R.id.txtNomM);
        EditText etPrenom = (EditText) findViewById(R.id.txtPrenomM);
        EditText etTel = (EditText) findViewById(R.id.txtTelM);

        etNom.setText(base.getNom());
        etPrenom.setText(base.getPrenom());
        etTel.setText(base.getNumero());
    }

    public void onClickModif(View v)
    {
        EditText etNom = (EditText) findViewById(R.id.txtNomM);
        EditText etPrenom = (EditText) findViewById(R.id.txtPrenomM);
        EditText etTel = (EditText) findViewById(R.id.txtTelM);

        String nomModif = etNom.getText().toString();
        String prenomModif = etPrenom.getText().toString();
        String telModif = etTel.getText().toString();

        // si click modif mais informations inchangée
        if(nomModif.equals(base.getNom()) && prenomModif.equals(base.getPrenom()) && telModif.equals(base.getNumero()))
        {
            Toast.makeText(getApplicationContext(), "Vous n'avez fait aucune modification !", Toast.LENGTH_LONG).show();
        }
        else
        {
            base.setNom(nomModif);
            base.setPrenom(prenomModif);
            base.setNumero(telModif);
            base.save();

            Toast.makeText(getApplicationContext(), "Modification faite !", Toast.LENGTH_LONG).show();

            finish();
        }
    }

    public void onClickSuppr(View v)
    {
        Builder builder = new Builder(this);
        builder.setTitle("Item");

        builder.setMessage("Voulez vous vraiment supprimer ce contact ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                base.delete();
                Toast.makeText(getApplicationContext(), "Suppression faite !", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton("Non", null);
        builder.show();
    }

    // Permet de retourner sur l'activité précédente donc sur Recherche
    public void onClickRetourRecherche(View v)
    {

        finish();
    }
}
