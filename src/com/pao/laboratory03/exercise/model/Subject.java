package com.pao.laboratory03.exercise.model;

public enum Subject {
    PAOJ("Programare Avansată pe Obiecte", 6),
    BD("Baze de Date", 5),
    SO("Sisteme de operare", 6),
    RC("Retele in calculatoare", 4);

    private String fullName;
    private int credits;

    Subject(String fullName, int credits) {
        this.fullName = fullName;
        this.credits = credits;
    }

    public String getFullName() {
        return fullName;
    }

    public int getCredits() {
        return credits;
    }

    public String toString(){
        return this + "("+ this.getFullName()+ " " + this.getCredits() + ")";
    }
}
