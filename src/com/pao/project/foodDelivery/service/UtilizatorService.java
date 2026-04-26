package com.pao.project.foodDelivery.service;

import com.pao.project.foodDelivery.exception.UtilizatorNegasitException;
import com.pao.project.foodDelivery.model.Utilizator;

import java.util.*;

public final class UtilizatorService {
    private static UtilizatorService instanta;
    private Map<String, Utilizator> utilizatori; // Map pe email

    private UtilizatorService(){
        utilizatori = new HashMap<>();
    }

    public static UtilizatorService getInstanta(){
        if (instanta == null){
            instanta = new UtilizatorService();
        }
        return instanta;
    }

    public void addUtilizator(Utilizator utilizator){
        utilizatori.put(utilizator.getEmail(), utilizator);
    }

    public void deleteUtilizator(String email) throws UtilizatorNegasitException {
        if(!utilizatori.containsKey(email)){
            throw new UtilizatorNegasitException("Utilizatorul cu email-ul " + email + " nu a fost gasit!");
        }
        utilizatori.remove(email);
    }

    public Utilizator getUtilizatorByEmail(String email) throws UtilizatorNegasitException {
        Utilizator utilizator = utilizatori.get(email);
        if(utilizator == null){
            throw new UtilizatorNegasitException("Utilizatorul cu email-ul " + email + " nu a fost gasit!");
        }
        return utilizator;
    }

    public Utilizator getUtilizatorByUsername(String username) throws UtilizatorNegasitException {
        for(Utilizator u : utilizatori.values()){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        throw new UtilizatorNegasitException("Utilizatorul cu username-ul " + username + " nu a fost gasit!");
    }

    public List<Utilizator> getAllUtilizatori(){
        return new ArrayList<>(utilizatori.values());
    }

    public int getUtilizatoriCount(){
        return utilizatori.size();
    }

    public List<Utilizator> getUtilizatoriSortedByPuncte(){
        List<Utilizator> sorted = new ArrayList<>(utilizatori.values());
        sorted.sort((u1, u2) -> Integer.compare(u2.getPuncte(), u1.getPuncte()));
        return sorted;
    }

    public void updateUtilizator(String email, Utilizator nouUtilizator) throws UtilizatorNegasitException {
        if(!utilizatori.containsKey(email)){
            throw new UtilizatorNegasitException("Utilizatorul cu email-ul " + email + " nu a fost gasit!");
        }
        utilizatori.put(email, nouUtilizator);
    }
}
