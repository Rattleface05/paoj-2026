package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Meniu;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MeniuRepository implements Repository<Meniu, Long>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }
}
