package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Local;
import com.pao.project.foodDelivery.model.Meniu;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocalRepository implements Repository<Local, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Local mapRow(ResultSet rs) throws SQLException {
        Local l = new Local();
        l.setId(rs.getLong("id"));
        l.setStrada(rs.getString("strada"));
        l.setNumar(rs.getInt("numar"));
        l.setScara(rs.getInt("scara"));
        l.setApartament(rs.getInt("apartament"));
        l.setNume(rs.getString("nume"));
        return l;
    }

    @Override
    public void save(Local local) throws SQLException {
        String sql = "INSERT INTO locatie (strada, numar, scara, apartament) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, local.getStrada());
            ps.setInt(2, local.getNumar());
            ps.setInt(3, local.getScara());
            ps.setInt(4, local.getApartament());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    local.setId(id);

                    // Save Meniu if it exists
                    long meniuId = local.getMeniu().getId();
                    if (meniuId == 0) {
                        MeniuRepository mr = new MeniuRepository();
                        mr.save(local.getMeniu());
                        meniuId = local.getMeniu().getId();
                    }

                    // Insert into local table
                    String localSql = "INSERT INTO local (id_locatie, nume, id_meniu) VALUES (?, ?, ?)";
                    try (PreparedStatement localPs = getConn().prepareStatement(localSql)) {
                        localPs.setLong(1, id);
                        localPs.setString(2, local.getNume());
                        localPs.setLong(3, meniuId);
                        localPs.executeUpdate();
                    }
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Local> findById(Long id) throws SQLException {
        String sql = "SELECT l.*, lo.nume, lo.id_meniu FROM locatie l JOIN local lo ON l.id = lo.id_locatie WHERE l.id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Local local = mapRow(rs);
                    long meniuId = rs.getLong("id_meniu");
                    MeniuRepository mr = new MeniuRepository();
                    Optional<Meniu> meniu = mr.findById(meniuId);
                    meniu.ifPresent(local::setMeniu);
                    return Optional.of(local);
                }
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Local> findAll() throws SQLException {
        String sql = "SELECT l.*, lo.nume, lo.id_meniu FROM locatie l JOIN local lo ON l.id = lo.id_locatie ORDER BY l.id";
        List<Local> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Local local = mapRow(rs);
                long meniuId = rs.getLong("id_meniu");
                MeniuRepository mr = new MeniuRepository();
                Optional<Meniu> meniu = mr.findById(meniuId);
                meniu.ifPresent(local::setMeniu);
                list.add(local);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Local local) throws SQLException {
        String sql = "UPDATE locatie SET strada = ?, numar = ?, scara = ?, apartament = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, local.getStrada());
            ps.setInt(2, local.getNumar());
            ps.setInt(3, local.getScara());
            ps.setInt(4, local.getApartament());
            ps.setLong(5, local.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }

        String localSql = "UPDATE local SET nume = ?, id_meniu = ? WHERE id_locatie = ?";
        try (PreparedStatement ps = getConn().prepareStatement(localSql)) {
            ps.setString(1, local.getNume());
            ps.setLong(2, local.getMeniu().getId());
            ps.setLong(3, local.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM locatie WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    /**
     * JOIN Query 3: Find all restaurants with their menu item count and vegan options
     * Combines: local JOIN meniu JOIN meniu_articole JOIN articol
     */
    public List<String> findAllWithMenuStats() throws SQLException {
        String sql = "SELECT lo.id_locatie, lo.nume, " +
                     "COUNT(ma.id_articol) as total_items, " +
                     "SUM(CASE WHEN a.vegan = 1 THEN 1 ELSE 0 END) as vegan_items " +
                     "FROM local lo " +
                     "JOIN meniu m ON lo.id_meniu = m.id " +
                     "LEFT JOIN meniu_articole ma ON m.id = ma.id_meniu " +
                     "LEFT JOIN articol a ON ma.id_articol = a.id " +
                     "GROUP BY lo.id_locatie, lo.nume " +
                     "ORDER BY total_items DESC";
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String result = String.format("Restaurant: %s | Menu Items: %d | Vegan Options: %d",
                        rs.getString("nume"),
                        rs.getLong("total_items"),
                        rs.getLong("vegan_items"));
                results.add(result);
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return results;
    }
}
