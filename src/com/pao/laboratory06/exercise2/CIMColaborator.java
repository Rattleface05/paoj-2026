package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator{
    enum Bonus{DA, NU};
    Bonus bonus;

    CIMColaborator(){
        this.bonus = Bonus.NU;
        this.tip = TipColaborator.CIM;
    }

    CIMColaborator(String nume, String prenume, double brut){
        this.bonus = Bonus.NU;
        this.nume = nume;
        this.prenume = prenume;
        this.brut = brut;
        this.tip = TipColaborator.CIM;
    }

    CIMColaborator(String nume, String prenume, double brut, Bonus bonus){
        this.bonus = bonus ;
        this.nume = nume;
        this.prenume = prenume;
        this.brut = brut;
        this.tip = TipColaborator.CIM;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        if (areBonus()) {
            return this.brut * 12 * 0.55 * 1.1;
        }
        else{
            return this.brut * 12 * 0.55;
        }
    }

    //Aici sunt metodele din interfata
    @Override
    public void citeste(Scanner in) {
        //TipColaborator tip = TipColaborator.valueOf(in.next());
        String nume = in.next();
        String prenume = in.next();
        Double brut = in.nextDouble();
        Bonus bonus = Bonus.valueOf(in.next());

        //this.setTip(tip);
        this.setNume(nume);
        this.setPrenume(prenume);
        this.setBrut(brut);
        this.setBonus(bonus);
    }

    @Override
    public void afiseaza() {
        System.out.println("CIM: " + nume + " "+ prenume +", venit net anual: " + String.format( "%.2f", this.calculeazaVenitNetAnual()) + " lei");
    }

    @Override
    public String tipContract() {
        return tip.toString();
    }

    @Override
    public boolean areBonus() {
        return bonus == Bonus.DA;
    }
}
