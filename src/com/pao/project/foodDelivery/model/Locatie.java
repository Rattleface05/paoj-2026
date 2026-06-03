package com.pao.project.foodDelivery.model;

import java.util.Objects;

public abstract class Locatie {
    protected long id;
    protected String strada;
    protected int numar;
    protected int scara;
    protected int apartament;

    public Locatie(){
        this.strada = "placeholder";
        this.numar = 0;
        this.scara = 0;
        this.apartament = 0;
    }

    public Locatie(String strada, int numar){
        this.strada = strada;
        this.numar = numar;
        this.scara = 0;
        this.apartament = 0;
    }

    public Locatie(String strada, int numar, int scara, int apartament){
        this.strada = strada;
        this.numar = numar;
        this.scara = scara;
        this.apartament = apartament;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    public int getScara() {
        return scara;
    }

    public void setScara(int scara) {
        this.scara = scara;
    }

    public int getApartament() {
        return apartament;
    }

    public void setApartament(int apartament) {
        this.apartament = apartament;
    }

    @Override
    public String toString() {
        return "Locatie{" +
                "id=" + id +
                ", strada='" + strada + '\'' +
                ", numar=" + numar +
                ", scara=" + scara +
                ", apartament=" + apartament +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locatie locatie = (Locatie) o;
        return numar == locatie.numar && Objects.equals(strada, locatie.strada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strada, numar);
    }
}
