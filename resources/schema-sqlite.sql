-- ============================================================
--  schema-sqlite.sql  —  Laboratory 12 / Proiect Etapa II
--  Compatibil cu: SQLite 3.x (via sqlite-jdbc driver)
--  IMPORTANT: FK-urile sunt ignorate implicit in SQLite.
--  Activeaza-le programatic cu: PRAGMA foreign_keys = ON;
--  (DatabaseConnection.java face asta automat)
-- ============================================================

PRAGMA foreign_keys = ON;

-- Ordinea DROP conteaza: intai tabelele cu FK, apoi cele referite
--DROP TABLE IF EXISTS articol;
--DROP TABLE IF EXISTS comanda;
--DROP TABLE IF EXISTS domiciliu;
--DROP TABLE IF EXISTS livrator;
--DROP TABLE IF EXISTS local;
--DROP TABLE IF EXISTS locatie;
--DROP TABLE IF EXISTS meniu;
--DROP TABLE IF EXISTS recordccomanda;
--DROP TABLE IF EXISTS utilizator;




-- -------------------------------------------------------
--  In SQLite: tipul coloanei e mai permisiv, dar conventiile conteaza
--  INTEGER PRIMARY KEY = alias pentru rowid (auto-increment implicit)
--  AUTOINCREMENT garanteaza ca ID-urile nu se reutilizeaza dupa DELETE
-- -------------------------------------------------------
--CREATE TABLE author (
--    id      INTEGER PRIMARY KEY AUTOINCREMENT,
--    name    TEXT    NOT NULL,
--    country TEXT
--);
--
---- -------------------------------------------------------
--CREATE TABLE book (
--    id        INTEGER PRIMARY KEY AUTOINCREMENT,
--    title     TEXT    NOT NULL,
--    author_id INTEGER NOT NULL,
--    available INTEGER NOT NULL DEFAULT 1,       -- 1=disponibil, 0=imprumutat
--    FOREIGN KEY (author_id) REFERENCES author(id)
--        ON DELETE CASCADE
--        ON UPDATE CASCADE
--);
--
---- -------------------------------------------------------
--CREATE TABLE reader (
--    id    INTEGER PRIMARY KEY AUTOINCREMENT,
--    name  TEXT NOT NULL,
--    email TEXT
--);
--
---- -------------------------------------------------------
--CREATE TABLE loan (
--    id          INTEGER PRIMARY KEY AUTOINCREMENT,
--    book_id     INTEGER NOT NULL,
--    reader_id   INTEGER NOT NULL,
--    loan_date   TEXT    NOT NULL,              -- format ISO: "YYYY-MM-DD"
--    return_date TEXT,                          -- NULL = imprumut activ
--    FOREIGN KEY (book_id)   REFERENCES book(id),
--    FOREIGN KEY (reader_id) REFERENCES reader(id)
--        ON DELETE CASCADE
--        ON UPDATE CASCADE
--);

CREATE TABLE IF NOT EXISTS articol (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL,
    pret REAL NOT NULL,
    vegan INTEGER NOT NULL CHECK (vegan IN (0, 1))
);

CREATE TABLE IF NOT EXISTS locatie (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    strada TEXT NOT NULL,
    numar INTEGER NOT NULL,
    scara INTEGER NOT NULL,
    apartament INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS domiciliu (
    id_locatie INTEGER PRIMARY KEY,
    FOREIGN KEY (id_locatie) REFERENCES locatie(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS meniu (
    id INTEGER PRIMARY KEY AUTOINCREMENT
);

CREATE TABLE IF NOT EXISTS meniu_articole (
    id_meniu INTEGER NOT NULL,
    cheie_map TEXT NOT NULL, -- Stores the String key of the HashMap
    id_articol INTEGER NOT NULL, -- Foreign Key to the articol table
    PRIMARY KEY (id_meniu, cheie_map), -- Ensures a key is unique per menu
    FOREIGN KEY (id_meniu) REFERENCES meniu(id) ON DELETE CASCADE,
    FOREIGN KEY (id_articol) REFERENCES articol(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS local (
    id_locatie INTEGER PRIMARY KEY,
    nume TEXT NOT NULL,
    id_meniu INTEGER NOT NULL, -- Foreign Key to Meniu table
    FOREIGN KEY (id_locatie) REFERENCES locatie(id) ON DELETE CASCADE,
    FOREIGN KEY (id_meniu) REFERENCES meniu(id)
);

CREATE TABLE IF NOT EXISTS livrator (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nume TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS utilizator (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    puncte INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS comanda (
    id INTEGER PRIMARY KEY, -- Handled by Java's nextId, the definition of legacy jank
    id_utilizator INTEGER NOT NULL,
    id_livrator INTEGER NOT NULL,
    id_plecare INTEGER NOT NULL,   -- Points to local(id_locatie)
    id_destinatie INTEGER NOT NULL, -- Points to domiciliu(id_locatie)
    status TEXT NOT NULL CHECK(status IN ('TERMINATA', 'NETERMINATA')),
    timestamp INTEGER NOT NULL, -- SQLite stores dates as Unix epoch integers or text

    FOREIGN KEY (id_livrator) REFERENCES livrator(id) ON DELETE SET NULL,
    FOREIGN KEY (id_plecare) REFERENCES local(id_locatie),
    FOREIGN KEY (id_destinatie) REFERENCES domiciliu(id_locatie),
    FOREIGN KEY (id_utilizator) REFERENCES utilizator(id) -- Once you create the Utilizator table
);

CREATE TABLE IF NOT EXISTS comanda_articole (
    id_comanda INTEGER NOT NULL,
    id_articol INTEGER NOT NULL,
    PRIMARY KEY (id_comanda, id_articol),
    FOREIGN KEY (id_comanda) REFERENCES comanda(id) ON DELETE CASCADE,
    FOREIGN KEY (id_articol) REFERENCES articol(id) ON DELETE CASCADE
);

CREATE VIEW IF NOT EXISTS v_record_comanda AS
SELECT
    c.id AS comandaId,
    u.username AS utilizatorUsername,
    l.nume AS livratorNume,
    (SELECT SUM(a.pret) FROM comanda_articole ca JOIN articol a ON ca.id_articol = a.id WHERE ca.id_comanda = c.id) AS pretTotal,
    c.timestamp AS dataComanda,
    c.status AS status
FROM comanda c
JOIN utilizator u ON c.id_utilizator = u.id
LEFT JOIN livrator l ON c.id_livrator = l.id;