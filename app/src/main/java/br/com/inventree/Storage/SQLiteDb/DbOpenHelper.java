package br.com.inventree.Storage.SQLiteDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static DbOpenHelper dbHelper;
    private static SQLiteDatabase db = null;

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dbHelper = this;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS inv_inventory ( _id INTEGER PRIMARY KEY AUTOINCREMENT, inv_name TEXT, inv_area DECIMAL, inv_date TEXT, "
                    +"inv_parcels_quantity INTEGER, inv_parc_area DECIMAL, inv_plant_date TEXT, inv_inline_space DECIMAL, inv_btw_line_space DECIMAL, "
                    +"inv_wood_volume DECIMAL, inv_species TEXT);");

            db.execSQL("CREATE TABLE IF NOT EXISTS inv_parcels ( _id INTEGER PRIMARY KEY AUTOINCREMENT, parc_name TEXT, "
                    +"parc_tree_quantity DECIMAL, parc_notes TEXT, parc_inventory_id INTEGER, parc_average_wood_volume DECIMAL);");

            db.execSQL("CREATE TABLE IF NOT EXISTS inv_trees ( _id INTEGER PRIMARY KEY AUTOINCREMENT, tree_name TEXT, " +
                    "tree_height DECIMAL, tree_cap DECIMAL, tree_dap DECIMAL, tree_wood_volume DECIMAL, tree_notes TEXT, " +
                    "tree_inventory_id INTEGER, tree_parcel_id INTEGER);");


            String sql = ("INSERT INTO inv_inventory (inv_name, inv_area, inv_date, inv_parcels_quantity, inv_parc_area, inv_plant_date, inv_inline_space, inv_btw_line_space, inv_wood_volume, inv_species) " +
                    "VALUES ('inventario 1', 132, '12/10/2017', 5, 20, '15-08-2015', 2, 2, 0, 'Eucalipto')");
            db.execSQL(sql);
            String sql2 = ("INSERT INTO inv_parcels (parc_name, parc_tree_quantity, parc_notes, parc_inventory_id, parc_average_wood_volume) " +
                    "VALUES ('parcel 1', 132, 'Eucalipto', 1, 0)");
            db.execSQL(sql2);
            String sql3 = ("INSERT INTO inv_trees (tree_name, tree_height, tree_cap, tree_dap, tree_wood_volume, tree_notes, tree_inventory_id, tree_parcel_id) " +
                    "VALUES ('tree 1', 132.2, 132.2, 132.2, 132.2, 'Eucalipto etc', 1, 1)");
            db.execSQL(sql3);

        }catch(Exception e){
            Log.e("DatabaseManager","error creating database-> "+e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
