package com.pao.laboratory07.exercise2;

public final class ComandaStandard extends Comanda{
    ComandaStandard(String nume, Double pret){
        this.nume = nume;
        this.pret = pret;
        //System.out.println("STANDARD: " + nume +", pret: " + String.format("%.2f", pret) + " lei [PLACED]");
    }

    @Override
    public double pretFinal(){
        return pret;
    }

    @Override
    public String descriere(){
        return "STANDARD: " + nume +", pret: " + String.format("%.2f", pretFinal()) + " lei [PLACED]";

    }
}
