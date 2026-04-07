package com.pao.laboratory07.exercise2;

public final class ComandaRedusa extends Comanda{
    private int discount;

    ComandaRedusa(String nume, Double pret, int discount){
        this.nume = nume;
        this.pret = pret;
        this.discount = discount;
        //System.out.println("DISCOUNTED: " + nume +", pret: " + String.format("%.2f", pret) + " lei (-" + discount + "%) [PLACED]");
    }

    @Override
    public double pretFinal(){
        return pret - pret * discount / 100;
    }

    @Override
    public String descriere(){
        return "DISCOUNTED: " + nume +", pret: " + String.format("%.2f", pretFinal()) + " lei (-" + discount + "%) [PLACED]";

    }


}
