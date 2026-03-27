package com.pao.laboratory05.biblioteca;

import com.pao.laboratory05.playlist.Song;

public class Carte implements Comparable<Carte>{
    String titlu;
    String autor;
    int an;
    double rating;

    Carte(String titlu, String autor, int an, double rating){
        this.titlu = titlu;
        this.autor = autor;
        this.an = an;
        this.rating = rating;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getAutor() {
        return autor;
    }

    public int getAn() {
        return an;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString(){
        return "Carte{titlu='" + this.titlu + "', autor='" + this.autor + "', an=" + this.an + ", rating="+ this.rating+"}";
    }

    @Override
    public int compareTo(Carte other) {
        // return other.rating.compareTo(this.rating);
        return Double.compare(other.rating, this.rating);
    }
}
