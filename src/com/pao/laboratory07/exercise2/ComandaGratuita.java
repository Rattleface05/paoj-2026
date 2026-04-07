package com.pao.laboratory07.exercise2;

public final class ComandaGratuita extends Comanda{

    ComandaGratuita(String nume){
        this.nume = nume;
        this.pret = 0.00;
        //System.out.println("GIFT: " + nume +", pret: 0.00 lei [PLACED]");
    }

    @Override
    public double pretFinal(){
        return 0.00;
    }

    @Override
    public String descriere(){
//        return "GIFT: " + nume +", pret: 0.00 lei [PLACED]";
        return "GIFT: " + nume +", gratuit [PLACED]";

    }
}
