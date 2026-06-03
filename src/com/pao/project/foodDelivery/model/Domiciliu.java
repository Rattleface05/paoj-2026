package com.pao.project.foodDelivery.model;

import java.util.Objects;

public class Domiciliu extends Locatie {
    public Domiciliu(){
        super();
    }

    public Domiciliu(String strada, int numar){
        super(strada, numar);
    }

    public Domiciliu(String strada, int numar, int scara, int apartament){
        super(strada, numar, scara, apartament);
    }

    @Override
    public String toString() {
        return "Domiciliu{" +
                "id=" + id +
                ", strada='" + strada + '\'' +
                ", numar=" + numar +
                ", scara=" + scara +
                ", apartament=" + apartament +
                '}';
    }
}
