package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class SRLColaborator extends Colaborator{
    double cheltuieli;

    SRLColaborator(){
        this.tip = TipColaborator.SRL;
    }

    SRLColaborator(String nume, String prenume, double brut, double cheltuieli){
        this.cheltuieli = cheltuieli;
        this.nume = nume;
        this.prenume = prenume;
        this.brut = brut;
        this.tip = TipColaborator.SRL;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net = (brut - cheltuieli) * 12;

        return venit_net  * 0.84;
    }


    public double getCheltuieli() {
        return cheltuieli;
    }

    public void setCheltuieli(double cheltuieli) {
        this.cheltuieli = cheltuieli;
    }

    //Aici sunt metodele din interfata
    @Override
    public void citeste(Scanner in) {
        //TipColaborator tip = TipColaborator.valueOf(in.next());
        String nume = in.next();
        String prenume = in.next();
        Double brut = in.nextDouble();
        Double cheltuieli = in.nextDouble();


        //this.setTip(tip);
        this.setNume(nume);
        this.setPrenume(prenume);
        this.setBrut(brut);
        this.setCheltuieli(cheltuieli);
    }

    @Override
    public void afiseaza() {
        System.out.println("SRL: " + nume + " "+ prenume +", venit net anual: " + String.format( "%.2f", this.calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return tip.toString();
    }
}
