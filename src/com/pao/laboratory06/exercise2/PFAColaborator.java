package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator{
    double cheltuieli;

    PFAColaborator(){
        this.tip = TipColaborator.PFA;
    }


    PFAColaborator(String nume, String prenume, double brut, double cheltuieli){
        this.cheltuieli = cheltuieli;
        this.nume = nume;
        this.prenume = prenume;
        this.brut = brut;
        this.tip = TipColaborator.PFA;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venit_net = (brut - cheltuieli) * 12;
        double salariuMinimBrutAnual = 4050.0 * 12;
        double salariuMinimBrutLunar = 4050.0;

        double CAS = 0.0, CASS = 0.0, impozit;

        impozit = 0.1 * venit_net;


        if(venit_net < 6 * salariuMinimBrutAnual){
            CASS =  6 * salariuMinimBrutAnual    * 0.1;
        }
        else if (venit_net >= 6 * salariuMinimBrutAnual && venit_net <= 72 * salariuMinimBrutAnual) {
            CASS = venit_net * 0.1;
        }
        else if (venit_net > 72 * salariuMinimBrutAnual){
            CASS = 72 * salariuMinimBrutAnual * 0.1;
        }

        if(venit_net < 12 * salariuMinimBrutAnual){
            CAS = 0.0;
        }
        else if (venit_net >= 12 * salariuMinimBrutAnual && venit_net <= 24 * salariuMinimBrutAnual) {
            CAS =  12 * salariuMinimBrutAnual * 0.25 ;
        }
        else if(venit_net > 24 * salariuMinimBrutAnual) {
            CAS = 24 * salariuMinimBrutAnual * 0.25;
        }

        return (venit_net - impozit - CAS - CASS);
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
        System.out.println("PFA: " + nume + " "+ prenume +", venit net anual: " + String.format( "%.2f", this.calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return tip.toString();
    }


}
