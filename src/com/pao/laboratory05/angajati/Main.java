package com.pao.laboratory05.angajati;

import java.util.Scanner;

/**
 * Exercise 3 — Angajați
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 3 — Angajați"
 *
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");
            // citește opțiunea și execută acțiunea


            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1":
                        System.out.print("Nume: ");
                        String name = scanner.nextLine().trim();
                        System.out.print("Departament(nume): ");
                        String numeDept = scanner.nextLine().trim();
                        System.out.print("Departament(locatie): ");
                        String locatie = scanner.nextLine().trim();
                        System.out.print("Salariu: ");
                        int salariu = Integer.parseInt(scanner.nextLine().trim());
                        service.addAngajat(new Angajat(name, new Departament(numeDept, locatie), salariu));
                        break;

                    case "2":
                        service.listBySalary();
                        break;

                    case "3":
                        System.out.print("Departament: ");
                        String dept = scanner.nextLine().trim();
                        service.findByDepartament(dept);
                        break;

                    case "0":
                        System.out.println("La revedere!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Opțiune invalidă.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Eroare: Introdu un număr valid.");
            } catch (RuntimeException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
        }


    }
}