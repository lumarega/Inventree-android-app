package br.com.inventree.Modules.Parcels;

public class Parcel {
    private int id;
    private String name;
    private int treesQuantity;
    private String notes;
    private int inventoryId;
    private double treesAverageWoodVolume;

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTreesQuantity(int treesQuantity){
        this.treesQuantity = treesQuantity;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setInventoryId(int inventoryId){this.inventoryId = inventoryId;}

    public void setTreesAverageWoodVolume(double woodVolume){
        this.treesAverageWoodVolume = woodVolume;
    }

    public int getId(){
        return  this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getTreesQuantity(){
        return this.treesQuantity;
    }

    public String getNotes(){
        return this.notes;
    }

    public int getInventoryId(){return this.inventoryId;}

    public double getTreesAverageWoodVolume(){return this.treesAverageWoodVolume;}
}
