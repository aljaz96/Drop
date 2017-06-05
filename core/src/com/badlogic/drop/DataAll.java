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
    int gold;
    ArrayList<Level> completedLvls;

    public DataAll(){
        this.lvl = 0;
        this.speed = 0;
        this.inventorySpace = 0;
        this.gold = 0;
        this.completedLvls = new ArrayList<Level>();
    }

    public DataAll(int lvl, int speed, int inventorySpace, int gold) {
        this.lvl = lvl;
        this.speed = speed;
        this.inventorySpace = inventorySpace;
        this.gold = gold;
        this.completedLvls = new ArrayList<Level>();
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

    public ArrayList<Level> getCompletedLvls() {
        return completedLvls;
    }

    public void setCompletedLvls(ArrayList<Level> completedLvls) {
        this.completedLvls = completedLvls;
    }

    public void addLevel(Level l){
        this.completedLvls.add(completedLvls.size(), l);
    }

    public DataAll scenarijA(){
        DataAll a = new DataAll(1, 1, 8, 0);
        return a;
    }
}

