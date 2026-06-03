package com.pao.project.foodDelivery.model;

import java.util.Objects;

public class Articol {
    private long id;
    private String nume;
    private Double pret;
    private boolean vegan;

    public Articol(){}
    public Articol(String nume, Double pret){
        this.nume = nume;
        this.pret = pret;
        this.vegan = false;
    }

    public Articol(String nume, Double pret, boolean vegan){
        this.nume = nume;
        this.pret = pret;
        this.vegan = vegan;
    }

    public String isVegan(){
        return vegan ? "Da" : "Nu";
    }

    public Double getPret() {
        return pret;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public String getNume(){
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public boolean isVeganProduct() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    @Override
    public String toString(){
        return "Articol{"+ "ID: " + id + ", Nume: " + nume + ", Pret: " + pret + " lei, Vegan: " + isVegan()+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articol articol = (Articol) o;
        return Objects.equals(nume, articol.nume) && Objects.equals(pret, articol.pret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, pret);
    }
}
