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
        DataAll a = new DataAll(1, 1, 8, 0, 120);
        return a;
    }
}

