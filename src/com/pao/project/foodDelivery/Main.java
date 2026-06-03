package com.pao.project.foodDelivery;


import com.pao.project.foodDelivery.model.*;
import com.pao.project.foodDelivery.exception.*;
import com.pao.project.foodDelivery.service.*;
import com.pao.project.foodDelivery.repository.*;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("=== SISTEM DE COMENZI - DELIVERY ===\n");

        

        UtilizatorService utilizatorService = UtilizatorService.getInstanta();
        ComandaService comandaService = ComandaService.getInstanta();
        AuditService auditService = AuditService.getInstance();

        try {
            System.out.println("1. Adaugă utilizatori noi în sistem:");
            Utilizator user1 = new Utilizator("ion.popescu@email.com", "ionpop");
            Utilizator user2 = new Utilizator("maria.ionescu@email.com", "mariaion");
            Utilizator user3 = new Utilizator("alex.dimitriu@email.com", "alexdim");

            utilizatorService.addUtilizator(user1);
            utilizatorService.addUtilizator(user2);
            utilizatorService.addUtilizator(user3);
            
            // Save users to database
            try {
                UtilizatorRepository ur = new UtilizatorRepository();
                ur.save(user1);
                ur.save(user2);
                ur.save(user3);
            } catch (SQLException e) {
                System.out.println("  [DB] Avertisment: Nu am putut salva utilizatorii în DB: " + e.getMessage());
            }

            System.out.println("Utilizatori adăugați: " + user1.getUsername() + ", " + user2.getUsername() + ", " + user3.getUsername());
            System.out.println();

            System.out.println("2. Înregistrează livratori noi:");
            Livrator livrator1 = new Livrator("Ionuț Șofer");
            Livrator livrator2 = new Livrator("Maria Rapid");
            System.out.println("Livratori înregistrați: " + livrator1.getNume() + ", " + livrator2.getNume());
            auditService.log("register_livrator");
            System.out.println();

            System.out.println("3. Creează restaurante cu meniu:");
            Meniu meniuPizza = new Meniu();
            Articol pizzaMargherita = new Articol("Pizza Margherita", 35.0);
            Articol pizzaPepperoni = new Articol("Pizza Pepperoni", 40.0);
            Articol salata = new Articol("Salată", 15.0, true);
            meniuPizza.add(pizzaMargherita);
            meniuPizza.add(pizzaPepperoni);
            meniuPizza.add(salata);

            Local localPizza = new Local("Pizzeria Italia", "Strada Mareșal Foch", 15, meniuPizza);

            Meniu meniuSushi = new Meniu();
            Articol sushiMix = new Articol("Sushi Mix", 50.0);
            Articol californiaRoll = new Articol("California Roll", 45.0);
            Articol edamame = new Articol("Edamame", 10.0, true);
            meniuSushi.add(sushiMix);
            meniuSushi.add(californiaRoll);
            meniuSushi.add(edamame);

            Local localSushi = new Local("Sushi House", "Bulevardul Magheru", 32, meniuSushi);
            System.out.println("Restaurante create: " + localPizza.getNume() + ", " + localSushi.getNume());
            auditService.log("create_local");
            
            // Save articles to database
            try {
                ArticolRepository ar = new ArticolRepository();
                ar.save(pizzaMargherita);
                ar.save(pizzaPepperoni);
                ar.save(salata);
                ar.save(sushiMix);
                ar.save(californiaRoll);
                ar.save(edamame);
            } catch (SQLException e) {
                System.out.println("  [DB] Avertisment: Nu am putut salva articolele în DB: " + e.getMessage());
            }
            
            // Save restaurants to database
            try {
                LocalRepository locr = new LocalRepository();
                locr.save(localPizza);
                locr.save(localSushi);
            } catch (SQLException e) {
                System.out.println("  [DB] Avertisment: Nu am putut salva restaurantele în DB: " + e.getMessage());
            }
            System.out.println();

            System.out.println("4. Creează adrese de livrare:");
            Domiciliu domiciliu1 = new Domiciliu("Strada Victoriei", 25, 4, 12);
            Domiciliu domiciliu2 = new Domiciliu("Avenida Brătianu", 100, 2, 5);
            System.out.println("Adrese create: " + domiciliu1.toString() + ", " + domiciliu2.toString());
            auditService.log("create_domiciliu");
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
            comandaService.finishComanda(comanda1);
            comandaService.finishComanda(comanda2);
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
            auditService.log("check_livrator_earnings");
            System.out.println();

            System.out.println("10. Adaugă puncte de fidelitate și caută utilizatori:");
            user1.addPuncte(50);
            user2.addPuncte(30);
            user3.addPuncte(20);
            auditService.log("add_fidelity_points");

            Utilizator utilizatorGasit = utilizatorService.getUtilizatorByEmail("ion.popescu@email.com");
            System.out.println("Utilizator găsit: " + utilizatorGasit.toString());

            List<Utilizator> utilizatoriSortati = utilizatorService.getUtilizatoriSortedByPuncte();
            System.out.println("Utilizatori sortați după puncte:");
            for (Utilizator u : utilizatoriSortati) {
                System.out.println("  - " + u.getUsername() + ": " + u.getPuncte() + " puncte");
            }
            System.out.println();

            // =================================================================
            // DEMONSTRARE INTEROGĂRI SQL CU JOIN (REQUIREMENT 3 - ETAPA II)
            // =================================================================
            System.out.println("\n=== INTEROGĂRI SQL AVANSATE CU JOIN ===\n");

            System.out.println("JOIN Query 1: Toate comenzile cu detalii utilizator, livrator și restaurant:");
            System.out.println("SQL: SELECT c.*, u.username, l.nume, lo.nume FROM comanda JOIN utilizator JOIN livrator JOIN local");
            try {
                ComandaRepository comandaRepo = new ComandaRepository();
                List<String> comandaDetails = comandaRepo.findAllWithUserAndDelivererDetails();
                if (comandaDetails.isEmpty()) {
                    System.out.println("  (Nicio comandă în baza de date - memoria locală este folosită pentru demonstrație)");
                } else {
                    for (String detail : comandaDetails) {
                        System.out.println("  " + detail);
                    }
                }
            } catch (SQLException e) {
                System.out.println("  (Nu s-a putut executa - baza de date nu este configurată)");
            }
            System.out.println();

            System.out.println("JOIN Query 2: Utilizatori cu numărul de comenzi și puncte de fidelitate:");
            System.out.println("SQL: SELECT u.*, COUNT(c.id) FROM utilizator LEFT JOIN comanda GROUP BY u.id");
            try {
                UtilizatorRepository utilizatorRepo = new UtilizatorRepository();
                List<String> utilizatorStats = utilizatorRepo.findAllWithOrderStats();
                if (utilizatorStats.isEmpty()) {
                    System.out.println("  (Nicio înregistrare în baza de date - memoria locală este folosită)");
                } else {
                    for (String stat : utilizatorStats) {
                        System.out.println("  " + stat);
                    }
                }
            } catch (SQLException e) {
                System.out.println("  (Nu s-a putut executa - baza de date nu este configurată)");
            }
            System.out.println();

            System.out.println("JOIN Query 3: Restaurante cu itemuri de meniu și opțiuni vegane:");
            System.out.println("SQL: SELECT lo.nume, COUNT(ma.id_articol), SUM(CASE WHEN a.vegan=1) FROM local JOIN meniu JOIN meniu_articole JOIN articol GROUP BY lo.id");
            try {
                LocalRepository localRepo = new LocalRepository();
                List<String> localStats = localRepo.findAllWithMenuStats();
                if (localStats.isEmpty()) {
                    System.out.println("  (Nicio înregistrare în baza de date - memoria locală este folosită)");
                } else {
                    for (String stat : localStats) {
                        System.out.println("  " + stat);
                    }
                }
            } catch (SQLException e) {
                System.out.println("  (Nu s-a putut executa - baza de date nu este configurată)");
            }
            System.out.println();

            System.out.println("JOIN Query 4: Livratori cu numărul de livrări și venituri totale:");
            System.out.println("SQL: SELECT l.*, COUNT(c.id), SUM(articol.pret) FROM livrator LEFT JOIN comanda JOIN comanda_articole JOIN articol GROUP BY l.id");
            try {
                LivratorRepository livratorRepo = new LivratorRepository();
                List<String> livratorStats = livratorRepo.findAllWithEarningStats();
                if (livratorStats.isEmpty()) {
                    System.out.println("  (Nicio înregistrare în baza de date - memoria locală este folosită)");
                } else {
                    for (String stat : livratorStats) {
                        System.out.println("  " + stat);
                    }
                }
            } catch (SQLException e) {
                System.out.println("  (Nu s-a putut executa - baza de date nu este configurată)");
            }
            System.out.println();

            // =================================================================
            // DEMONSTRARE TRANZACȚII JDBC (REQUIREMENT 2 - ETAPA II)
            // =================================================================
            System.out.println("=== TRANZACȚII JDBC EXPLICITE ===\n");
            System.out.println("Demonstrație: Salvarea unei comenzi cu múltiple tabele în o tranzacție JDBC:");
            System.out.println("1. setAutoCommit(false) — start transaction");
            System.out.println("2. INSERT INTO comanda...");
            System.out.println("3. INSERT INTO comanda_articole... (pentru fiecare articol)");
            System.out.println("4. commit() — confirma dacă toate operațiile au reușit");
            System.out.println("5. rollback() — dacă apare o eroare, revenire la starea anterioară");
            System.out.println();

            System.out.println("Testare: Salvarea unei noi comenzi cu tranzacție:");

            ComandaRepository comandaRepo = new ComandaRepository();

            // Create a test order with articles
            ArrayList<Articol> testArticole = new ArrayList<>();
            Articol pizza = new Articol("Pizza Diavola", 42.0);
            pizza.setId(1);  // Simulate that article exists in DB
            testArticole.add(pizza);

            Comanda testComanda = new Comanda(testArticole, user1, livrator1, localPizza, domiciliu1);

            // Try to save with transaction
            System.out.println("Calling saveWithTransaction() pentru Comanda #" + testComanda.getId() + " cu " + testArticole.size() + " articol(e)...");
            try {
                comandaRepo.saveWithTransaction(testComanda);
                System.out.println("✓ Tranzacția a fost executată cu succes!");
            } catch (SQLException transactionError) {
                System.out.println("✗ Eroare în tranzacție (rolled back): " + transactionError.getMessage());
                System.out.println("  Notă: Eroarea este așteptată dacă baza de date nu are IDs-urile necesare în tabele.");
            }
            System.out.println();

            // =================================================================
            // DEMONSTRARE AUDIT SERVICE (REQUIREMENT 4 - ETAPA II)
            // =================================================================
            System.out.println("=== AUDIT SERVICE ===");
            System.out.println("Toate acțiunile de mai sus au fost loggate în fișierul 'audit.csv'");
            System.out.println("Format audit: action_name,timestamp");
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
