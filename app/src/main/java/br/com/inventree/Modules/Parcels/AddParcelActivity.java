package br.com.inventree.Modules.Parcels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class AddParcelActivity extends AppCompatActivity {
    private EditText etParcelName, etTreesQuantity, etNotes;
    private Button btSave;
    private String parcelId = null;
    private String inventoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parcel);

        etParcelName = (EditText)findViewById(R.id.et_parcel_name);
        etTreesQuantity = (EditText)findViewById(R.id.et_trees_quantity);
        etNotes = (EditText)findViewById(R.id.et_parcel_notes);
        btSave = (Button)findViewById(R.id.bt_save_parcel);


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParcelData();
            }
        });

        Intent parameters = getIntent();
        if(parameters.hasExtra("parcel_id")){
            parcelId = parameters.getStringExtra("parcel_id");
            Log.e("PARCEL ID","PARCEL ID-> "+parcelId);
        }
        if(parameters.hasExtra("parcel_name")){
            etParcelName.setText(parameters.getStringExtra("parcel_name"));
        }
        if(parameters.hasExtra("parcel_tree_quantity")){
            etTreesQuantity.setText(String.valueOf(parameters.getStringExtra("parcel_tree_quantity")));
        }
        if(parameters.hasExtra("parcel_notes")){
            Log.e("PARAMS","NOTES-> "+(parameters.getStringExtra("parcel_notes")));
            etNotes.setText(parameters.getStringExtra("parcel_notes"));
        }
        if(parameters.hasExtra("inventory_id")){
            inventoryId = parameters.getStringExtra("inventory_id");
            Log.e("PARCEL","INVENTORY ID-> "+inventoryId);
        }
    }

    public boolean validateParcelRequiredData(){
        return String.valueOf(etParcelName.getText()).equals("") &&
                String.valueOf(etTreesQuantity.getText()).equals("");
    }

    public void saveParcelData(){
        try{
            if(!validateParcelRequiredData()){
                Parcel parcel = new Parcel();
                //REQUIRED
                parcel.setName(String.valueOf(etParcelName.getText()));
                parcel.setTreesQuantity(Integer.parseInt(String.valueOf(etTreesQuantity.getText())));
                parcel.setNotes(String.valueOf(etNotes.getText()));
                parcel.setInventoryId(Integer.parseInt(inventoryId));

                Dao dao = new Dao(this);
                if(parcelId != null){
                    parcel.setId(Integer.parseInt(parcelId));
                    dao.updateParcel(parcel);
                }else{
                    dao.insertParcel(parcel);
                }
                cleanEditTextFields();
            }else{
                Toast.makeText(this, "Fill in the required fields!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "There was an error while saving parcel data", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanEditTextFields(){
        etParcelName.setText(null);
        etTreesQuantity.setText(null);
        etNotes.setText(null);
        finish();
    }
}