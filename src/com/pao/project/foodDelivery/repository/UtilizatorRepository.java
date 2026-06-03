package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Utilizator;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilizatorRepository implements Repository<Utilizator, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Utilizator mapRow(ResultSet rs) throws SQLException {
        Utilizator u = new Utilizator();
        u.setId(rs.getLong("id"));
        u.setEmail(rs.getString("email"));
        u.setPuncte(rs.getInt("puncte"));
        u.setUsername(rs.getString("username"));
        return u;
    }

    @Override
    public void save(Utilizator utilizator) throws SQLException {
        String sql = "INSERT INTO utilizator (username, email, puncte) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, utilizator.getUsername());
            ps.setString(2, utilizator.getEmail());
            ps.setInt(3, utilizator.getPuncte());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) utilizator.setId(keys.getLong(1));
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Utilizator> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM utilizator WHERE id = ?";
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
    public List<Utilizator> findAll() throws SQLException {
        String sql = "SELECT * FROM utilizator ORDER BY id";
        List<Utilizator> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Utilizator utilizator) throws SQLException {
        String sql = "UPDATE utilizator SET username = ?, email = ?, puncte = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, utilizator.getUsername());
            ps.setString(2, utilizator.getEmail());
            ps.setInt(3, utilizator.getPuncte());
            ps.setLong(4, utilizator.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM utilizator WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    /**
     * JOIN Query 2: Find all users with their order count and total spent
     * Combines: utilizator LEFT JOIN comanda
     */
    public List<String> findAllWithOrderStats() throws SQLException {
        String sql = "SELECT u.id, u.username, u.email, u.puncte, " +
                     "COUNT(c.id) as order_count, " +
                     "COALESCE(SUM(c.id), 0) as total_orders " +
                     "FROM utilizator u " +
                     "LEFT JOIN comanda c ON u.id = c.id_utilizator " +
                     "GROUP BY u.id, u.username, u.email, u.puncte " +
                     "ORDER BY order_count DESC";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String result = String.format("User: %s | Email: %s | Puncte: %d | Comenzi: %d",
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("puncte"),
                        rs.getLong("order_count"));
                results.add(result);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return results;
    }
}
