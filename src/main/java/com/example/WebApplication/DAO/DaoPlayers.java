package com.example.WebApplication.DAO;

import com.example.WebApplication.Models.Players;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is responsible for interaction with database.
 */
public class DaoPlayers implements PlayersDao {

    private final Connection connection;

    private static final String INSERT_USERS_SQL = "INSERT INTO players" + "  (nickname, player_lvl, biography) VALUES " +
            " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "SELECT player_id,nickname,player_lvl,biography FROM players where player_id =?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM players;";
    private static final String DELETE_USERS_SQL = "DELETE FROM players WHERE player_id = ?;";
    private static final String UPDATE_USERS_SQL = "update players set nickname = ?,player_lvl= ?, biography =? where player_id = ?;";

    /**
     * Simple DAO constructor
     * @param connection
     */
    public DaoPlayers(Connection connection) {
        this.connection = connection;
    }



    /**
     * Method for user selection query initialization
     * @param id - user's id
     * @return player object using id
     * @throws SQLException
     */
    @Override
    public Players find(int id) throws SQLException {

        Players player = null;
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

    /**
     * This method shows list of all players from the database
     * @return listPlayers - all selected players from the DB
     * @throws NullPointerException if there is now connection to the DB
     */
    @Override
    public List<Players> findAll() throws NullPointerException {

        List<Players> listPlayers = new ArrayList<>();
        try (
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

    /**
     * Inserts new player into the DB
     * @param player - entity which is being inserted
     * @throws SQLException
     */
    @Override
    public void insert(Players player) throws SQLException {

        System.out.println(INSERT_USERS_SQL);
        try (
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

    /**
     * Method used to edit players from the list and to update them eventually
     * @param player - player from the DB to be updated
     * @return rowUpdated - returns updated DB row
     * @throws SQLException
     */
    @Override
    public boolean update(Players player) throws SQLException {

        boolean rowUpdated;
        PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);
        statement.setString(1, player.getNickname());
        statement.setInt(2, player.getLvl());
        statement.setString(3, player.getBiography());
        statement.setInt(4, player.getId());

        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }

    /**
     * Methods is used to delete row with player's info from the DB
     * @param id - id of the player that has to be deleted from the DB
     * @return
     * @throws SQLException
     */
    @Override
    public boolean delete(int id) throws SQLException {

        boolean rowDeleted = false;


        PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);
        statement.setInt(1, id);
        rowDeleted = statement.executeUpdate() > 0;

        return rowDeleted;
    }
}
