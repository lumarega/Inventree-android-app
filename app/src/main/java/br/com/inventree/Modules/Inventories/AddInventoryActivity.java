package br.com.inventree.Modules.Inventories;

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

public class AddInventoryActivity extends AppCompatActivity {
    private EditText etInventoryName, etArea, etInventoryDate, etParcelsArea, etInlineSpace, etBetweenLineSpace, etPlantationDate, etSpecies;
    private Button btSave;
    private String inventoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);

        etInventoryName = (EditText)findViewById(R.id.et_inventory_name);
        etArea = (EditText)findViewById(R.id.et_area);
        etInventoryDate = (EditText)findViewById(R.id.et_inventory_date);
        etParcelsArea = (EditText)findViewById(R.id.et_parcels_area);
        etInlineSpace = (EditText)findViewById(R.id.et_inline_space);
        etBetweenLineSpace = (EditText)findViewById(R.id.et_between_line_space);
        etPlantationDate = (EditText)findViewById(R.id.et_plantation_date);
        etSpecies = (EditText)findViewById(R.id.et_species);
        btSave = (Button)findViewById(R.id.bt_save_inventory);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInventoryData();
            }
        });

        Intent parameters = getIntent();
        if(parameters.hasExtra("inventory_id")){
            inventoryId = parameters.getStringExtra("inventory_id");
            Log.e("INVENTORY ID","INVENTORY ID-> "+inventoryId);
        }
        if(parameters.hasExtra("inventory_name")){
           etInventoryName.setText(parameters.getStringExtra("inventory_name"));
        }
        if(parameters.hasExtra("inventory_area")){
            etArea.setText(String.valueOf(parameters.getStringExtra("inventory_area")));
        }
        if(parameters.hasExtra("inventory_date")){
            etInventoryDate.setText(parameters.getStringExtra("inventory_date"));
        }
        if(parameters.hasExtra("species")){
            etSpecies.setText(parameters.getStringExtra("species"));
        }
        if(parameters.hasExtra("plantation_date")){
            etPlantationDate.setText(parameters.getStringExtra("plantation_date"));
        }
        if(parameters.hasExtra("parcels_area")){
            etParcelsArea.setText(parameters.getStringExtra("parcels_area"));
        }
        if(parameters.hasExtra("inline_space")){
            etInlineSpace.setText(parameters.getStringExtra("inline_space"));
        }
        if(parameters.hasExtra("between_line_space")){
            etBetweenLineSpace.setText(parameters.getStringExtra("between_line_space"));
        }
    }

    public boolean validateInventoryRequiredData(){
        return String.valueOf(etArea.getText()).equals("") && String.valueOf(etInventoryName.getText()).equals("") &&
                String.valueOf(etInventoryDate.getText()).equals("") && String.valueOf(etParcelsArea.getText()).equals("");
    }

    public void saveInventoryData(){
        try{
            if(!validateInventoryRequiredData()){
                Inventory inventory = new Inventory();
                //REQUIRED
                inventory.setName(String.valueOf(etInventoryName.getText()));
                inventory.setArea(Double.parseDouble(String.valueOf(etArea.getText())));
                inventory.setInventoryDate(String.valueOf(etInventoryDate.getText()));
                inventory.setParcelsArea(Double.parseDouble(String.valueOf(etParcelsArea.getText())));
                //NOT REQUIRED
                inventory.setPlantationDate(String.valueOf(etPlantationDate.getText()));
                if(!String.valueOf(etInlineSpace.getText()).equals("")){
                    inventory.setInlineSpace(Double.parseDouble(String.valueOf(etInlineSpace.getText())));
                }
                if(!String.valueOf(etBetweenLineSpace.getText()).equals("")){
                    inventory.setBetweenLineSpace(Double.parseDouble(String.valueOf(etBetweenLineSpace.getText())));
                }
                inventory.setSpecies(String.valueOf(etSpecies.getText()));

                Dao dao = new Dao(this);
                if(inventoryId != null){
                    inventory.setId(Integer.parseInt(inventoryId));
                    dao.updateInventory(inventory);
                }else{
                    dao.insertInventory(inventory);
                }
                cleanEditTextFields();
            }else{
                Toast.makeText(this, "Fill in the required fields!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "There was an error while saving Inventory data", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanEditTextFields(){
        etInventoryDate.setText(null);
        etArea.setText(null);
        etInventoryName.setText(null);
        etParcelsArea.setText(null);
        etInlineSpace.setText(null);
        etBetweenLineSpace.setText(null);
        etPlantationDate.setText(null);
        etSpecies.setText(null);
        finish();
    }
}
