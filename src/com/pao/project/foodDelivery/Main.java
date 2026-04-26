package com.pao.project.foodDelivery;


import com.pao.project.foodDelivery.model.*;
import com.pao.project.foodDelivery.exception.*;
import com.pao.project.foodDelivery.service.*;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEM DE COMENZI - DELIVERY ===\n");

        UtilizatorService utilizatorService = UtilizatorService.getInstanta();
        ComandaService comandaService = ComandaService.getInstanta();

        try {
            System.out.println("1. Adaugă utilizatori noi în sistem:");
            Utilizator user1 = new Utilizator("ion.popescu@email.com", "ionpop");
            Utilizator user2 = new Utilizator("maria.ionescu@email.com", "mariaion");
            Utilizator user3 = new Utilizator("alex.dimitriu@email.com", "alexdim");

            utilizatorService.addUtilizator(user1);
            utilizatorService.addUtilizator(user2);
            utilizatorService.addUtilizator(user3);

            System.out.println("Utilizatori adăugați: " + user1.getUsername() + ", " + user2.getUsername() + ", " + user3.getUsername());
            System.out.println();

            System.out.println("2. Înregistrează livratori noi:");
            Livrator livrator1 = new Livrator("Ionuț Șofer");
            Livrator livrator2 = new Livrator("Maria Rapid");
            System.out.println("Livratori înregistrați: " + livrator1.getNume() + ", " + livrator2.getNume());
            System.out.println();

            System.out.println("3. Creează restaurante cu meniu:");
            Meniu meniuPizza = new Meniu();
            meniuPizza.add(new Articol("Pizza Margherita", 35.0));
            meniuPizza.add(new Articol("Pizza Pepperoni", 40.0));
            meniuPizza.add(new Articol("Salată", 15.0, true));

            Local localPizza = new Local("Pizzeria Italia", "Strada Mareșal Foch", 15, meniuPizza);

            Meniu meniuSushi = new Meniu();
            meniuSushi.add(new Articol("Sushi Mix", 50.0));
            meniuSushi.add(new Articol("California Roll", 45.0));
            meniuSushi.add(new Articol("Edamame", 10.0, true));

            Local localSushi = new Local("Sushi House", "Bulevardul Magheru", 32, meniuSushi);
            System.out.println("Restaurante create: " + localPizza.getNume() + ", " + localSushi.getNume());
            System.out.println();

            System.out.println("4. Creează adrese de livrare:");
            Domiciliu domiciliu1 = new Domiciliu("Strada Victoriei", 25, 4, 12);
            Domiciliu domiciliu2 = new Domiciliu("Avenida Brătianu", 100, 2, 5);
            System.out.println("Adrese create: " + domiciliu1.toString() + ", " + domiciliu2.toString());
            System.out.println();

            System.out.println("5. Creează comenzi noi:");
            ArrayList<Articol> articole1 = new ArrayList<>();
            articole1.add(meniuPizza.get("Pizza Margherita"));
            articole1.add(meniuPizza.get("Salată"));

            Comanda comanda1 = new Comanda(articole1, user1, livrator1, localPizza, domiciliu1);
            comandaService.addComanda(comanda1);

            ArrayList<Articol> articole2 = new ArrayList<>();
            articole2.add(meniuSushi.get("Sushi Mix"));

            Comanda comanda2 = new Comanda(articole2, user2, livrator2, localSushi, domiciliu2);
            comandaService.addComanda(comanda2);

            ArrayList<Articol> articole3 = new ArrayList<>();
            articole3.add(meniuPizza.get("Pizza Pepperoni"));
            articole3.add(meniuPizza.get("Salată"));

            Comanda comanda3 = new Comanda(articole3, user3, livrator1, localPizza, domiciliu1);
            comandaService.addComanda(comanda3);

            System.out.println("Comenzi create: ID " + comanda1.getId() + ", " + comanda2.getId() + ", " + comanda3.getId());
            System.out.println();

            System.out.println("6. Finalizează comenzi:");
            comanda1.finishComanda();
            comanda2.finishComanda();
            System.out.println("Comenzi finalizate: ID " + comanda1.getId() + ", " + comanda2.getId());
            System.out.println();

            System.out.println("7. Caută o comandă după ID:");
            try {
                Comanda comandaGasita = comandaService.getComandaById(comanda1.getId());
                System.out.println("Comandă găsită: " + comandaGasita);
            } catch (ComandaNegasitaException e) {
                System.out.println("Eroare: " + e.getMessage());
            }
            System.out.println();

            System.out.println("8. Listează comenzile sortate pe timp:");
            List<Comanda> comenziSortate = comandaService.getComenziSorted();
            for (Comanda c : comenziSortate) {
                System.out.println("  - " + c.toString());
            }
            System.out.println();

            System.out.println("9. Afișează banii obținuți de livratori:");
            System.out.println("  - " + livrator1.getNume() + ": " + livrator1.baniObtinuti() + " lei");
            System.out.println("  - " + livrator2.getNume() + ": " + livrator2.baniObtinuti() + " lei");
            System.out.println();

            System.out.println("10. Adaugă puncte de fidelitate și caută utilizatori:");
            user1.addPuncte(50);
            user2.addPuncte(30);

            Utilizator utilizatorGasit = utilizatorService.getUtilizatorByEmail("ion.popescu@email.com");
            System.out.println("Utilizator găsit: " + utilizatorGasit.toString());

            List<Utilizator> utilizatoriSortati = utilizatorService.getUtilizatoriSortedByPuncte();
            System.out.println("Utilizatori sortați după puncte:");
            for (Utilizator u : utilizatoriSortati) {
                System.out.println("  - " + u.getUsername() + ": " + u.getPuncte() + " puncte");
            }
            System.out.println();

            // Informații suplimentare
            System.out.println("=== REZUMAT FINAL ===");
            System.out.println("Total utilizatori: " + utilizatorService.getUtilizatoriCount());
            System.out.println("Total comenzi: " + comandaService.getComenziCount());
            System.out.println("Livrator cu cele mai mari venituri: " + livrator1.getNume() + " (" + livrator1.baniObtinuti() + " lei)");

        } catch (UtilizatorNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}
