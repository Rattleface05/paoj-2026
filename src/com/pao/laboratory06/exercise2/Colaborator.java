package com.pao.laboratory06.exercise2;

import java.util.Comparator;

public abstract class Colaborator implements  IOperatiiCitireScriere, Comparable<Colaborator> {
    String nume;
    String prenume;
    double brut;
    TipColaborator tip;

    public abstract double calculeazaVenitNetAnual();

    @Override
    public int compareTo(Colaborator other) {
        if (this.calculeazaVenitNetAnual() - other.calculeazaVenitNetAnual() < 0){
            return 1;
        }

        if (this.calculeazaVenitNetAnual() - other.calculeazaVenitNetAnual() == 0){
            return 0;
        }

        return -1;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public double getBrut() {
        return brut;
    }

    public void setBrut(double brut) {
        this.brut = brut;
    }

    public TipColaborator getTip() {
        return tip;
    }

    public void setTip(TipColaborator tip) {
        this.tip = tip;
    }
}
