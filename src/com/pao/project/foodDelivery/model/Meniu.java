package com.pao.project.foodDelivery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Meniu {
    private HashMap<String, Articol> articole;

    public Meniu(){
        articole = new HashMap<>();
    }

    public void add(Articol articol){
        articole.put(articol.getNume(), articol);
    }

    public Articol get(String nume){
        return articole.get(nume);
    }

    public void remove(String nume){
        articole.remove(nume);
    }

    public HashMap<String, Articol> getArticole() {
        return articole;
    }

    public int getSize(){
        return articole.size();
    }

    @Override
    public String toString(){
        String aux = "Meniul contine: ";
        for (String nume : articole.keySet()){
            aux += "\n" + articole.get(nume);
        }
        aux += "\n Pofta mare!";
        return aux;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meniu meniu = (Meniu) o;
        return Objects.equals(articole, meniu.articole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articole);
    }
}
