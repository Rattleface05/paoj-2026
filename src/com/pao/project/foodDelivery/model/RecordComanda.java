package com.pao.project.foodDelivery.model;

import java.util.Date;
import java.util.Objects;

/**
 * Clasă imutabilă pentru înregistrarea unei comenzi.
 * Odată creată, nu poate fi modificată.
 */
public final class RecordComanda {
    private final int comandaId;
    private final String utilizatorUsername;
    private final String livratorNume;
    private final double pretTotal;
    private final Date dataComanda;
    private final String status;

    public RecordComanda(int comandaId, String utilizatorUsername, String livratorNume,
                         double pretTotal, Date dataComanda, String status) {
        this.comandaId = comandaId;
        this.utilizatorUsername = utilizatorUsername;
        this.livratorNume = livratorNume;
        this.pretTotal = pretTotal;
        this.dataComanda = new Date(dataComanda.getTime()); // defensive copy
        this.status = status;
    }

    public int getComanadaId() {
        return comandaId;
    }

    public String getUtilizatorUsername() {
        return utilizatorUsername;
    }

    public String getLivratorNume() {
        return livratorNume;
    }

    public double getPretTotal() {
        return pretTotal;
    }

    public Date getDataComanda() {
        return new Date(dataComanda.getTime()); // return defensive copy
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "RecordComanda{" +
                "comandaId=" + comandaId +
                ", utilizatorUsername='" + utilizatorUsername + '\'' +
                ", livratorNume='" + livratorNume + '\'' +
                ", pretTotal=" + pretTotal +
                ", dataComanda=" + dataComanda +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordComanda that = (RecordComanda) o;
        return comandaId == that.comandaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comandaId);
    }
}
