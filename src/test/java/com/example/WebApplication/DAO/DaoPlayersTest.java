import com.example.WebApplication.DAO.DaoPlayers;
import com.example.WebApplication.Models.Players;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.*;
import java.util.List;

import static com.example.WebApplication.DAO.DaoPlayersTestData.*;
import static org.junit.jupiter.api.Assertions.*;

   //@TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Testcontainers
   class DaoPlayersTest {
       private static final String MYSQL_LATEST_IMAGE = "mysql:latest";
       private static Connection connection;
       private static DaoPlayers dao;

       @Container
       private static JdbcDatabaseContainer mysql = new MySQLContainer<>(MYSQL_LATEST_IMAGE).withInitScript("initdb.sql");


       //create connection to container and create DAO object
       @BeforeAll
       public static void setup() throws SQLException {
           mysql.start();
           connection = DriverManager.getConnection(
                   mysql.getJdbcUrl(),
                   mysql.getUsername(),
                   mysql.getPassword()
           );
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
           assertEquals(com.example.WebApplication.DAO.DaoPlayersTestData.players, players);
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

       @AfterAll
       static void destroy() {
        mysql.stop();
        }
   }