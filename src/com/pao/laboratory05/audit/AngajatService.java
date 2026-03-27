package com.pao.laboratory05.audit;


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
    private AuditEntry[] auditLog;

    private AngajatService() {
        this.angajati = new Angajat[0];
        this.auditLog = new AuditEntry[0];
    }

    private void logAction(String action, String target){
        ArrayList<AuditEntry> aux = new ArrayList<AuditEntry>(Arrays.asList(this.auditLog));
        aux.add(new AuditEntry(action, target, java.time.LocalDateTime.now().toString()));
        this.auditLog = aux.toArray(this.auditLog);
    }


    void addAngajat(Angajat angajat) {

        ArrayList<Angajat> aux = new ArrayList<Angajat>(Arrays.asList(this.angajati));
        aux.add(angajat);
        this.angajati = aux.toArray(this.angajati);
        logAction("ADD", angajat.getNume());
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
        logAction("FIND_BY_DEPT", numeDept);
        for (Angajat angajat : angajati){
            if (angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println(angajat);
                ok = true;
            }
        }
        if (!ok) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }

    void printAuditLog() {
        for (AuditEntry auditEntry : auditLog) {
            System.out.println(auditEntry);
        }
    }

}
