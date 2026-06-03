package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Meniu;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeniuRepository implements Repository<Meniu, Long>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Meniu mapRow(ResultSet rs) throws SQLException {
        Meniu m = new Meniu();
        m.setId(rs.getLong("id"));
        return m;
    }

    @Override
    public void save(Meniu meniu) throws SQLException {
        String sql = "INSERT INTO meniu DEFAULT VALUES";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) meniu.setId(keys.getLong(1));
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Meniu> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM meniu WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Meniu m = mapRow(rs);
                    loadArticole(m);
                    return Optional.of(m);
                }
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Meniu> findAll() throws SQLException {
        String sql = "SELECT * FROM meniu ORDER BY id";
        List<Meniu> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Meniu m = mapRow(rs);
                loadArticole(m);
                list.add(m);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Meniu meniu) throws SQLException {
        // Delete existing articole for this menu
        String deleteSql = "DELETE FROM meniu_articole WHERE id_meniu = ?";
        try (PreparedStatement ps = getConn().prepareStatement(deleteSql)) {
            ps.setLong(1, meniu.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }

        // Insert updated articole
        String insertSql = "INSERT INTO meniu_articole (id_meniu, cheie_map, id_articol) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(insertSql)) {
            for (String key : meniu.getArticole().keySet()) {
                ps.setLong(1, meniu.getId());
                ps.setString(2, key);
                ps.setLong(3, meniu.getArticole().get(key).getId());
                ps.executeUpdate();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM meniu WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    private void loadArticole(Meniu meniu) throws SQLException, IOException {
        String sql = "SELECT cheie_map, id_articol FROM meniu_articole WHERE id_meniu = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, meniu.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String key = rs.getString("cheie_map");
                    Long articolId = rs.getLong("id_articol");
                    // Get the articol from database
                    ArticolRepository ar = new ArticolRepository();
                    Optional<Articol> articol = ar.findById(articolId);
                    if (articol.isPresent()) {
                        meniu.getArticole().put(key, articol.get());
                    }
                }
            }
        }
    }
}
