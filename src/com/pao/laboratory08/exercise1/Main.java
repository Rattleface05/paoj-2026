package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează
        Scanner in = new Scanner(System.in);
        String[] action = in.nextLine().split(" ");
        String[] sargs;

        BufferedReader fin = new BufferedReader(new FileReader(FILE_PATH));

        if (Objects.equals(action[0], "PRINT")) {
            String linie;
            while ((linie = fin.readLine()) != null) {
                sargs = linie.split(",");

                Student student = new Student(sargs[0],Integer.parseInt( sargs[1]),new Adresa(sargs[2],sargs[3]));
                System.out.println(student);
            }
        } else if (Objects.equals(action[0], "SHALLOW")) {
            String nume = action[1];
            String linie;
            while ((linie = fin.readLine()) != null) {
                sargs = linie.split(",");
                if (Objects.equals(sargs[0], nume)){
                    Student student = new Student(sargs[0],Integer.parseInt( sargs[1]),new Adresa(sargs[2],sargs[3]));
                    Student clone = (Student) student.clone();
                    Adresa axu = clone.getAdresa();
                    axu.setOras("MODIFICAT");
                    clone.setAdresa(axu);
                    System.out.println("Original: " + student);
                    System.out.println("Clona: " + clone);

                }
            }
        } else if (Objects.equals(action[0], "DEEP")) {
            String nume = action[1];
            String linie;
            while ((linie = fin.readLine()) != null) {
                sargs = linie.split(",");
                if (Objects.equals(sargs[0], nume)){
                    Student student = new Student(sargs[0],Integer.parseInt( sargs[1]),new Adresa(sargs[2],sargs[3]));
                    Student clone = (Student) student.deppClone();
                    Adresa axu = clone.getAdresa();
                    axu.setOras("MODIFICAT");
                    clone.setAdresa(axu);
                    System.out.println("Original: " + student);
                    System.out.println("Clona: " + clone);

                }
            }
        }
        fin.close();
        //System.out.println("TODO: implementează exercițiul 1");
    }
}
