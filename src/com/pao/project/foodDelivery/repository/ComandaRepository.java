package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Comanda;
import com.pao.project.foodDelivery.model.Utilizator;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComandaRepository implements Repository<Comanda, Long>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    
}
