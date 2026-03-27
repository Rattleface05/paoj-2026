package com.pao.laboratory05.playlist;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs;


    Playlist(String name) {
        this.name = name;
        this.songs = new Song[0];
    }

    String getName(){
        return name;
    }

    void addSong(Song song) {
        ArrayList<Song> aux = new ArrayList<Song>(Arrays.asList(this.songs));
        aux.add(song);
        this.songs = aux.toArray(this.songs);
    }
    void printSortedByTitle(){
        Song[] copy = this.songs.clone();
        Arrays.sort(copy);
        // System.out.println(copy);
        for (Song song : copy){
            System.out.println(song);
        }
    }
    void printSortedByDuration(){
        Song[] copy = this.songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        // System.out.println(copy);
        for (Song song : copy){
            System.out.println(song);
        }
    }
    int getTotalDuration(){
        int total = 0;
        for (Song song : songs){
            total += song.durationSeconds();
        }
        return total;
    }
}
