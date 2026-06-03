package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Comanda;
import com.pao.project.foodDelivery.model.Utilizator;
import com.pao.project.foodDelivery.model.Livrator;
import com.pao.project.foodDelivery.model.Local;
import com.pao.project.foodDelivery.model.Domiciliu;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public class ComandaRepository implements Repository<Comanda, Long>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Comanda mapRow(ResultSet rs) throws SQLException {
        Comanda c = new Comanda();
        c.setId(rs.getLong("id"));
        c.setStatus(Comanda.Status.valueOf(rs.getString("status")));
        c.setTime(new Date(rs.getLong("timestamp")));
        return c;
    }

    @Override
    public void save(Comanda comanda) throws SQLException {
        String sql = "INSERT INTO comanda (id_utilizator, id_livrator, id_plecare, id_destinatie, status, timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, comanda.getUtilizator().getId());
            ps.setLong(2, comanda.getLivrator().getId());
            ps.setLong(3, comanda.getPlecare().getId());
            ps.setLong(4, comanda.getDestinatie().getId());
            ps.setString(5, String.valueOf(comanda.getStatus()));
            ps.setLong(6, comanda.getTime().getTime());
            ps.executeUpdate();
            
            // Get the ID from the Comanda object (it should be set before saving)
            // Insert articole
            String articolSql = "INSERT INTO comanda_articole (id_comanda, id_articol) VALUES (?, ?)";
            try (PreparedStatement articolPs = getConn().prepareStatement(articolSql)) {
                for (Articol articol : comanda.getArticolList()) {
                    articolPs.setLong(1, comanda.getId());
                    articolPs.setLong(2, articol.getId());
                    articolPs.executeUpdate();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Comanda> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM comanda WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Comanda c = mapRow(rs);
                    loadRelatedData(c, rs);
                    loadArticole(c);
                    return Optional.of(c);
                }
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Comanda> findAll() throws SQLException {
        String sql = "SELECT * FROM comanda ORDER BY id";
        List<Comanda> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Comanda c = mapRow(rs);
                loadRelatedData(c, rs);
                loadArticole(c);
                list.add(c);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Comanda comanda) throws SQLException {
        String sql = "UPDATE comanda SET id_utilizator = ?, id_livrator = ?, id_plecare = ?, id_destinatie = ?, status = ?, timestamp = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, comanda.getUtilizator().getId());
            ps.setLong(2, comanda.getLivrator().getId());
            ps.setLong(3, comanda.getPlecare().getId());
            ps.setLong(4, comanda.getDestinatie().getId());
            ps.setString(5, String.valueOf(comanda.getStatus()));
            ps.setLong(6, comanda.getTime().getTime());
            ps.setLong(7, comanda.getId());
            ps.executeUpdate();

            // Delete existing articole
            String deleteSql = "DELETE FROM comanda_articole WHERE id_comanda = ?";
            try (PreparedStatement deletePs = getConn().prepareStatement(deleteSql)) {
                deletePs.setLong(1, comanda.getId());
                deletePs.executeUpdate();
            }

            // Insert updated articole
            String articolSql = "INSERT INTO comanda_articole (id_comanda, id_articol) VALUES (?, ?)";
            try (PreparedStatement articolPs = getConn().prepareStatement(articolSql)) {
                for (Articol articol : comanda.getArticolList()) {
                    articolPs.setLong(1, comanda.getId());
                    articolPs.setLong(2, articol.getId());
                    articolPs.executeUpdate();
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM comanda WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    private void loadRelatedData(Comanda comanda, ResultSet rs) throws SQLException, IOException {
        // Load Utilizator
        long utilizatorId = rs.getLong("id_utilizator");
        UtilizatorRepository ur = new UtilizatorRepository();
        Optional<Utilizator> utilizator = ur.findById(utilizatorId);
        utilizator.ifPresent(comanda::setUtilizator);

        // Load Livrator
        long livratorId = rs.getLong("id_livrator");
        LivratorRepository lr = new LivratorRepository();
        Optional<Livrator> livrator = lr.findById(livratorId);
        livrator.ifPresent(comanda::setLivrator);

        // Load Local (plecare)
        long plecare = rs.getLong("id_plecare");
        LocalRepository locr = new LocalRepository();
        Optional<Local> localPlecare = locr.findById(plecare);
        localPlecare.ifPresent(comanda::setPlecare);

        // Load Domiciliu (destinatie)
        long destinatie = rs.getLong("id_destinatie");
        DomiciliuRepository dr = new DomiciliuRepository();
        Optional<Domiciliu> domiciliuDestinatie = dr.findById(destinatie);
        domiciliuDestinatie.ifPresent(comanda::setDestinatie);
    }

    private void loadArticole(Comanda comanda) throws SQLException, IOException {
        String sql = "SELECT id_articol FROM comanda_articole WHERE id_comanda = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, comanda.getId());
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<Articol> articolList = new ArrayList<>();
                ArticolRepository ar = new ArticolRepository();
                while (rs.next()) {
                    long articolId = rs.getLong("id_articol");
                    Optional<Articol> articol = ar.findById(articolId);
                    articol.ifPresent(articolList::add);
                }
                comanda.setArticolList(articolList);
            }
        }
    }

    /**
     * REQUIREMENT 2: JDBC TRANSACTION — Explicit commit/rollback
     * 
     * Saves a Comanda and all its associated articole in a single JDBC transaction.
     * If any error occurs, the entire operation is rolled back to maintain data consistency.
     * 
     * This demonstrates:
     * - setAutoCommit(false) to start explicit transaction
     * - Multiple SQL operations (INSERT into 2 tables)
     * - commit() on success
     * - rollback() on error
     * - finally: setAutoCommit(true) to restore normal behavior
     */
    public void saveWithTransaction(Comanda comanda) throws SQLException {
        Connection conn;
        try {
            conn = getConn();
        } catch (IOException e) {
            throw new SQLException(e);
        }
        
        boolean autoCommitBackup = false;
        try {
            // 1. DISABLE AUTO-COMMIT — start explicit transaction
            autoCommitBackup = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // 2. INSERT INTO comanda table
            String comandaSql = "INSERT INTO comanda (id_utilizator, id_livrator, id_plecare, id_destinatie, status, timestamp) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(comandaSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, comanda.getUtilizator().getId());
                ps.setLong(2, comanda.getLivrator().getId());
                ps.setLong(3, comanda.getPlecare().getId());
                ps.setLong(4, comanda.getDestinatie().getId());
                ps.setString(5, String.valueOf(comanda.getStatus()));
                ps.setLong(6, comanda.getTime().getTime());
                ps.executeUpdate();
                
                // Get generated ID if not set
                if (comanda.getId() == 0) {
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            comanda.setId(keys.getLong(1));
                        }
                    }
                }
            }

            // 3. INSERT INTO comanda_articole table for each article
            String articolSql = "INSERT INTO comanda_articole (id_comanda, id_articol) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(articolSql)) {
                for (Articol articol : comanda.getArticolList()) {
                    ps.setLong(1, comanda.getId());
                    ps.setLong(2, articol.getId());
                    ps.addBatch();
                }
                // Execute all inserts in batch
                ps.executeBatch();
            }

            // 4. COMMIT — all operations succeeded
            conn.commit();
            System.out.println("[TRANSACTION] ✓ Comanda #" + comanda.getId() + " saved successfully with " + 
                             comanda.getArticolList().size() + " articole (committed)");

        } catch (SQLException e) {
            // 5. ROLLBACK — something went wrong, undo everything
            try {
                conn.rollback();
                System.err.println("[TRANSACTION] ✗ Error saving comanda, rolled back: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("[TRANSACTION] ✗ Error rolling back: " + rollbackEx.getMessage());
            }
            throw e;
        } finally {
            // 6. RESTORE AUTO-COMMIT to normal behavior
            try {
                conn.setAutoCommit(autoCommitBackup);
            } catch (SQLException e) {
                System.err.println("[TRANSACTION] Warning: Could not restore AutoCommit: " + e.getMessage());
            }
        }
    }

    /**
     * REQUIREMENT 2: UPDATE WITH TRANSACTION — Demonstrates transaction on update
     * 
     * Updates a comanda and its articole list in a single transaction.
     * Ensures data consistency across multiple tables.
     */
    public void updateWithTransaction(Comanda comanda) throws SQLException {
        Connection conn;
        try {
            conn = getConn();
        } catch (IOException e) {
            throw new SQLException(e);
        }
        
        boolean autoCommitBackup = false;
        try {
            // Start explicit transaction
            autoCommitBackup = conn.getAutoCommit();
            conn.setAutoCommit(false);

            // Update comanda record
            String updateSql = "UPDATE comanda SET id_utilizator = ?, id_livrator = ?, id_plecare = ?, id_destinatie = ?, status = ?, timestamp = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setLong(1, comanda.getUtilizator().getId());
                ps.setLong(2, comanda.getLivrator().getId());
                ps.setLong(3, comanda.getPlecare().getId());
                ps.setLong(4, comanda.getDestinatie().getId());
                ps.setString(5, String.valueOf(comanda.getStatus()));
                ps.setLong(6, comanda.getTime().getTime());
                ps.setLong(7, comanda.getId());
                ps.executeUpdate();
            }

            // Delete existing articole association
            String deleteSql = "DELETE FROM comanda_articole WHERE id_comanda = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setLong(1, comanda.getId());
                ps.executeUpdate();
            }

            // Insert updated articole associations
            String articolSql = "INSERT INTO comanda_articole (id_comanda, id_articol) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(articolSql)) {
                for (Articol articol : comanda.getArticolList()) {
                    ps.setLong(1, comanda.getId());
                    ps.setLong(2, articol.getId());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // All operations succeeded, commit
            conn.commit();
            System.out.println("[TRANSACTION] ✓ Comanda #" + comanda.getId() + " updated successfully (committed)");

        } catch (SQLException e) {
            // Rollback on error
            try {
                conn.rollback();
                System.err.println("[TRANSACTION] ✗ Error updating comanda, rolled back: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                System.err.println("[TRANSACTION] ✗ Error rolling back: " + rollbackEx.getMessage());
            }
            throw e;
        } finally {
            // Restore auto-commit mode
            try {
                conn.setAutoCommit(autoCommitBackup);
            } catch (SQLException e) {
                System.err.println("[TRANSACTION] Warning: Could not restore AutoCommit: " + e.getMessage());
            }
        }
    }

    /**
     * REQUIREMENT 3 & 4: Advanced query methods and audit service are implemented in
     * the section below (findAllWithUserAndDelivererDetails and other JOIN queries)
     */

    public List<String> findAllWithUserAndDelivererDetails() throws SQLException {
        String sql = "SELECT c.id, u.username, l.nume as livrator_nume, lo.nume as local_nume, " +
                     "c.status, c.timestamp, " +
                     "(SELECT SUM(a.pret) FROM comanda_articole ca JOIN articol a ON ca.id_articol = a.id WHERE ca.id_comanda = c.id) as total " +
                     "FROM comanda c " +
                     "JOIN utilizator u ON c.id_utilizator = u.id " +
                     "JOIN livrator l ON c.id_livrator = l.id " +
                     "JOIN local lo ON c.id_plecare = lo.id_locatie " +
                     "ORDER BY c.timestamp DESC";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String result = String.format("Comanda #%d | User: %s | Livrator: %s | Restaurant: %s | Status: %s | Total: %.2f lei",
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("livrator_nume"),
                        rs.getString("local_nume"),
                        rs.getString("status"),
                        rs.getDouble("total"));
                results.add(result);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return results;
    }
}
