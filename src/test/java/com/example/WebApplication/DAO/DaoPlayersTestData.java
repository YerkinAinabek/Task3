
package com.example.WebApplication.DAO;

import com.example.WebApplication.Models.Players;

import java.util.List;

public class DaoPlayersTestData {
    public static final Integer PLAYER_1_ID = 1;
    public static final Integer PLAYER_2_ID = 2;
    public static final Integer PLAYER_3_ID = 3;
    public static final Integer NOT_FOUND_ID = 1000;
    public static final Players player1 = new Players(PLAYER_1_ID, "player1", 1, "poor");
    public static final Players player2 = new Players(PLAYER_2_ID, "player2", 2, "average");
    public static final Players player3 = new Players(PLAYER_3_ID, "player3", 3, "rich" );
    public static final List<Players> players = List.of(player1, player2, player3);

    public static Players getNew() {
        return new Players(4,"NewPlayer", 5, "new biography");
    }

    public static Players getUpdated() {
        return new Players(1,"Updated", 1, "was poor");
    }
}


