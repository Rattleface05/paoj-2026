package com.pao.laboratory05.biblioteca;

import com.pao.laboratory05.playlist.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private static BibliotecaService single_instance = null;

    public static BibliotecaService getInstance(){
        if (single_instance == null){
            return new BibliotecaService();
        }
        return single_instance;
    }



    private Carte[] carti;
    private BibliotecaService() {
        this.carti = new Carte[0];
    }

    void addCarte(Carte carte) {

            ArrayList<Carte> aux = new ArrayList<Carte>(Arrays.asList(this.carti));
            aux.add(carte);
            this.carti = aux.toArray(this.carti);
            System.out.println("Cartea " + carte.getTitlu() + " adaugata cu succes!");

    }
    void listSortedByRating() {
        Carte[] copy = this.carti.clone();
        Arrays.sort(copy);
        // System.out.println(copy);
        for (Carte carte : copy){
            System.out.println(carte);
        }
    }
    void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy = this.carti.clone();
        Arrays.sort(copy, comparator);
        // System.out.println(copy);
        for (Carte carte : copy){
            System.out.println(carte);
        }
    }
}
