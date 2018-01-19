package com.badlogic.drop;

/**
 * Created by mikik on 16. 01. 2018.
 */

public class arffMinigameInstance {
    public int lvl;
    public float mySpeed;
    public float boySpeed;
    public int numberOfTrees;
    public int time;
    public String val;

    public arffMinigameInstance(){

    };

    public arffMinigameInstance(int l, float my, float boy, int num, int t, String v){
        lvl = l;
        mySpeed = my;
        boySpeed = boy;
        numberOfTrees = num;
        time = t;
        val = v;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public float getMySpeed() {
        return mySpeed;
    }

    public void setMySpeed(float mySpeed) {
        this.mySpeed = mySpeed;
    }

    public float getBoySpeed() {
        return boySpeed;
    }

    public void setBoySpeed(float boySpeed) {
        this.boySpeed = boySpeed;
    }

    public int getNumberOfTrees() {
        return numberOfTrees;
    }

    public void setNumberOfTrees(int numberOfTrees) {
        this.numberOfTrees = numberOfTrees;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
