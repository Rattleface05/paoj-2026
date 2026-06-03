package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Utilizator;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticolRepository implements Repository<Articol, Long> {

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Articol mapRow(ResultSet rs) throws SQLException {
        Articol a = new Articol();
        a.setId(rs.getLong("id"));
        a.setNume(rs.getString("nume"));
        a.setPret(rs.getDouble("pret"));
        a.setVegan(rs.getInt("vegan") == 1);
        return a;
    }

    @Override
    public void save(Articol articol) throws SQLException{
        String sql = "INSERT INTO articol (nume, pret, vegan) VALUES (?, ?, ?)";
        try (PreparedStatement ps = getConn().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, articol.getNume());
            ps.setDouble(2, articol.getPret());
            ps.setInt(3, articol.isVeganProduct() ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) articol.setId(keys.getLong(1));
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }


    @Override
    public Optional<Articol> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM articol WHERE id = ?";
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
    public List<Articol> findAll() throws SQLException {
        String sql = "SELECT * FROM articol ORDER BY id";
        List<Articol> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Articol articol) throws SQLException {
        String sql = "UPDATE articol SET nume = ?, pret = ?, vegan = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setString(1, articol.getNume());
            ps.setDouble(2, articol.getPret());
            ps.setInt(3, articol.isVeganProduct() ? 1 : 0);
            ps.setLong(4, articol.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM articol WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }




}
