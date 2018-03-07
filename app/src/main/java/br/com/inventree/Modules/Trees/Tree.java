package br.com.inventree.Modules.Trees;

public class Tree {

    private int id;
    private String name;
    private double height;
    private double cap;
    private double dap;
    private double woodVolume;
    private String notes;
    private int inventoryId;
    private int parcelId;

    public void setId(int id){this.id = id;}

    public void setName(String name){this.name = name;}

    public void setHeight(double height){this.height = height;}

    public void setCap(double cap){this.cap = cap;}

    public void setDap(double dap){this.dap = dap;}

    public void setWoodVolume(double woodVolume){this.woodVolume = woodVolume;}

    public void setNotes(String notes){this.notes = notes;}

    public void setInventoryId(int inventoryId){this.inventoryId = inventoryId;}

    public void setParcelId(int parcelId){this.parcelId = parcelId;}

    public int getId(){return this.id;}

    public String getName(){return this.name;}

    public double getHeight(){return this.height;}

    public double getCap(){return this.cap;}

    public double getDap(){return this.dap;}

    public double getWoodVolume(){return this.woodVolume;}

    public String getNotes(){return this.notes;}

    public int getInventoryId(){return this.inventoryId;}

    public int getParcelId(){return this.parcelId;}


}
