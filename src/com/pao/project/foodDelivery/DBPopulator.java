package com.pao.project.foodDelivery;

import java.sql.*;

//A fost rulat o singura data pentru a insera date in DB pentru a scapa de erori neprevazute si inexplicabile
public class DBPopulator {

    private static final String DB_URL = "jdbc:sqlite:proiect2.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                // 1. Explicitly enforce foreign keys for this connection session
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("PRAGMA foreign_keys = ON;");
                }

                System.out.println("Connected to SQLite database. Populating data...");

                // 2. Insert Independent Data
                int idArticol1 = insertArticol(conn, "Pizza Margherita", 35.50, 0); // 0 = false
                int idArticol2 = insertArticol(conn, "Burger Vegan", 42.00, 1);     // 1 = true
                int idArticol3 = insertArticol(conn, "Paste Carbonara", 38.00, 0);

                int idLocPlec = insertLocatie(conn, "Splaiul Independentei", 313, 0, 0);
                int idLocDest = insertLocatie(conn, "Bulevardul Unirii", 15, 2, 44);

                int idMeniu = insertMeniu(conn);

                int idLivrator = insertLivrator(conn, "Ion Popescu");
                int idUtilizator = insertUtilizator(conn, "andrei123", "andrei@email.com", 50);

                // 3. Insert Dependent Data (Inheritance & Sub-components)
                insertDomiciliu(conn, idLocDest);
                insertLocal(conn, idLocPlec, "Trattoria Politehnica", idMeniu);

                // Map items into the menu
                insertMeniuArticol(conn, idMeniu, "pizza_item", idArticol1);
                insertMeniuArticol(conn, idMeniu, "pasta_item", idArticol3);

                // 4. Insert Order (Using manual ID allocation as per your schema layout)
                int manualComandaId = 1;
                long currentUnixTimestamp = System.currentTimeMillis() / 1000L;

                insertComanda(conn, manualComandaId, idUtilizator, idLivrator, idLocPlec, idLocDest, "TERMINATA", currentUnixTimestamp);

                // Link articles to the order (assuming you added the comanda_articole table)
                insertComandaArticol(conn, manualComandaId, idArticol1);
                insertComandaArticol(conn, manualComandaId, idArticol3);

                System.out.println("Database population completed successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Database population failed!");
            e.printStackTrace();
        }
    }

    // ==========================================
    // HELPER INSERT METHODS
    // ==========================================

    private static int insertArticol(Connection conn, String nume, double pret, int vegan) throws SQLException {
        String sql = "INSERT INTO articol(nume, pret, vegan) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nume);
            pstmt.setDouble(2, pret);
            pstmt.setInt(3, vegan);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private static int insertLocatie(Connection conn, String strada, int numar, int scara, int apartament) throws SQLException {
        String sql = "INSERT INTO locatie(strada, numar, scara, apartament) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, strada);
            pstmt.setInt(2, numar);
            pstmt.setInt(3, scara);
            pstmt.setInt(4, apartament);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private static int insertMeniu(Connection conn) throws SQLException {
        String sql = "INSERT INTO meniu DEFAULT VALUES";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private static int insertLivrator(Connection conn, String nume) throws SQLException {
        String sql = "INSERT INTO livrator(nume) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, nume);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private static int insertUtilizator(Connection conn, String username, String email, int puncte) throws SQLException {
        String sql = "INSERT INTO utilizator(username, email, puncte) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setInt(3, puncte);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    private static void insertDomiciliu(Connection conn, int idLocatie) throws SQLException {
        String sql = "INSERT INTO domiciliu(id_locatie) VALUES(?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLocatie);
            pstmt.executeUpdate();
        }
    }

    private static void insertLocal(Connection conn, int idLocatie, String nume, int idMeniu) throws SQLException {
        String sql = "INSERT INTO local(id_locatie, nume, id_meniu) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLocatie);
            pstmt.setString(2, nume);
            pstmt.setInt(3, idMeniu);
            pstmt.executeUpdate();
        }
    }

    private static void insertMeniuArticol(Connection conn, int idMeniu, String cheieMap, int idArticol) throws SQLException {
        String sql = "INSERT INTO meniu_articole(id_meniu, cheie_map, id_articol) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idMeniu);
            pstmt.setString(2, cheieMap);
            pstmt.setInt(3, idArticol);
            pstmt.executeUpdate();
        }
    }

    private static void insertComanda(Connection conn, int id, int idUtilizator, int idLivrator, int idPlecare, int idDestinatie, String status, long timestamp) throws SQLException {
        String sql = "INSERT INTO comanda(id, id_utilizator, id_livrator, id_plecare, id_destinatie, status, timestamp) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, idUtilizator);
            pstmt.setInt(3, idLivrator);
            pstmt.setInt(4, idPlecare);
            pstmt.setInt(5, idDestinatie);
            pstmt.setString(6, status);
            pstmt.setLong(7, timestamp);
            pstmt.executeUpdate();
        }
    }

    private static void insertComandaArticol(Connection conn, int idComanda, int idArticol) throws SQLException {
        String sql = "INSERT INTO comanda_articole(id_comanda, id_articol) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idComanda);
            pstmt.setInt(2, idArticol);
            pstmt.executeUpdate();
        }
    }
}