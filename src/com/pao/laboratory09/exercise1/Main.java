package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {

    public void list(){}

    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        Scanner in = new Scanner(System.in);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE));

        int N = Integer.parseInt(in.nextLine());
        for (int i =1; i<= N; i++){
            String[] detallii = in.nextLine().split(" ");
            Tranzactie aux = new Tranzactie(Integer.parseInt(detallii[0]), Double.parseDouble(detallii[1]), detallii[2], detallii[3], detallii[4], TipTranzactie.valueOf(detallii[5])) ;
            outputStream.writeObject(aux);
        }
        outputStream.close();

        while (in.hasNext()) {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(OUTPUT_FILE));
            String[] comanda = in.nextLine().split(" ");
            if (Objects.equals(comanda[0], "LIST")) {
                for (int i = 1; i <= N; ++i) {

                    Tranzactie aux = (Tranzactie) inputStream.readObject();
                    System.out.println(aux);
                }
            } else if (Objects.equals(comanda[0], "FILTER")) {
                boolean ok = false;
                for (int i = 1; i <= N; ++i) {

                    Tranzactie aux = (Tranzactie) inputStream.readObject();
                    String data = aux.getData().split("-")[0] + '-' + aux.getData().split("-")[1];
                    if (Objects.equals(comanda[1], data)) {
                        System.out.println(aux);
                        ok = true;
                    }

                }
                if (!ok) {
                    System.out.println("Niciun rezultat.");
                }
            } else if (Objects.equals(comanda[0], "NOTE")) {
                boolean ok = false;
                for (int i = 1; i <= N; ++i) {

                    Tranzactie aux = (Tranzactie) inputStream.readObject();
                    if (Integer.parseInt(comanda[1]) ==  aux.getId()) {
                        System.out.println("NOTE[" + comanda[1] + "]: " + aux.getNote()); ok = true;
                    }

                }
                if (!ok){
                    System.out.println("NOTE[" + comanda[1] + "]: not found");
                }
            }
        }


        //System.out.println("TODO: implementează exercițiul 1");
    }
}
