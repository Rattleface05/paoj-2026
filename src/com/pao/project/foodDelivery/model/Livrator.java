package com.pao.project.foodDelivery.model;

import java.util.ArrayList;
import java.util.Objects;

public class Livrator {
    public long id;
    private String nume;
    private ArrayList<Comanda> comenziIndeplinite;

    public Livrator(){}
    public Livrator(String nume) {
        this.nume = nume;
        this.comenziIndeplinite = new ArrayList<>();
    }

    public void addComanda(Comanda comanda){
        comenziIndeplinite.add(comanda);
    }
    public double baniObtinuti(){
        double sum = 0;
        for (Comanda comanda : comenziIndeplinite){
            sum += comanda.total();
        }
        return sum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public ArrayList<Comanda> getComenziIndeplinite() {
        return comenziIndeplinite;
    }

    @Override
    public String toString() {
        return "Livrator{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", comenziIndeplinite=" + comenziIndeplinite.size() +
                ", baniObtinuti=" + baniObtinuti() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livrator livrator = (Livrator) o;
        return Objects.equals(nume, livrator.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume);
    }
}
