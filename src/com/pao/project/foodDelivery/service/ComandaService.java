package com.pao.project.foodDelivery.service;


import com.pao.project.foodDelivery.exception.ComandaNegasitaException;
import com.pao.project.foodDelivery.model.Comanda;

import java.util.*;

public final class ComandaService {
    private static ComandaService instanta;
    private PriorityQueue<Comanda> listaComenzi;
    private Map<Integer, Comanda> comenziPeId;

    private ComandaService(){
        listaComenzi = new PriorityQueue<>();
        comenziPeId = new HashMap<>();
    }

    public static ComandaService getInstanta(){
        if (instanta == null){
            instanta = new ComandaService();
        }
        return instanta;
    }

    public void addComanda(Comanda comanda){
        listaComenzi.add(comanda);
        comenziPeId.put(comanda.getId(), comanda);
    }

    public void deleteComanda(int comandaId) throws ComandaNegasitaException {
        Comanda comanda = comenziPeId.remove(comandaId);
        if(comanda == null){
            throw new ComandaNegasitaException("Comanda cu id-ul " + comandaId + " nu a fost gasita!");
        }
        listaComenzi.remove(comanda);
    }

    public Comanda getComandaById(int id) throws ComandaNegasitaException {
        Comanda comanda = comenziPeId.get(id);
        if(comanda == null){
            throw new ComandaNegasitaException("Comanda cu id-ul " + id + " nu a fost gasita!");
        }
        return comanda;
    }

    public List<Comanda> getAllComenzi(){
        return new ArrayList<>(comenziPeId.values());
    }

    public int getComenziCount(){
        return comenziPeId.size();
    }

    public List<Comanda> getComenziSorted(){
        List<Comanda> sorted = new ArrayList<>(comenziPeId.values());
        Collections.sort(sorted);
        return sorted;
    }
}
