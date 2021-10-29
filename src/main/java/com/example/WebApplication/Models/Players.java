package com.example.WebApplication.Models;

public class Players {

    protected int player_id;
    protected String nickname;
    protected String biography;
    protected int player_lvl;

    public Players() {
    }

    public Players(int player_id) {
        this.player_id=player_id;
    }

    public Players(int player_id, String nickname, int player_lvl, String biography) {
        this(nickname, player_lvl, biography);
        this.player_id = player_id;
    }

    public Players(String nickname, int player_lvl, String biography) {
        this.nickname = nickname;
        this.biography = biography;
        this.player_lvl = player_lvl;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getPlayer_lvl() {
        return player_lvl;
    }

    public void setPlayer_lvl(int player_lvl) {
        this.player_lvl = player_lvl;
    }
}


