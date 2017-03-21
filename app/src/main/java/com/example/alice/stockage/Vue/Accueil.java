package com.example.alice.stockage.Vue;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.alice.stockage.R;
import com.orm.SugarContext;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        SugarContext.init(getApplicationContext());
    }

    public void onClickAjout(View v)
    {
            Intent changement = new Intent(this, Ajout.class);
            startActivity(changement);
    }

    public void onClickVisu(View v)
    {
       Intent direction = new Intent(this, ListeContact.class);
        startActivity(direction);
    }

    public void onClickRecherche(View v)
    {
        Intent changement = new Intent(this, Rechercher.class);
        startActivity(changement);
    }
}
