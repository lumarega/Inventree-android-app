package br.com.inventree.Modules.Parcels;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import br.com.inventree.Modules.Inventories.AddInventoryActivity;
import br.com.inventree.Modules.Trees.TreesListActivity;
import br.com.inventree.Modules.Trees.TreesListAdapter;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class ParcelsListAdapter extends ArrayAdapter<Parcel> {
    ArrayList<Parcel> parcelsList = new ArrayList<>();

    public ParcelsListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Parcel> parcels) {
        super(context, resource, parcels);
        parcelsList = parcels;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        View v = view;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_parcels_list, null);
        TextView tvParcelName = (TextView) v.findViewById(R.id.tv_parcel_name);
        TextView tvParcelTreeQuantity = (TextView) v.findViewById(R.id.tv_parcel_tree_qtt);
        TextView tvParcelNotes = (TextView) v.findViewById(R.id.tv_parcel_notes);
        TextView tvParcelWoodVolume = (TextView)v.findViewById(R.id.tv_parcel_average_wood_volume);
        tvParcelName.setText(parcelsList.get(position).getName());
        tvParcelTreeQuantity.setText(String.valueOf(parcelsList.get(position).getTreesQuantity()));
        tvParcelNotes.setText(parcelsList.get(position).getNotes());
        tvParcelWoodVolume.setText(String.valueOf(parcelsList.get(position).getTreesAverageWoodVolume()));

        Button btEnter = (Button)v.findViewById(R.id.bt_enter);
        Button btEdit = (Button)v.findViewById(R.id.bt_edit);
        Button btDelete = (Button) v.findViewById(R.id.bt_delete);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openTreesList = new Intent(getContext(), TreesListActivity.class);
                openTreesList.putExtra("parcel_id", String.valueOf(parcelsList.get(position).getId()));
                openTreesList.putExtra("inventory_id", String.valueOf(parcelsList.get(position).getInventoryId()));
                v.getContext().startActivity(openTreesList);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editParcel = new Intent(getContext(), AddParcelActivity.class);
                editParcel.putExtra("parcel_id", String.valueOf(parcelsList.get(position).getId()));
                editParcel.putExtra("parcel_name", String.valueOf(parcelsList.get(position).getName()));
                editParcel.putExtra("parcel_notes", String.valueOf(parcelsList.get(position).getNotes()));
                editParcel.putExtra("parcel_tree_quantity", String.valueOf(parcelsList.get(position).getTreesQuantity()));
                editParcel.putExtra("inventory_id", String.valueOf(parcelsList.get(position).getInventoryId()));
                v.getContext().startActivity(editParcel);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao dao = new Dao(getContext());
                dao.deleteParcel(parcelsList.get(position), getContext());
            }
        });

        return v;
    }
}

