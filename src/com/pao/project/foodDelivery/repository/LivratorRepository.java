package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Livrator;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LivratorRepository implements Repository<Livrator, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Livrator mapRow(ResultSet rs) throws SQLException {
        Livrator l = new Livrator();
        l.setId(rs.getLong("id"));
        l.setNume(rs.getString("nume"));
        return l;
    }

    @Override
    public void save(Livrator livrator) throws SQLException {
        String sql = "INSERT INTO livrator (nume) VALUES (?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, livrator.getNume());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) livrator.setId(keys.getLong(1));
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Livrator> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM livrator WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Livrator> findAll() throws SQLException {
        String sql = "SELECT * FROM livrator ORDER BY id";
        List<Livrator> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Livrator livrator) throws SQLException {
        String sql = "UPDATE livrator SET nume = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, livrator.getNume());
            ps.setLong(2, livrator.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM livrator WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    /**
     * JOIN Query 4: Find all deliverers with their delivery count and total earnings
     * Combines: livrator LEFT JOIN comanda
     */
    public List<String> findAllWithEarningStats() throws SQLException {
        String sql = "SELECT l.id, l.nume, " +
                     "COUNT(c.id) as delivery_count, " +
                     "COALESCE(SUM((SELECT SUM(a.pret) FROM comanda_articole ca " +
                     "                JOIN articol a ON ca.id_articol = a.id " +
                     "                WHERE ca.id_comanda = c.id)), 0) as total_earnings " +
                     "FROM livrator l " +
                     "LEFT JOIN comanda c ON l.id = c.id_livrator " +
                     "GROUP BY l.id, l.nume " +
                     "ORDER BY total_earnings DESC";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String result = String.format("Livrator: %s | Livrări: %d | Venituri: %.2f lei",
                        rs.getString("nume"),
                        rs.getLong("delivery_count"),
                        rs.getDouble("total_earnings"));
                results.add(result);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return results;
    }
}
