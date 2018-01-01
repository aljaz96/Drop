package com.badlogic.drop;

import java.util.Random;

/**
 * Created by mikik on 1. 01. 2018.
 */

public class Minigame {
    public float drawPosX = 179.5f * 1.3f;
    public float drawPosY = 250f;
    public int boyID;
    public boolean isActive;
    public float timer;
    public int numberOfTrees;
    public int dificulty;
    public float mySpeed;
    public float boySpeed;
    public int[][] zones;
    public int Xx = -1;
    public int Xy = -1;
    public int Cx;
    public int Cy;

    public Minigame(int id, int numberOfT, int diff, float mSpeed, float bSpeed){
        boyID = id;
        numberOfTrees = numberOfT;
        dificulty = diff;
        mySpeed = mSpeed;
        boySpeed = bSpeed;
        zones = new int[8][8];
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                zones[i][j] = 0;
            }
        }
        Random rand = new Random();
        for(int i=0; i<numberOfTrees; i++){
            int x = rand.nextInt(8-0) + 0;
            int y = rand.nextInt(8-0) + 0;
            while(zones[x][y] == 3 || (y == 6 && (x == 3 || x == 4)) || (y == 0 && (x == 3 || x == 4))){
                x = rand.nextInt(8-0) + 0;
                y = rand.nextInt(8-0) + 0;
            }
            zones[x][y] = 3;
        }
        zones[3][0] = 1;
        Cx = 3;
        Cy = 0;
        zones[4][6] = 2;
    }
}
