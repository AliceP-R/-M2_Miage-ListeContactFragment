package com.example.alice.stockage.Vue;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.R;

public class Ajout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
    }


    public void onClickStock(View v)
    {
        EditText etNom = (EditText) findViewById(R.id.txtNom);
        EditText etPrenom = (EditText) findViewById(R.id.txtPrenom);
        EditText etTel = (EditText) findViewById(R.id.txtTel);

        String nom = etNom.getText().toString();
        String prenom = etPrenom.getText().toString();
        String tel = etTel.getText().toString();

        if(nom.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Vous avez oublié de rentrer votre nom !", Toast.LENGTH_LONG).show();
            return;
        }
        if(prenom.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Vous avez oublié de rentrer votre prénom !", Toast.LENGTH_LONG).show();
            return;
        }
        if(tel.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Vous avez oublié de rentrer votre numéro !", Toast.LENGTH_LONG).show();
            return;
        }

        try
        {
            Personne p = new Personne(nom, tel, prenom);
            p.save(); // ajoute à la BDD

            Toast.makeText(getApplicationContext(), "Ajout réussi avec Sugar!", Toast.LENGTH_LONG).show();

            etNom.setText("");
            etPrenom.setText("");
            etTel.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClickAnnule(View v)
    {
        Intent annulation = new Intent(this, Accueil.class);
        startActivity(annulation);
    }
}
