# Sistem de Comenzi - Aplicație de Delivery

## 1. Definiția sistemului

### 1.1 — Lista cu acțiuni / interogări posibile în sistem

1. Adaugă un utilizator nou în sistem
2. Înregistrează un livrator nou
3. Creează o comandă nouă
4. Finalizează o comandă
5. Caută o comandă după ID
6. Listează toate comenzile în ordine cronologică
7. Afișează istoricul comenzilor unui utilizator
8. Calculează banii obținuți de un livrator
9. Adaugă puncte de fidelitate unui utilizator
10. Caută un utilizator după username sau email

### 1.2 — Lista cu tipuri de obiecte din domeniu

1. `Utilizator` - clientul care plasează comenzi
2. `Comanda` - comanda de livrare cu articole
3. `Livrator` - persoana responsabilă cu livrarea
4. `Local` - restaurantul/magazinul care furnizează articolele
5. `Domiciliu` - adresa de livrare a clientului
6. `Locatie` - clasa de bază pentru Local și Domiciliu
7. `Meniu` - colecția de articole disponibile la un local
8. `Articol` - articol din meniu (produs individual)
9. `RecordComanda` - înregistrare imutabilă a unei comenzi finalizate
10. `ComandaService` - serviciu singleton pentru gestionarea comenzilor
11. `UtilizatorService` - serviciu singleton pentru gestionarea utilizatorilor

## 2. Implementare Java

### 2.1 — Caracteristici OOP

- 8+ clase care modelează obiectele sistemului
- Atribute private/protected cu getteri/setteri
- Metode `toString()`, `equals()` și `hashCode()` suprascrise în Utilizator și Livrator
- Ierarhie de moștenire: `Locatie` (abstractă) → `Domiciliu` și `Local`
- Clasă abstractă: `Locatie` cu metode abstracte și implementări
- Clasă imutabilă: `RecordComanda` cu atribute final și fără setteri
- 3 excepții custom: `ComandaNegasitaException`, `UtilizatorNegasitException`, `StocInsuficientException`

### 2.2 — Colecții

- `ArrayList<Comanda>` - pentru istoricul comenzilor ale unui utilizator
- `PriorityQueue<Comanda>` - pentru coada de comenzi sortate
- `HashMap<String, Utilizator>` - pentru indexarea utilizatorilor pe email
- `HashMap<String, Articol>` - pentru meniu indexat pe nume articol
- Implementare `Comparable<Comanda>` pentru sortare după timp
- Map<String, Articol> pentru gruparea articolelor pe nume în meniu

### 2.3 — Servicii Singleton

- `ComandaService` - gestionează comenzile (adaugă, șterge, caută, listează)
- `UtilizatorService` - gestionează utilizatorii (adaugă, șterge, caută, listează)
- Ambele implementate ca Singleton cu constructor privat și metodă `getInstanta()`

### 2.4 — Organizare

- Cod organizat în pachete: `model/`, `service/`, `exception/`
- Fără cod duplicat
- Validare inputuri și tratare excepții
