package br.com.inventree.Modules.Trees;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class TreesListActivity extends AppCompatActivity {
    private ListView listView;
    private TextView tvEmpty;
    private Button btNewTree;
    private TreesListAdapter listAdapter;
    private ArrayList<Tree> list = new ArrayList<>();
    private Dao dao;
    private String inventoryId = null, parcelId = null;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_list);

        Intent parameters = getIntent();
        if(parameters.hasExtra("inventory_id")){
            inventoryId = parameters.getStringExtra("inventory_id");
        }
        if(parameters.hasExtra("parcel_id")){
            parcelId = parameters.getStringExtra("parcel_id");
        }

        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        btNewTree = (Button)findViewById(R.id.bt_add_tree);
        listView = (ListView) findViewById(R.id.trees_list);
        listView.setDivider(null);
        tvEmpty.setVisibility(View.INVISIBLE);

        if(list.size() > 0){
            listView.setAdapter(listAdapter);
        }else{
            listView.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        }

        btNewTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTreeActivity.class);
                intent.putExtra("tree_inventory_id",inventoryId);
                intent.putExtra("tree_parcel_id",parcelId);
                startActivity(intent);
            }
        });

        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("refresh_trees_list")) {
                        onResume();
                    }
                }
            };
            registerReceiver(broadcastReceiver,new IntentFilter("refresh_trees_list"));
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if(list != null){
            list.clear();
        }
        createList();
    }

    public void createList(){
        if(dao == null){
            dao = new Dao(this);
        }

        if(inventoryId != null) {
            list.addAll( dao.listTrees(Integer.parseInt(inventoryId), Integer.parseInt(parcelId)));
        }

        if(listAdapter == null){
            listAdapter = new TreesListAdapter(this,R.layout.item_parcels_list, list);
        }

        if (list.size() > 0) {
            listView.setAdapter(listAdapter);
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        } else {
            listView.setVisibility(View.INVISIBLE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
}