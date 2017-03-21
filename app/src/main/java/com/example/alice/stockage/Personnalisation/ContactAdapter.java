package com.example.alice.stockage.Personnalisation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alice.stockage.Donnees.Personne;
import com.example.alice.stockage.R;

import java.util.ArrayList;

/**
 * Created by Alice on 19/12/2015.
 */
public class ContactAdapter extends BaseAdapter {

    private ArrayList<Personne> listeContact;
    private Context contexte;

    // gestion de l'affichage
    private LayoutInflater gestionAffichage;

    public ContactAdapter(Context contexte, ArrayList<Personne> liste) {
        this.contexte = contexte;
        this.listeContact = liste;
        this.gestionAffichage = LayoutInflater.from(this.contexte);
    }

    @Override
    public int getCount() {
        return listeContact.size();
    }

    @Override
    public Object getItem(int position) {
        return listeContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layoutItem;

        //region Création du layout en fonction du notre (layout_contact.xml)
        if (convertView == null)
        {
            layoutItem = (RelativeLayout) gestionAffichage.inflate(R.layout.layout_contact, parent, false);
        } else {
            layoutItem = (RelativeLayout) convertView;
        }
        //endregion

        //region Recup des champs
        TextView tvNom = (TextView)layoutItem.findViewById(R.id.tvNom);
        TextView tvPrenom = (TextView)layoutItem.findViewById(R.id.tvPrenom);
        TextView tvTel = (TextView)layoutItem.findViewById(R.id.tvTel);
        TextView tvId = (TextView) layoutItem.findViewById(R.id.tvId);
        //endregion

        //region Modification des champs
        tvNom.setText(listeContact.get(position).getNom());
        tvPrenom.setText(listeContact.get(position).getPrenom());
        tvTel.setText(listeContact.get(position).getNumero());
        tvId.setText(listeContact.get(position).getId().toString());
        //endregion

        return layoutItem;
    }
}
