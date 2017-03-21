package com.example.alice.stockage.Vue;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.Fragment.*;
import com.example.alice.stockage.Personnalisation.ContactAdapter;
import com.example.alice.stockage.R;

import java.util.ArrayList;
import java.util.List;

public class Rechercher extends AppCompatActivity {

    private ArrayList lPersonne;
    private ArrayList lRes;
    private ContactAdapter adapterContact;

    private FragmentTransaction transaction;
    private FragModifier fragModif;

    Personne base;

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView tvID = (TextView) view.findViewById(R.id.tvId);
            String text = tvID.getText().toString();

            // si on est en portrait, on utilise la méthode "classique" d'appeler une autre activité
            if (findViewById(R.id.idContenaireFrag) == null) {
                Intent intent = new Intent(parent.getContext(), Modification.class);
                intent.putExtra("select-id", text);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "frag", Toast.LENGTH_SHORT).show();



                List<Personne> res = Personne.find(Personne.class, "id = " + text);
                base = res.get(0);

                EditText etNomF = (EditText) findViewById(R.id.txtNomMF);
                EditText etPrenomF = (EditText) findViewById(R.id.txtPrenomMF);
                EditText etTelF = (EditText) findViewById(R.id.txtTelMF);

                etNomF.setText(base.getNom());
                etPrenomF.setText(base.getPrenom());
                etTelF.setText(base.getNumero());

                transaction = getFragmentManager().beginTransaction();
                transaction.show(fragModif);
                transaction.commit();
            }
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

        fragModif = new FragModifier();

        // si le container est créé, on ajoute le fragment
        if (findViewById(R.id.idContenaireFrag) != null) {
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.idContenaireFrag,fragModif);
            transaction.addToBackStack(null);
            transaction.hide(fragModif);
            transaction.commit();

            Button modif=(Button)findViewById(R.id.bModifF) ;
            modif.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText etNom = (EditText) findViewById(R.id.txtNomMF);
                    EditText etPrenom = (EditText) findViewById(R.id.txtPrenomMF);
                    EditText etTel = (EditText) findViewById(R.id.txtTelMF);

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

                        transaction = getFragmentManager().beginTransaction();
                        transaction.hide(fragModif);
                        transaction.commit();

                        adapterContact.notifyDataSetChanged();
                    }
                }
            });

            Button suppr=(Button)findViewById(R.id.bSupprF) ;
            suppr.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
            });

            Button annul=(Button)findViewById(R.id.bAnnuleF) ;
            annul.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText etNomF = (EditText) findViewById(R.id.txtNomMF);
                    EditText etPrenomF = (EditText) findViewById(R.id.txtPrenomMF);
                    EditText etTelF = (EditText) findViewById(R.id.txtTelMF);

                    etNomF.setText("");
                    etPrenomF.setText("");
                    etTelF.setText("");

                    transaction = getFragmentManager().beginTransaction();
                    transaction.hide(fragModif);
                    transaction.commit();
                }
            });
        }
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
