package com.pao.laboratory05.audit;


public class Angajat implements  Comparable<Angajat>{
    String nume;
    Departament departament;
    double salariu;

    Angajat(String nume, Departament departament, double salariu){
        this.nume = nume;
        this.departament = departament;
        this.salariu = salariu;
    }

    public Departament getDepartament() {
        return departament;
    }

    public String getNume() {
        return nume;
    }

    public double getSalariu() {
        return salariu;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", departament=" + departament +
                ", salariu=" + salariu +
                '}';
    }

    @Override
    public int compareTo(Angajat other){
        //return  other.getSalariu().compareTo(this.getSalariu());
        return Double.compare(other.getSalariu(), this.getSalariu());
    }
}
