package com.example.WebApplication.Models;

public class Inventory {
    protected int item_id;
    protected String item_name;
    protected int player_id;

    public Inventory(){
    }

    public Inventory(int item_id) {
        this.item_id = item_id;
    }

    public Inventory(int item_id, String item_name, int player_id) {
        this(item_name, player_id);
        this.item_id = item_id;
    }

    public Inventory(String item_name, int player_id) {
        this.item_name = item_name;
        this.player_id = player_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }
}
