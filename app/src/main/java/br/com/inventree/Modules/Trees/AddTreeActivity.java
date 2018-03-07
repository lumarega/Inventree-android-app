package br.com.inventree.Modules.Trees;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class AddTreeActivity extends AppCompatActivity {
    private EditText etTreeName, etTreeHeight, etTreeCap, etTreeDap, etTreeNotes;
    private Button btSave;
    private String parcelId = null;
    private String inventoryId = null;
    private String treeId = null;
    //private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        //df = new DecimalFormat("#.####");
        //df.setRoundingMode(RoundingMode.CEILING);

        etTreeName = (EditText)findViewById(R.id.et_tree_name);
        etTreeHeight = (EditText)findViewById(R.id.et_tree_height);
        etTreeCap = (EditText)findViewById(R.id.et_tree_cap);
        etTreeDap = (EditText)findViewById(R.id.et_tree_dap);
        etTreeNotes = (EditText)findViewById(R.id.et_tree_notes);
        btSave = (Button)findViewById(R.id.bt_save_tree);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTreeData();
            }
        });

        Intent parameters = getIntent();
        if(parameters.hasExtra("tree_id")){
            treeId = parameters.getStringExtra("tree_id");
            Log.e("TREE ID","TREE ID-> "+treeId);
        }
        if(parameters.hasExtra("tree_inventory_id")){
            inventoryId = parameters.getStringExtra("tree_inventory_id");
            Log.e("TREE","INVENTORY ID-> "+inventoryId);
        }
        if(parameters.hasExtra("tree_parcel_id")){
            parcelId = parameters.getStringExtra("tree_parcel_id");
            Log.e("TREE","parcel ID-> "+parcelId);
        }
        if(parameters.hasExtra("tree_name")){
            etTreeName.setText(parameters.getStringExtra("tree_name"));
        }
        if(parameters.hasExtra("tree_height")){
            etTreeHeight.setText(parameters.getStringExtra("tree_height"));
        }
        if(parameters.hasExtra("tree_cap")){
            //Log.e("TESTE","CAP-> "+ );
            etTreeCap.setText(parameters.getStringExtra("tree_cap"));
        }
        if(parameters.hasExtra("tree_dap")){
            etTreeDap.setText(parameters.getStringExtra("tree_dap"));
        }
        if(parameters.hasExtra("tree_notes")){
            etTreeNotes.setText(parameters.getStringExtra("tree_notes"));
        }

        etTreeCap.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    double cap = Double.parseDouble(s.toString());
                    double dap = cap/3.1416;
                    etTreeDap.setText(String.valueOf(dap));
                }else{
                    etTreeDap.setText(null);
                }
            }
        });

    }

    public boolean validateParcelRequiredData(){
        return String.valueOf(etTreeName.getText()).equals("") && String.valueOf(etTreeHeight.getText()).equals("") &&
                String.valueOf(etTreeCap.getText()).equals("") && String.valueOf(etTreeDap.getText()).equals("");
    }

    public void saveTreeData(){
      ////  try{
            if(!validateParcelRequiredData()){
                double formfactor = 0.5, radius_meters = 0, height_meters = 0, cilinderVolume = 0, treeVolume = 0;

                radius_meters = (Double.parseDouble(String.valueOf(etTreeDap.getText()))/2)/100;
                height_meters = Double.parseDouble(String.valueOf(etTreeHeight.getText()));
                cilinderVolume = 3.1416 * Math.pow(radius_meters,2) * height_meters;
                treeVolume = cilinderVolume * formfactor;

                Log.w("TESTE","radius_meters->"+radius_meters);
                Log.w("TESTE","height_meters->"+height_meters);
                Log.w("TESTE","cilinderVolume->"+cilinderVolume);
                Log.w("TESTE","treeVolume->"+treeVolume);

                Tree tree = new Tree();
                tree.setName(String.valueOf(etTreeName.getText()));
                tree.setHeight(height_meters);
                tree.setCap(Double.parseDouble(String.valueOf(etTreeCap.getText())));
                tree.setDap(Double.parseDouble(String.valueOf(etTreeDap.getText())));
                tree.setNotes(String.valueOf(etTreeNotes.getText()));
                tree.setWoodVolume(treeVolume);

                if(inventoryId != null){
                    tree.setInventoryId(Integer.parseInt(inventoryId));
                }
                if(parcelId != null){
                    tree.setParcelId(Integer.parseInt(parcelId));
                }

                Dao dao = new Dao(this);
                if(treeId != null){
                    Log.e("TREE","update");
                    tree.setId(Integer.parseInt(treeId));
                    dao.updateTree(tree);
                }else{
                    Log.e("TREE","insert");
                    dao.insertTree(tree);
                }
                cleanEditTextFields();
            }else{
                Toast.makeText(this, "Fill in the required fields!", Toast.LENGTH_SHORT).show();
            }
      //  }catch(Exception e){
        //    Log.e("ERROR","saveTreeData-> "+e);
        //    Toast.makeText(this, "There was an error while saving tree data", Toast.LENGTH_SHORT).show();
       // }
    }

    private void cleanEditTextFields(){
        etTreeName.setText(null);
        etTreeHeight.setText(null);
        etTreeCap.setText(null);
        etTreeDap.setText(null);
        etTreeNotes.setText(null);
        finish();
    }
}
