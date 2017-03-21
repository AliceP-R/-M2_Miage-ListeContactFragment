package com.example.alice.stockage.Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.Personnalisation.ContactAdapter;
import com.example.alice.stockage.R;

import java.util.ArrayList;

public class Rechercher extends AppCompatActivity {

    private ArrayList lPersonne;
    private ArrayList lRes;
    private ContactAdapter adapterContact;

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView tvID = (TextView) view.findViewById(R.id.tvId);
            String text = tvID.getText().toString();
            Intent intent = new Intent(parent.getContext(), Modification.class);
            intent.putExtra("select-id", text);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);

        EditText recherche=(EditText) findViewById(R.id.txtRecherche);
        recherche.setText("");

        // On réinitialise toutes les valeurs pour éviter que la recherche se fasse même à la première ouverture de l'activité
        lPersonne = new ArrayList();
        lRes = new ArrayList();

        lPersonne.clear();
        lRes.clear();

        adapterContact = new ContactAdapter(this, lPersonne);
        adapterContact.notifyDataSetChanged();
        ListView lv = (ListView) findViewById(R.id.lview);
        lv.setAdapter(adapterContact);
        lv.setOnItemClickListener(new ListClickHandler());
    }

    private void recherche()
    {
        lPersonne.clear();
        lRes.clear();
        EditText recherche=(EditText) findViewById(R.id.txtRecherche); // Recherche
        lRes = (ArrayList) Personne.find(Personne.class, "nom LIKE '%"
                + recherche.getText() + "' OR prenom LIKE '%" + recherche.getText()+"'" );

        if(lRes.isEmpty())
            Toast.makeText(getApplicationContext(), "Aucun résultat", Toast.LENGTH_LONG).show();

        else {

            for (Personne lRe : (Iterable<Personne>) lRes) {
                lPersonne.add(lRe);
            }

            adapterContact.notifyDataSetChanged();
        }
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        recherche();


    }

    public void onClickRecherche(View v)
    {
        try
        {
            recherche();
        }
        catch (Exception e){
            Log.e("Main : ", e.toString());
        }
    }

    public void onClickRetour(View v)
    {
        Intent annulation = new Intent(this, Accueil.class);
        startActivity(annulation);
    }
}
