package com.example.WebApplication.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    T find(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    void insert (T o) throws SQLException;

    boolean update (T o) throws SQLException;

    boolean delete (int o) throws SQLException;

}
