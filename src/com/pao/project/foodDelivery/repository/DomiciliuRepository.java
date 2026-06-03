package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Domiciliu;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DomiciliuRepository implements Repository<Domiciliu, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Domiciliu mapRow(ResultSet rs) throws SQLException {
        Domiciliu d = new Domiciliu();
        d.setId(rs.getLong("id"));
        d.setStrada(rs.getString("strada"));
        d.setNumar(rs.getInt("numar"));
        d.setScara(rs.getInt("scara"));
        d.setApartament(rs.getInt("apartament"));
        return d;
    }

    @Override
    public void save(Domiciliu domiciliu) throws SQLException {
        String sql = "INSERT INTO locatie (strada, numar, scara, apartament) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, domiciliu.getStrada());
            ps.setInt(2, domiciliu.getNumar());
            ps.setInt(3, domiciliu.getScara());
            ps.setInt(4, domiciliu.getApartament());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long id = keys.getLong(1);
                    domiciliu.setId(id);
                    // Insert into domiciliu table
                    String domiciliuSql = "INSERT INTO domiciliu (id_locatie) VALUES (?)";
                    try (PreparedStatement domiciliuPs = getConn().prepareStatement(domiciliuSql)) {
                        domiciliuPs.setLong(1, id);
                        domiciliuPs.executeUpdate();
                    }
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Domiciliu> findById(Long id) throws SQLException {
        String sql = "SELECT l.* FROM locatie l JOIN domiciliu d ON l.id = d.id_locatie WHERE l.id = ?";
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
    public List<Domiciliu> findAll() throws SQLException {
        String sql = "SELECT l.* FROM locatie l JOIN domiciliu d ON l.id = d.id_locatie ORDER BY l.id";
        List<Domiciliu> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Domiciliu domiciliu) throws SQLException {
        String sql = "UPDATE locatie SET strada = ?, numar = ?, scara = ?, apartament = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, domiciliu.getStrada());
            ps.setInt(2, domiciliu.getNumar());
            ps.setInt(3, domiciliu.getScara());
            ps.setInt(4, domiciliu.getApartament());
            ps.setLong(5, domiciliu.getId());
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
}
