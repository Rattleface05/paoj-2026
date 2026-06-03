package com.pao.project.foodDelivery.model;

public class Local extends Locatie {
    private Meniu meniu;
    private String nume;

    public Local(){
        super();
        this.meniu = new Meniu();
        this.nume = "Local Anonim";
    }

    public Local(String nume, String strada, int numar){
        super(strada, numar);
        this.meniu = new Meniu();
        this.nume = nume;
    }

    public Local(String nume, String strada, int numar, Meniu meniu){
        super(strada, numar);
        this.meniu = meniu;
        this.nume = nume;
    }

    public Meniu getMeniu() {
        return meniu;
    }

    public void setMeniu(Meniu meniu) {
        this.meniu = meniu;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", strada='" + strada + '\'' +
                ", numar=" + numar +
                '}';
    }
}
