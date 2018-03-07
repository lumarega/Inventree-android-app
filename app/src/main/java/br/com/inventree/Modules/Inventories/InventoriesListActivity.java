package br.com.inventree.Modules.Inventories;

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
import java.util.ArrayList;
import br.com.inventree.R;
import br.com.inventree.Storage.SQLiteDb.Dao;

public class InventoriesListActivity extends AppCompatActivity{
    private ListView listView;
    private TextView tvEmpty;
    private Button btNewInventory;
    private InventoryListAdapter listAdapter;
    private ArrayList<Inventory> list = new ArrayList<>();
    private Dao dao;
    private BroadcastReceiver broadcastReceiver;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventories_list);

            //adicionar filtro de boradcast receiver para fazer update da tela

            tvEmpty = (TextView) findViewById(R.id.tv_empty);
            btNewInventory = (Button)findViewById(R.id.bt_add_inventory);
            listView = (ListView) findViewById(R.id.inventories_list);
            listView.setDivider(null);
            tvEmpty.setVisibility(View.INVISIBLE);

            btNewInventory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AddInventoryActivity.class);
                    startActivity(intent);
                }
            });

            if(broadcastReceiver == null){
                broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent.getAction().equals("refresh_inventories_list")) {
                            onResume();
                        }
                    }
                };
                registerReceiver(broadcastReceiver,new IntentFilter("refresh_inventories_list"));
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

        //list = dao.listInventory();
        list.addAll(dao.listInventory());

        if(listAdapter == null){
            listAdapter = new InventoryListAdapter(this,R.layout.item_invetories_list, list);
        }

        if(list.size() > 0){
            listView.setAdapter(listAdapter);
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.INVISIBLE);
        }else{
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