package com.pao.project.foodDelivery.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Comanda implements Comparable<Comanda> {

    enum Status {TERMINATA, NETERMINATA}

    private long id;
    private ArrayList<Articol> articolList;
    private Utilizator utilizator;
    private Livrator livrator;
    private Local plecare;
    private Domiciliu destinatie;
    private Status status;
    private Date time;
    private static int nextId = 1;

    public Comanda(){}
    public Comanda(ArrayList<Articol> articolList, Utilizator utilizator, Livrator livrator, Local plecare, Domiciliu destinatie) {
        this.id = nextId++;
        this.articolList = articolList;
        this.utilizator = utilizator;
        this.livrator = livrator;
        this.plecare = plecare;
        this.destinatie = destinatie;
        this.status = Status.NETERMINATA;
        this.time = new Date();
    }

    public void finishComanda(){
        if (this.status == Status.NETERMINATA) {
            this.status = Status.TERMINATA;
            utilizator.addComanda(this);
            livrator.addComanda(this);
        }
    }

    public double total(){
        double sum = 0;
        for (Articol articol: articolList){
            sum += articol.getPret();
        }

        if (sum <= 50){
            return sum + 10;
        }
        else {
            return sum;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Articol> getArticolList() {
        return articolList;
    }

    public void setArticolList(ArrayList<Articol> articolList) {
        this.articolList = articolList;
    }

    public Utilizator getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    public Livrator getLivrator() {
        return livrator;
    }

    public void setLivrator(Livrator livrator) {
        this.livrator = livrator;
    }

    public Local getPlecare() {
        return plecare;
    }

    public void setPlecare(Local plecare) {
        this.plecare = plecare;
    }

    public Domiciliu getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(Domiciliu destinatie) {
        this.destinatie = destinatie;
    }

    public Status getStatus() {
        return status;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public int compareTo(Comanda other) {
        return Long.compare(other.time.getTime(), this.time.getTime());
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", utilizator='" + utilizator.getUsername() + '\'' +
                ", status=" + status +
                ", total=" + total() +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comanda comanda = (Comanda) o;
        return id == comanda.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
