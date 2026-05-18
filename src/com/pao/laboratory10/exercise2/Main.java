package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {

    public static void UNIQUE_IDS(ArrayList<Tranzactie> list){
        LinkedHashSet<Integer> hashSet = new LinkedHashSet<>();
        for (Tranzactie l : list){
            hashSet.add(l.getId());
        }

        System.out.print("IDs unice ("+ hashSet.size() +"): [");
        Iterator<Integer> i = hashSet.iterator();
        while (i.hasNext()){
            System.out.print(i.next());
            if(i.hasNext()){
                System.out.print(", ");
            }
        }

        System.out.print("]");

    }
    public static void MONTHLY_REPORT(ArrayList<Tranzactie> list){
        TreeMap<String, double[]> months = new TreeMap<>();
        for (Tranzactie tranzactie : list){
            double debit, credit;

            if (tranzactie.getTip() == TipTranzactie.DEBIT){
                credit = 0;
                debit = tranzactie.getSuma();
            } else {
                credit = tranzactie.getSuma();;
                debit = 0;
            }

            double[] aux;
            if (!months.containsKey(tranzactie.getData().substring(0,7))){
                aux = new double[]{credit, debit};
            }
            else {
                aux = months.get(tranzactie.getData().substring(0, 7));
                aux[0] += credit;
                aux[1] += debit;
            }
            months.put(tranzactie.getData().substring(0,7), aux);

        }

        for  (String luna : months.keySet()){
            double[] aux = months.get(luna);
            System.out.println(luna + ": CREDIT " + String.format("%.2f", aux[0]) + " RON, DEBIT "
                    + String.format("%.2f", aux[1]) + " RON");
        }
    }
    public static void TOP(ArrayList<Tranzactie> list, int n){
        ArrayList<Tranzactie> aux = new ArrayList<>(list);
        aux.sort( (x, y) -> (int) (y.getSuma() - x.getSuma()));

        aux = (ArrayList<Tranzactie>) aux.subList(0,n);

        System.out.println("Top " + n + ":");
        for (Tranzactie tranzactie : aux){
            System.out.println(tranzactie);
        }

    }
    public static void SORT_ASC(ArrayList<Tranzactie> list){
        Collections.sort(list);

        for (Tranzactie tranzactie : list){
            System.out.println(tranzactie);
        }
    }
    public static void SORT_DESC(ArrayList<Tranzactie> list){
        Collections.sort(list);
        Collections.reverse(list);

        for (Tranzactie tranzactie : list){
            System.out.println(tranzactie);
        }
    }
    public static void REVERSE(ArrayList<Tranzactie> list){
        Collections.reverse(list);

        for (Tranzactie tranzactie : list){
            System.out.println(tranzactie);
        }
    }
    public static void MIN_MAX(ArrayList<Tranzactie> list){

        System.out.println("MIN: " + Collections.min(list));
        System.out.println("MAX: " + Collections.max(list));

    }
    public static void CME_DEMO(ArrayList<Tranzactie> list){
        try {
            for (Tranzactie aux : list){
                list.add(new Tranzactie(0,0,"", null));
            }
        } catch (ConcurrentModificationException exception){
            System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
        }
    }
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

        ArrayList<Tranzactie> tranzactieArrayList = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        String[] comanda;
        int N = Integer.parseInt(in.nextLine());

        for (int i = 1 ; i<=N; i++){
            comanda = in.nextLine().split(" ");
            tranzactieArrayList.add(new Tranzactie(Integer.parseInt(comanda[0]), Double.parseDouble(comanda[1]), comanda[2], TipTranzactie.valueOf(comanda[3])));
        }



        while(in.hasNext()) {
            comanda = in.nextLine().split(" ");

            if (Objects.equals(comanda[0], "UNIQUE_IDS")) {
                UNIQUE_IDS(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "MONTHLY_REPORT")) {
                MONTHLY_REPORT(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "TOP")) {
                TOP(tranzactieArrayList, Integer.parseInt(comanda[1]));
            } else if (Objects.equals(comanda[0], "REVERSE")) {
                REVERSE(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "SORT_ASC")) {
                SORT_ASC(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "SORT_DESC")) {
                SORT_DESC(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "MIN_MAX")) {
                MIN_MAX(tranzactieArrayList);
            } else if (Objects.equals(comanda[0], "CME_DEMO")) {
                CME_DEMO(tranzactieArrayList);
            }

        }

        //System.out.println("TODO: implementează exercițiul 2");
    }
}
