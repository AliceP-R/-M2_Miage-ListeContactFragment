package com.example.alice.stockage.Vue;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.Personnalisation.ContactAdapter;
import com.example.alice.stockage.R;

import java.util.ArrayList;

public class ListeContact extends ListActivity {

    private ArrayList lPersonne;
    private ContactAdapter adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_contact);
        lPersonne = new ArrayList<>();

        try {
            lPersonne = (ArrayList) Personne.listAll(Personne.class);
            adapterContact = new ContactAdapter(this, lPersonne);
            setListAdapter(adapterContact);
        }catch(SQLiteException sqle)
        {
            Toast.makeText(getApplicationContext(), "Vous n'avez aucun contact à afficher.", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRetour(View v)
    {
        Intent annulation = new Intent(this, Accueil.class);
        startActivity(annulation);
    }


}
