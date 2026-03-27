package com.pao.laboratory05.angajati;

import java.util.ArrayList;
import java.util.Arrays;

public class AngajatService {
    private static AngajatService single_instance = null;

    public static AngajatService getInstance(){
        if (single_instance == null){
            return new AngajatService();
        }
        return single_instance;
    }

    private Angajat[] angajati;
    private AngajatService() {
        this.angajati = new Angajat[0];
    }

    void addAngajat(Angajat angajat) {

        ArrayList<Angajat> aux = new ArrayList<Angajat>(Arrays.asList(this.angajati));
        aux.add(angajat);
        this.angajati = aux.toArray(this.angajati);
        System.out.println("Angajatul " + angajat.getNume() + " adaugata cu succes!");

    }

    void printAll() {
        for (Angajat angajat : angajati){
            System.out.println(angajat);
        }
    }

    void listBySalary() {
        Angajat[] copy = this.angajati.clone();
        Arrays.sort(copy);

        for (Angajat angajat : copy){
            System.out.println(angajat);
        }
    }

    void findByDepartament(String numeDept) {
        boolean ok =false;
        for (Angajat angajat : angajati){
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(angajat);
                ok = true;
            }
        }
        if (!ok){
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }




}
