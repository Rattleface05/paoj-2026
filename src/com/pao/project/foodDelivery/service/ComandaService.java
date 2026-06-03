package com.pao.project.foodDelivery.service;


import com.pao.project.foodDelivery.exception.ComandaNegasitaException;
import com.pao.project.foodDelivery.model.Comanda;

import java.util.*;

public final class ComandaService {
    private static ComandaService instanta;
    private PriorityQueue<Comanda> listaComenzi;
    private Map<Long, Comanda> comenziPeId;
    private AuditService auditService;

    private ComandaService(){
        listaComenzi = new PriorityQueue<>();
        comenziPeId = new HashMap<>();
        auditService = AuditService.getInstance();
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
        auditService.log("create_comanda");
    }

    public void deleteComanda(int comandaId) throws ComandaNegasitaException {
        Comanda comanda = comenziPeId.remove(comandaId);
        if(comanda == null){
            throw new ComandaNegasitaException("Comanda cu id-ul " + comandaId + " nu a fost gasita!");
        }
        listaComenzi.remove(comanda);
        auditService.log("delete_comanda");
    }

    public Comanda getComandaById(long id) throws ComandaNegasitaException {
        Comanda comanda = comenziPeId.get(id);
        if(comanda == null){
            throw new ComandaNegasitaException("Comanda cu id-ul " + id + " nu a fost gasita!");
        }
        auditService.log("search_comanda");
        return comanda;
    }

    public List<Comanda> getAllComenzi(){
        auditService.log("list_all_comenzi");
        return new ArrayList<>(comenziPeId.values());
    }

    public int getComenziCount(){
        return comenziPeId.size();
    }

    public List<Comanda> getComenziSorted(){
        List<Comanda> sorted = new ArrayList<>(comenziPeId.values());
        Collections.sort(sorted);
        auditService.log("sort_comenzi");
        return sorted;
    }

    public void finishComanda(Comanda comanda) {
        comanda.finishComanda();
        auditService.log("finish_comanda");
    }
}
