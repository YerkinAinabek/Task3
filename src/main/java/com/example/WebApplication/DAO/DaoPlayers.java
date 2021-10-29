package com.example.WebApplication.DAO;

import com.example.WebApplication.DataSourceFactory;
import com.example.WebApplication.Models.Players;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoPlayers implements PlayersDao {

    private DaoPlayers () {
    }

    private static class SingletonHelper {
        private static final DaoPlayers INSTANCE = new DaoPlayers();
    }


    public static DaoPlayers getInstance() {
        return SingletonHelper.INSTANCE;
    }



    @Override
    public Optional<Players> find(String id) throws SQLException {
        String sql = "SELECT players_id, nickname, player_lvl, biography FROM players WHERE player_id = ?";
        int id_player = 0, player_lvl = 0;
        String nickname = "", biography = "";
        Connection conn = DataSourceFactory.getConnection();

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            id_player = resultSet.getInt("player_id");
            nickname = resultSet.getString("nickname");
            player_lvl = resultSet.getInt("player_lvl");
            biography = resultSet.getString("biography");
        }
        return Optional.of(new Players(id_player, nickname, player_lvl, biography));
    }

    @Override
    public List<Players> findAll() throws SQLException {
        List<Players> listPlayers = new ArrayList<>();
        String sql = "SELECT player_id, nickname, player_lvl, biography FROM players";

        Connection conn = DataSourceFactory.getConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int player_id = resultSet.getInt("player_id");
            String nickname = resultSet.getString("nickname");
            int player_lvl = resultSet.getInt("player_lvl");
            String biography = resultSet.getString("biography");

            Players players = new Players(player_id, nickname, player_lvl, biography);
            listPlayers.add(players);
        }
        return listPlayers;
    }

    @Override
    public boolean save(Players players) throws SQLException {

        String sql = "INSERT into players (nickname, player_lvl, biography) VALUES (?, ?, ?)";
        boolean rowInserted = false;
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, players.getNickname());
        statement.setInt(2, players.getPlayer_lvl());
        statement.setString(3, players.getBiography());
        rowInserted = statement.executeUpdate() > 0 ;

        return rowInserted;
    }

    @Override
    public boolean update(Players players) throws SQLException {
        String sql = "UPDATE players SET nickname = ?, player_lvl = ?, biography = ?";
        sql += " WHERE player_id = ?";
        boolean rowUpdated = false;
        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, players.getNickname());
        statement.setInt(2, players.getPlayer_lvl());
        statement.setString(3, players.getBiography());
        statement.setInt(4, players.getPlayer_id());
        rowUpdated = statement.executeUpdate() > 0;

        return rowUpdated;
    }

    @Override
    public boolean delete(Players players) throws SQLException {

        String sql = "DELETE FROM players where player_id = ?";
        boolean rowDeleted = false;

        Connection conn = DataSourceFactory.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, players.getPlayer_id());
        rowDeleted = statement.executeUpdate() > 0;

        return rowDeleted;
    }





}
