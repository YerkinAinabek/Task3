package com.example.WebApplication.DAO;

import com.example.WebApplication.DAO.DaoPlayers;
import com.example.WebApplication.Models.Players;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;
import static com.example.WebApplication.DAO.DaoPlayersTestData.*;

import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

   //@TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Testcontainers
    class DaoPlayersTest {

        private static String jdbcURL = "jdbc:mysql://localhost:3306/playersdb?useSSL=false&useUnicode=true&serverTimezone=UTC";
        private static String jdbcUsername = "root";
        private static String jdbcPassword = "23011999";
        private static Connection connection;
        private static PlayersDao dao;

       @BeforeAll
       public static void setup() throws SQLException {
           connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
           dao = new DaoPlayers(connection);
       }


       //before each test reset database to the initial state
        @BeforeEach
        public void refreshData() throws SQLException {
            Statement statement = connection.createStatement();
            statement.addBatch("DELETE FROM players;");
            statement.addBatch("ALTER TABLE players AUTO_INCREMENT = 1;");
            statement.addBatch("INSERT INTO players (player_id, nickname, player_lvl, biography) VALUES (1, 'player1', 1, 'poor' ), (2, 'player2', 2, 'average' ),(3, 'player3', 3, 'rich' );");
            statement.executeBatch();
        }


        @Test
        void findAll() throws SQLException {

           List<Players> players = dao.findAll();
           assertEquals(DaoPlayersTestData.players, players);
        }

        @Test
        void insert() throws SQLException {

            Players newPlayer = getNew();
            dao.insert(newPlayer);
            int id = newPlayer.getId();
            newPlayer.setId(id);
            assertEquals(newPlayer, dao.find(id));
        }

        @Test
        void update() throws SQLException {

            Players updatedPlayer = getUpdated();
            dao.update(updatedPlayer);
            assertEquals(updatedPlayer, dao.find(PLAYER_1_ID));
        }

        @Test
        void delete() throws SQLException {

            assertTrue(dao.delete(PLAYER_1_ID));
            assertNull(dao.find(PLAYER_1_ID));
        }

        @Test
        void deleteNotFound() throws SQLException {

            assertFalse(dao.delete(NOT_FOUND_ID));
        }

        @Test
        void get() throws SQLException {

            dao.find(PLAYER_1_ID);
            assertEquals(player1, dao.find(PLAYER_1_ID));
        }
    }