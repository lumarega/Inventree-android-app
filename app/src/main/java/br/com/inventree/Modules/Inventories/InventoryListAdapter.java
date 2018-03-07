package br.com.inventree.Modules.Inventories;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import br.com.inventree.Modules.Parcels.ParcelsListActivity;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class InventoryListAdapter extends ArrayAdapter<Inventory> {
    ArrayList<Inventory> inventoriesList = new ArrayList<>();

    public InventoryListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<Inventory> inventories) {
        super(context, resource, inventories);
        inventoriesList = inventories;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.item_invetories_list, null);
        TextView tvName = (TextView) v.findViewById(R.id.tv_inv_name);
        TextView tvDate = (TextView) v.findViewById(R.id.tv_inv_date);
        TextView tvArea = (TextView) v.findViewById(R.id.tv_inv_area);
        TextView tvSpecies = (TextView) v.findViewById(R.id.tv_inv_species);
        TextView tvPlantationDate = (TextView) v.findViewById(R.id.tv_inv_plantation_date);
        TextView tvParcelsArea = (TextView) v.findViewById(R.id.tv_inv_parcels_area);
        TextView tvVolumeArea = (TextView) v.findViewById(R.id.tv_volume_area);
        //ADD CAMPOS: tv_volume_hectates, tv_volume_area

        tvDate.setText(inventoriesList.get(position).getInventoryDate());
        tvName.setText(inventoriesList.get(position).getName());
        tvArea.setText(String.valueOf(inventoriesList.get(position).getArea()));
        tvSpecies.setText(inventoriesList.get(position).getSpecies());
        tvPlantationDate.setText(inventoriesList.get(position).getPlantationDate());
        tvParcelsArea.setText(String.valueOf(inventoriesList.get(position).getParcelsArea()));
        tvVolumeArea.setText(String.valueOf(inventoriesList.get(position).getWoodVolume()));

        Button btEnter = (Button)v.findViewById(R.id.bt_enter);
        Button btEdit = (Button)v.findViewById(R.id.bt_edit);
        Button btDelete = (Button) v.findViewById(R.id.bt_delete);
        Button btEmail = (Button) v.findViewById(R.id.bt_email);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editInventory = new Intent(getContext(), AddInventoryActivity.class);
                editInventory.putExtra("inventory_id", String.valueOf(inventoriesList.get(pos).getId()));
                editInventory.putExtra("inventory_date", inventoriesList.get(pos).getInventoryDate());
                editInventory.putExtra("inventory_name", inventoriesList.get(pos).getName());
                editInventory.putExtra("inventory_area", String.valueOf(inventoriesList.get(pos).getArea()));
                editInventory.putExtra("species", inventoriesList.get(pos).getSpecies());
                editInventory.putExtra("plantation_date", inventoriesList.get(pos).getPlantationDate());
                editInventory.putExtra("parcels_area", String.valueOf(inventoriesList.get(pos).getParcelsArea()));
                editInventory.putExtra("inline_space", String.valueOf(inventoriesList.get(pos).getInlineSpace()));
                editInventory.putExtra("between_line_space", String.valueOf(inventoriesList.get(pos).getBetweenLineSpace()));
                v.getContext().startActivity(editInventory);
            }
        });

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goParcelsView = new Intent(getContext(), ParcelsListActivity.class);
                goParcelsView.putExtra("inventory_id",String.valueOf(inventoriesList.get(pos).getId()));
                v.getContext().startActivity(goParcelsView);
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao dao = new Dao(getContext());
                dao.deleteInventory(inventoriesList.get(pos), getContext());
            }
        });

        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dao dao = new Dao(getContext());
                String dataToCreateFile = dao.getInventoryDataToCreateFile(inventoriesList.get(pos).getId());
                Log.w("DATA TO CREATE FILE","DATA TO CREATE FILE-> \n"+dataToCreateFile);


                File file = new File(getContext().getExternalFilesDir(null), "/inventree.csv");
                Log.w("FILES PATH", "getContext().getFilesDir()-> "+getContext().getFilesDir());

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    out.write(dataToCreateFile.getBytes(), 0, dataToCreateFile.getBytes().length);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    Log.e("E","FileNotFoundException");
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.e("E","IOException");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Uri u1  =   null;
                u1  =   Uri.fromFile(file);

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Planilha Inventree");
                sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
                sendIntent.setType("text/html");
                v.getContext().startActivity(sendIntent);
            }
        });

        return v;
    }
}


//  File file = new File(caminho.getPath()+"/arquivo.csv");
             /*
                File file   = null;
                File root   = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    File dir    =   new File (root.getAbsolutePath() + "/Inventree");
                    dir.mkdirs();
                    file   =   new File(dir, "plan_teste1.csv");
                    FileOutputStream out   =   null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
               Uri u1  =   null;
               u1  =   Uri.fromFile(file);
*/