package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void afisareColabSortati(ArrayList<Colaborator> colaborators){
        ArrayList<Colaborator> sorted = colaborators;
        sorted.sort(null);

        for(Colaborator colaborator : sorted){
            colaborator.afiseaza();
        }
    }

    public static void afisareColab(ArrayList<Colaborator> colaborators){

        for(Colaborator colaborator : colaborators){
            colaborator.afiseaza();
        }
    }


    public static void afiseazaVenitNetMaxim(ArrayList<Colaborator> colaborators){
        Colaborator maxim = colaborators.getFirst();
        for (Colaborator colaborator : colaborators){
            if (colaborator.calculeazaVenitNetAnual() > maxim.calculeazaVenitNetAnual()){
                maxim = colaborator;
            }
        }

        System.out.print("Colaborator cu venit net maxim: ");
        maxim.afiseaza();
    }

    public static void afiseazaPersJuridice(ArrayList<Colaborator> colaborators){
        System.out.println("Colaboratori persoane juridice:");
        for (Colaborator colaborator :colaborators){
            if (Objects.equals(colaborator.tipContract(), "SRL")){
                colaborator.afiseaza();
            }
        }
    }

    public static void afiseazaSumePeTip(ArrayList<Colaborator> colaborators){
        System.out.println("Sume și număr colaboratori pe tip:");

        Double CIMSuma=0.0, PFASuma=0.0, SRLSuma=0.0;
        Integer CIMNumar=0, PFANumar=0, SRLNumar=0;


        for (Colaborator colaborator :colaborators){

            switch (colaborator.tipContract()){
                case "SRL":
                    SRLSuma += colaborator.calculeazaVenitNetAnual();
                    SRLNumar ++;
                    break;
                case "CIM":
                    CIMSuma += colaborator.calculeazaVenitNetAnual();
                    CIMNumar ++;
                    break;
                case "PFA":
                    PFASuma += colaborator.calculeazaVenitNetAnual();
                    PFANumar ++;
                    break;
            }
        }
        if (CIMNumar > 0)
            System.out.println("CIM: suma = " + String.format( "%.2f", CIMSuma) + " lei, număr = " + CIMNumar);
        if (PFANumar > 0)

            System.out.println("PFA: suma = " + String.format( "%.2f", PFASuma) + " lei, număr = " + PFANumar);
        if (SRLNumar > 0)

            System.out.println("SRL: suma = " + String.format( "%.2f", SRLSuma) + " lei, număr = " + SRLNumar);
    }

    public static void main(String[] args) {
        // Vezi Readme.md pentru cerințe

        Scanner in = new Scanner(System.in) ;
        ArrayList<Colaborator> colaborators = new ArrayList<Colaborator>();

        int nr = in.nextInt();

        for (int i = 0; i < nr; i++) {
            String tip = in.next();
            Colaborator aux = null;

            switch (tip){
                case "SRL":
                    aux = new SRLColaborator();
                    aux.citeste(in);
                    break;
                case "CIM":
                    aux = new CIMColaborator();
                    aux.citeste(in);
                    break;
                case "PFA":
                    aux = new PFAColaborator();
                    aux.citeste(in);
                    break;
            }

            colaborators.add(aux);

        }

        afisareColab(colaborators);

        System.out.println();

        afiseazaVenitNetMaxim(colaborators);

        System.out.println();


        afiseazaPersJuridice(colaborators);

        System.out.println();


        afiseazaSumePeTip(colaborators);


        in.close();

    }
}