package com.pao.project.foodDelivery.model;

import java.util.ArrayList;
import java.util.Objects;

public class Utilizator {
    private long id;
    private String username;
    private String email;
    private int puncte;
    private ArrayList<Comanda> history;

    public  Utilizator(){}
    public  Utilizator(String email, String username) {
        this.email = email;
        this.username = username;
        this.puncte = 0;
        this.history = new ArrayList<>();
    }

    public void addPuncte(int plus){
        this.puncte += plus;
        System.out.println("I-au fost adaugate utilizatorului " + username + " " + plus + " puncte!");
    }

    public void addComanda(Comanda comanda){
        history.add(comanda);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPuncte() {
        return puncte;
    }

    public void setPuncte(int puncte) {
        this.puncte = puncte;
    }

    public ArrayList<Comanda> getHistory() {
        return history;
    }

    @Override
    public String toString(){
        return "Utilizator{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", puncte=" + puncte +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizator that = (Utilizator) o;
        return Objects.equals(email, that.email) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
