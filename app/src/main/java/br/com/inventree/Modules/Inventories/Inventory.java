package br.com.inventree.Modules.Inventories;

public class Inventory {
    private int id;
    private String name;
    private double area;
    private String inventoryDate;
    private double parcelsArea;
    private String plantationDate;
    private double inlineSpace;
    private double betweenLineSpace;
    private double woodVolume;
    private String species;

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setArea(double area){
        this.area = area;
    }

    public void setInventoryDate(String inventoryDate){
        this.inventoryDate = inventoryDate;
    }

    public void setParcelsArea(double parcelsArea){
        this.parcelsArea = parcelsArea;
    }

    public void setPlantationDate(String plantationDate){
        this.plantationDate = plantationDate;
    }

    public void setInlineSpace(double inlineSpace){
        this.inlineSpace = inlineSpace;
    }

    public void setBetweenLineSpace(double betweenLineSpace){
        this.betweenLineSpace = betweenLineSpace;
    }

    public void setWoodVolume(double woodVolume){
        this.woodVolume = woodVolume;
    }

    public void setSpecies(String species){
        this.species = species;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public double getArea(){
        return this.area;
    }

    public String getInventoryDate(){
        return this.inventoryDate;
    }

    public Double getParcelsArea(){
        return this.parcelsArea;
    }

    public String getPlantationDate(){
        return this.plantationDate;
    }

    public Double getInlineSpace(){
        return this.inlineSpace;
    }

    public Double getBetweenLineSpace(){
        return this.betweenLineSpace;
    }

    public Double getWoodVolume(){
        return this.woodVolume;
    }

    public String getSpecies(){
        return this.species;
    }

}
