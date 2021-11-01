package com.example.WebApplication.DAO;

import com.example.WebApplication.Models.Players;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoPlayers implements PlayersDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/playersdb?useSSL=false&useUnicode=true&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "23011999";
    public Connection connection;

    private static final String INSERT_USERS_SQL = "INSERT INTO players" + "  (nickname, player_lvl, biography) VALUES " +
            " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "SELECT player_id,nickname,player_lvl,biography FROM players where player_id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM players;";
    private static final String DELETE_USERS_SQL = "DELETE FROM players WHERE player_id = ?;";
    private static final String UPDATE_USERS_SQL = "update players set nickname = ?,player_lvl= ?, biography =? where player_id = ?;";

    public DaoPlayers() {}

    public DaoPlayers(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Players find(int id) throws SQLException {

        Players player = null;
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
        preparedStatement.setInt(1, id);
        System.out.println(preparedStatement);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            String nickname = rs.getString("nickname");
            int lvl = rs.getInt("player_lvl");
            String biography = rs.getString("biography");
            player = new Players(id, nickname, lvl, biography);
        }
        return player;
    }

    @Override
    public List<Players> findAll() throws NullPointerException {

        List<Players> listPlayers = new ArrayList<>();
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("player_id");
                String nickname = rs.getString("nickname");
                int lvl = rs.getInt("player_lvl");
                String biography = rs.getString("biography");
                //Players players = new Players(id, nickname, lvl, biography);
                listPlayers.add(new Players(id,nickname,lvl,biography));
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    return listPlayers;
    }

    @Override
    public void insert(Players player) throws SQLException {

        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);) {
            preparedStatement.setString(1, player.getNickname());
            preparedStatement.setInt(2, player.getLvl());
            preparedStatement.setString(3, player.getBiography());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Players player) throws SQLException {

        boolean rowUpdated;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);
        statement.setString(1, player.getNickname());
        statement.setInt(2, player.getLvl());
        statement.setString(3, player.getBiography());
        statement.setInt(4, player.getId());

        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }

    @Override
    public boolean delete(int id) throws SQLException {

        boolean rowDeleted = false;

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);
        statement.setInt(1, id);
        rowDeleted = statement.executeUpdate() > 0;

        return rowDeleted;
    }





}
