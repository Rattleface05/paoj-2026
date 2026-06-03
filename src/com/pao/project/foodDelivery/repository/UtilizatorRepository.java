package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Utilizator;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilizatorRepository implements Repository<Utilizator, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Utilizator mapRow(ResultSet rs) throws SQLException {
        Utilizator a = new Utilizator();
        a.setId(rs.getLong("id"));
        a.setEmail(rs.getString("email"));
        a.setPuncte(rs.getInt("puncte"));
        a.setUsername(rs.getString("username"));
        return a;
    }
}
