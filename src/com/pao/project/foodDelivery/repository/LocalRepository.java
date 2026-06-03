package com.pao.project.foodDelivery.repository;

import com.pao.project.foodDelivery.model.Articol;
import com.pao.project.foodDelivery.model.Local;
import com.pao.project.foodDelivery.util.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LocalRepository implements Repository<Local, Long>{

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }
}
