package com.pao.laboratory10.exercise1;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void ENQUEUE(LinkedList<Tranzactie> list,int id, double suma, String data, TipTranzactie tip){
        list.addLast(new Tranzactie(id, suma, data, tip));
    }

    public static void DEQUEUE(LinkedList<Tranzactie> list){
        Tranzactie t;
        if (list.peekFirst() != null){
            t = list.removeFirst();
        } else {
             t = null;
        }

        if (t == null){
            System.out.println("Coada goala.");
        }
        else {
            System.out.println("Procesat: " + t);
        }
    }

    public static void PUSH(LinkedList<Tranzactie> list,int id, double suma, String data, TipTranzactie tip){
        list.addFirst(new Tranzactie(id, suma, data, tip));
    }

    public static void POP(LinkedList<Tranzactie> list){
        Tranzactie t;
        if (list.peekFirst() != null){
            t = list.removeFirst();
        } else {
            t = null;
        }

        if (t == null){
            System.out.println("Coada goala.");
        }
        else {
            System.out.println("Extras: " + t);
        }
    }

    public static void REMOVE_DEBIT(LinkedList<Tranzactie> list){
        Iterator<Tranzactie> itr = list.iterator();
        int N = 0;
        while (itr.hasNext()){
            if(itr.next().getTip() == TipTranzactie.DEBIT){
                itr.remove(); N++;
            }
        }
        System.out.println("Eliminat " + N + " tranzactii DEBIT.");
    }

    public static void REMOVE_BELOW(LinkedList<Tranzactie> list, double suma){
        Iterator<Tranzactie> itr = list.iterator();
        int N = 0;
        while (itr.hasNext()){
            if((itr.next()).getSuma() < suma){
                itr.remove(); N++;
            }
        }
        System.out.println("Eliminat " + N + " tranzactii sub " + String.format("%.2f", suma) + " RON.");
    }

    public static void PRINT(LinkedList<Tranzactie> list){
        Iterator<Tranzactie> itr = list.iterator();
        while (itr.hasNext()){
            System.out.println( itr.next());
        }
    }

    public static void SIZE(LinkedList<Tranzactie> list){
        System.out.println("Dimensiune coada: " + list.size());
    }



    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // Folosește LinkedList<Tranzactie> ca structură internă.
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON

        LinkedList<Tranzactie> tranzactieLinkedList = new LinkedList<Tranzactie>();
        Scanner in = new Scanner(System.in);
        String[] comanda;

        while(in.hasNext()){
            comanda = in.nextLine().split(" ");

            if (Objects.equals(comanda[0], "ENQUEUE")){
                ENQUEUE(tranzactieLinkedList, Integer.parseInt(comanda[1]), Double.parseDouble(comanda[2]), comanda[3], TipTranzactie.valueOf(comanda[4]));
            } else if (Objects.equals(comanda[0], "DEQUEUE")){
                DEQUEUE(tranzactieLinkedList);
            } else if (Objects.equals(comanda[0], "PUSH")){
                PUSH(tranzactieLinkedList, Integer.parseInt(comanda[1]), Double.parseDouble(comanda[2]), comanda[3], TipTranzactie.valueOf(comanda[4]));
            } else if (Objects.equals(comanda[0], "POP")) {
                POP(tranzactieLinkedList);
            } else if (Objects.equals(comanda[0], "REMOVE_DEBIT")) {
                REMOVE_DEBIT(tranzactieLinkedList);
            } else if (Objects.equals(comanda[0], "REMOVE_BELOW")) {
                REMOVE_BELOW(tranzactieLinkedList, Double.parseDouble(comanda[1]));
            } else if (Objects.equals(comanda[0], "PRINT")) {
                PRINT(tranzactieLinkedList);
            } else if (Objects.equals(comanda[0], "SIZE")) {
                SIZE(tranzactieLinkedList);
            }

        }


        // System.out.println("TODO: implementează exercițiul 1");
    }
}
