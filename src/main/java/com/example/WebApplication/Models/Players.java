package com.example.WebApplication.Models;

import java.util.Objects;

public class Players {

    protected int id;
    protected String nickname;
    protected String biography;
    protected int lvl;

    public Players() {
    }

    public Players(int player_id) {
        this.id=id;
    }

    public Players(int id, String nickname, int lvl, String biography) {
        this(nickname, lvl, biography);
        this.id = id;
    }

    public Players(String nickname, int lvl, String biography) {
        this.nickname = nickname;
        this.biography = biography;
        this.lvl = lvl;
    }

    public int getId() {
        return id;
    }

    public void setId(int player_id) {
        this.id = id;
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

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Players player = (Players) o;
        return Objects.equals(nickname,player.nickname) && Objects.equals(id, player.id) && Objects.equals(lvl, player.lvl) && Objects.equals(biography,player.biography);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, lvl, biography);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", level='" + lvl + '\'' +
                ", bio=" + biography +
                '}';
    }
}



