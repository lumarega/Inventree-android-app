package br.com.inventree.Storage.SQLiteDb;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import br.com.inventree.Modules.Inventories.Inventory;
import br.com.inventree.Modules.Parcels.Parcel;
import br.com.inventree.Modules.Trees.Tree;

public class Dao {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public Dao (Context context){
        dbOpenHelper = new DbOpenHelper(context,"inventree.db", null,1);
    }

    public void open(){
        db = dbOpenHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public ArrayList<Inventory> listInventory(){
        ArrayList<Inventory> inventoryList = new ArrayList<>();
        open();
        Cursor cursor = db.query("inv_inventory", null, null, null, null, null, "_id");
        while(cursor.moveToNext()){
            Inventory inventory = new Inventory();
            inventory.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            inventory.setName(cursor.getString(cursor.getColumnIndex("inv_name")));
            inventory.setArea(cursor.getDouble(cursor.getColumnIndex("inv_area")));
            inventory.setInventoryDate(cursor.getString(cursor.getColumnIndex("inv_date")));
            inventory.setParcelsArea(cursor.getDouble(cursor.getColumnIndex("inv_parc_area")));
            inventory.setPlantationDate(cursor.getString(cursor.getColumnIndex("inv_plant_date")));
            inventory.setInlineSpace(cursor.getDouble(cursor.getColumnIndex("inv_inline_space")));
            inventory.setBetweenLineSpace(cursor.getDouble(cursor.getColumnIndex("inv_btw_line_space")));
            inventory.setWoodVolume(cursor.getDouble(cursor.getColumnIndex("inv_wood_volume")));
            inventory.setSpecies(cursor.getString(cursor.getColumnIndex("inv_species")));
            inventoryList.add(inventory);
        }
        close();
        return inventoryList;
    }

    public void insertInventory(Inventory inventory){
        ContentValues values = new ContentValues();
        values.put("inv_name",inventory.getName());
        values.put("inv_area",inventory.getArea());
        values.put("inv_date",inventory.getInventoryDate());
        values.put("inv_parc_area",inventory.getParcelsArea());
        values.put("inv_plant_date",inventory.getPlantationDate());
        values.put("inv_inline_space",inventory.getInlineSpace());
        values.put("inv_btw_line_space",inventory.getBetweenLineSpace());
        values.put("inv_wood_volume",inventory.getWoodVolume());
        values.put("inv_species",inventory.getSpecies());
        open();
        db.insert("inv_inventory", null, values);
        close();
    }

    public void deleteInventory(Inventory inventory, Context context){
        open();
        Log.e("DAO","delete entrou");
        if(db.delete("inv_inventory", "_id="+ inventory.getId(), null) != -1){
            Intent intent_refresh_inventories = new Intent("refresh_inventories_list");
            context.sendBroadcast(intent_refresh_inventories);
        }
        close();
    }

    public void updateInventory(Inventory inventory){
        ContentValues values = new ContentValues();
        values.put("inv_name",inventory.getName());
        values.put("inv_area",inventory.getArea());
        values.put("inv_date",inventory.getInventoryDate());
        values.put("inv_parc_area",inventory.getParcelsArea());
        values.put("inv_plant_date",inventory.getPlantationDate());
        values.put("inv_inline_space",inventory.getInlineSpace());
        values.put("inv_btw_line_space",inventory.getBetweenLineSpace());
        values.put("inv_wood_volume",inventory.getWoodVolume());
        values.put("inv_species",inventory.getSpecies());
        open();
        db.update("inv_inventory",values, "_id = " + inventory.getId(), null);
        close();
    }

    public void updateInventoryAverageWoodVolume(int inventoryId, double averageWoodVolume){
        ContentValues values = new ContentValues();
        values.put("inv_wood_volume",averageWoodVolume);
        open();
        db.update("inv_inventory",values, "_id = " + inventoryId, null);
        close();
    }

    public void calcInventoryWoodVolumeAverage(int inventoryId){
        open();
        Cursor cursor = db.query("inv_parcels", null, "parc_inventory_id = "+inventoryId, null, null, null, "_id");
        double sumAllParcelsWoodVolume = 0;
        double averageWoodVolume = 0;
        int countAllParcels = cursor.getCount();
        if(countAllParcels > 0){
            while(cursor.moveToNext()){
                sumAllParcelsWoodVolume += cursor.getDouble(cursor.getColumnIndex("parc_average_wood_volume"));
            }
            averageWoodVolume = sumAllParcelsWoodVolume/countAllParcels;
        }
        close();
        updateInventoryAverageWoodVolume(inventoryId, averageWoodVolume);
    }

    public ArrayList<Parcel> listParcel(int inventoryId){
        ArrayList<Parcel> parcelsList = new ArrayList<>();
        open();
        Cursor cursor = db.query("inv_parcels", null, "parc_inventory_id = "+inventoryId, null, null, null, "_id");
        while(cursor.moveToNext()){
            Parcel parcel = new Parcel();

            parcel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            parcel.setName(cursor.getString(cursor.getColumnIndex("parc_name")));
            parcel.setTreesQuantity(cursor.getInt(cursor.getColumnIndex("parc_tree_quantity")));
            parcel.setNotes(cursor.getString(cursor.getColumnIndex("parc_notes")));
            parcel.setInventoryId(cursor.getInt(cursor.getColumnIndex("parc_inventory_id")));
            parcel.setTreesAverageWoodVolume(cursor.getDouble(cursor.getColumnIndex("parc_average_wood_volume")));
            parcelsList.add(parcel);
        }
        close();
        return parcelsList;
    }

    public void insertParcel(Parcel parcel){
        ContentValues values = new ContentValues();
        values.put("parc_name",parcel.getName());
        values.put("parc_tree_quantity",parcel.getTreesQuantity());
        values.put("parc_notes",parcel.getNotes());
        values.put("parc_inventory_id",parcel.getInventoryId());
        values.put("parc_average_wood_volume", 0);
        open();
        db.insert("inv_parcels", null, values);
        close();
        calcInventoryWoodVolumeAverage(parcel.getInventoryId());
    }

    public void deleteParcel(Parcel parcel, Context context){
        open();
        if(db.delete("inv_parcels", "_id="+ parcel.getId()+" AND parc_inventory_id = "+parcel.getInventoryId(), null) != -1){
            Intent intent_refresh_inventories = new Intent("refresh_parcels_list");
            context.sendBroadcast(intent_refresh_inventories);
            calcInventoryWoodVolumeAverage(parcel.getInventoryId());
        }
        close();
    }

    public void updateParcel(Parcel parcel){
        ContentValues values = new ContentValues();
        values.put("parc_name",parcel.getName());
        values.put("parc_tree_quantity",parcel.getTreesQuantity());
        values.put("parc_notes",parcel.getNotes());
        values.put("parc_inventory_id",parcel.getInventoryId());
        open();
        db.update("inv_parcels",values, "_id = " + parcel.getId(), null);
        close();
    }

    public void updateParcelAverageWoodVolume(int inventoryId, int parcelId, double parcelWoodVolume){
        ContentValues values = new ContentValues();
        values.put("parc_average_wood_volume",parcelWoodVolume);
        open();
        if(db.update("inv_parcels",values, "_id = " + parcelId, null) != -1){
            calcInventoryWoodVolumeAverage(inventoryId);
        }
        close();
    }

    public ArrayList<Tree> listTrees(int inventoryId, int parcelId){
        ArrayList<Tree> treesList = new ArrayList<>();
        open();
        Cursor cursor = db.query("inv_trees", null, "tree_inventory_id = "+inventoryId+" AND tree_parcel_id = "+parcelId , null, null, null, "_id");
        while(cursor.moveToNext()){
            Tree tree = new Tree();
            tree.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            tree.setName(cursor.getString(cursor.getColumnIndex("tree_name")));
            tree.setHeight(cursor.getDouble(cursor.getColumnIndex("tree_height")));
            tree.setCap(cursor.getDouble(cursor.getColumnIndex("tree_cap")));
            tree.setDap(cursor.getDouble(cursor.getColumnIndex("tree_dap")));
            tree.setWoodVolume(cursor.getDouble(cursor.getColumnIndex("tree_wood_volume")));
            tree.setNotes(cursor.getString(cursor.getColumnIndex("tree_notes")));
            tree.setInventoryId(cursor.getInt(cursor.getColumnIndex("tree_inventory_id")));
            tree.setParcelId(cursor.getInt(cursor.getColumnIndex("tree_parcel_id")));
            treesList.add(tree);
        }
        close();
        return treesList;
    }

    public void insertTree(Tree tree){
        ContentValues values = new ContentValues();
        values.put("tree_name",tree.getName());
        values.put("tree_height",tree.getHeight());
        values.put("tree_cap",tree.getCap());
        values.put("tree_dap",tree.getDap());
        values.put("tree_wood_volume",tree.getWoodVolume());
        values.put("tree_notes",tree.getNotes());
        values.put("tree_inventory_id",tree.getInventoryId());
        values.put("tree_parcel_id",tree.getParcelId());
        open();
        if(db.insert("inv_trees", null, values) != -1){
            calcTreesWoodVolumeAverage(tree.getParcelId(), tree.getInventoryId());
        }
        close();
    }

    public void calcTreesWoodVolumeAverage(int parcelId, int inventoryId){
        open();
        Cursor cursor = db.query("inv_trees", null, "tree_inventory_id = "+inventoryId+" AND tree_parcel_id = "+parcelId , null, null, null, "_id");
        double sumAllTreesWoodVolume = 0;
        double averageWoodVolume = 0;
        int countAllTrees = cursor.getCount();
        if(countAllTrees > 0){
            while(cursor.moveToNext()){
                sumAllTreesWoodVolume += cursor.getDouble(cursor.getColumnIndex("tree_wood_volume"));
            }
            averageWoodVolume = sumAllTreesWoodVolume/countAllTrees;
        }
        close();

        updateParcelAverageWoodVolume(inventoryId, parcelId, averageWoodVolume);
    }

    public void deleteTree(Tree tree, Context context){
        open();
        if(db.delete("inv_trees", "_id = "+tree.getId()+" AND tree_parcel_id = "+ tree.getParcelId()+" AND tree_inventory_id = "+tree.getInventoryId(), null) != -1){
            Intent intent_refresh_trees = new Intent("refresh_trees_list");
            context.sendBroadcast(intent_refresh_trees);
            calcTreesWoodVolumeAverage(tree.getParcelId(), tree.getInventoryId());
        }
        close();
    }

    public void updateTree(Tree tree){
        Log.e("TREE","update DAO");
        ContentValues values = new ContentValues();
        values.put("tree_name",tree.getName());
        values.put("tree_height",tree.getHeight());
        values.put("tree_cap",tree.getCap());
        values.put("tree_dap",tree.getDap());
        values.put("tree_wood_volume",tree.getWoodVolume());
        values.put("tree_notes",tree.getNotes());
        values.put("tree_inventory_id",tree.getInventoryId());
        values.put("tree_parcel_id",tree.getParcelId());
        open();
        db.update("inv_trees",values, "_id = " + tree.getId(), null);
        close();
    }

    public String getInventoryDataToCreateFile(int inventoryId){
        String combinedString = null;

        String sql = ("SELECT inv_inventory.inv_name, inv_inventory.inv_area, inv_inventory.inv_date," +
                "inv_inventory.inv_parc_area, inv_inventory.inv_plant_date, inv_inventory.inv_inline_space, inv_inventory.inv_btw_line_space," +
                "inv_inventory.inv_wood_volume, inv_inventory.inv_species, inv_parcels.parc_name, inv_parcels.parc_notes," +
                "inv_parcels.parc_average_wood_volume, inv_trees.tree_name, inv_trees.tree_height, inv_trees.tree_cap, inv_trees.tree_dap, " +
                "inv_trees.tree_wood_volume, inv_trees.tree_notes FROM inv_inventory INNER JOIN inv_parcels ON inv_inventory._id = inv_parcels.parc_inventory_id INNER JOIN inv_trees ON inv_parcels._id = inv_trees.tree_parcel_id " +
                "WHERE inv_inventory._id = "+inventoryId);
        open();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            String columnString =   "Inventario; Area Inventario; Data Inventario; Area Parcela; Data Plantio; Espacamento em linha; Espacamento entre linha;" +
                    "Volume Madeira Inventario; Especie; Parcela; Observacoes Parcela; Volume Madeira Parcela; Arvore; Altura; CAP; DAP; Volume Madeira Arvore;" +
                    "Observações Arvore\n";
            String dataString   =  "";

            while(cursor.moveToNext()){
                dataString += cursor.getString(cursor.getColumnIndex("inv_name"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("inv_area"))+"; "
                        +cursor.getString(cursor.getColumnIndex("inv_date"))+"; "
                        +cursor.getString(cursor.getColumnIndex("inv_parc_area"))+"; "
                        +cursor.getString(cursor.getColumnIndex("inv_plant_date"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("inv_inline_space"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("inv_btw_line_space"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("inv_wood_volume"))+"; "
                        +cursor.getString(cursor.getColumnIndex("inv_species"))+"; "
                        +cursor.getString(cursor.getColumnIndex("parc_name"))+"; "
                        +cursor.getString(cursor.getColumnIndex("parc_notes"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("parc_average_wood_volume"))+"; "
                        +cursor.getString(cursor.getColumnIndex("tree_name"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("tree_height"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("tree_cap"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("tree_dap"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("tree_wood_volume"))+"; "
                        +cursor.getDouble(cursor.getColumnIndex("tree_notes"))+"\n";
            }
            combinedString = columnString+dataString;
        }
        close();
        return combinedString;
    }

/*
    public void print(){
        //Log.v("DUMP CURSOR","DADOS PARA CRIAR ARQUIVO-> "+ DatabaseUtils.dumpCursorToString(cursor));
    }*/

}


