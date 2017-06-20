package com.badlogic.drop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikik on 4. 06. 2017.
 */

public class DataAll {
    int lvl;
    float speed;
    int inventorySpace;
    int time;
    int gold;
    int gamesPlayed;
    int gamesFailed;
    int totalTrashPickups;
    int gamesAborted;

    public DataAll(){
        this.lvl = 0;
        this.speed = 0;
        this.inventorySpace = 0;
        this.gold = 0;
        time = 120;
    }

    public DataAll(int lvl, int speed, int inventorySpace, int gold, int timer) {
        this.lvl = lvl;
        this.speed = speed;
        this.inventorySpace = inventorySpace;
        this.gold = gold;
        this.time = timer;
        this.gamesPlayed = 0;
        this.gamesFailed = 0;
        this.totalTrashPickups = 0;
        this.gamesAborted = 0;
    }

    public int getGamesAborted() {
        return gamesAborted;
    }

    public void setGamesAborted(int gamesAborted) {
        this.gamesAborted = gamesAborted;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesFailed() {
        return gamesFailed;
    }

    public void setGamesFailed(int gamesFailed) {
        this.gamesFailed = gamesFailed;
    }

    public int getTotalTrashPickups() {
        return totalTrashPickups;
    }

    public void setTotalTrashPickups(int totalTrashPickups) {
        this.totalTrashPickups = totalTrashPickups;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getInventorySpace() {
        return inventorySpace;
    }

    public void setInventorySpace(int inventorySpace) {
        this.inventorySpace = inventorySpace;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public static DataAll scenarijA(){
        DataAll a = new DataAll(1, 1, 6, 0, 90);
        return a;
    }
}

