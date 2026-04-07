package com.pao.laboratory07.exercise2;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected Double pret;
    protected OrderState state ;
    public abstract double pretFinal();
    public abstract String descriere();
}
