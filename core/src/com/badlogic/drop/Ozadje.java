package com.badlogic.drop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mikik on 22. 05. 2017.
 */

public class Ozadje {
    TextureRegion[] regions;
    int[] xPos;
    int[] yPos;

    Ozadje(){
        regions = new TextureRegion[32];
        xPos = new int[32];
        yPos = new int[32];
    }

    public TextureRegion[] getRegions() {
        return regions;
    }

    public void setRegions(TextureRegion[] regions) {
        this.regions = regions;
    }

    public void setXRegion(int i, TextureRegion t){
        this.regions[i] = t;
    }

    public TextureRegion getXRegion(int i){
        return this.regions[i];
    }

    public int getxPos(int i) {
        return xPos[i];
    }

    public void setxPos(int i, int xPos) {
        this.xPos[i] = xPos;
    }

    public int getyPos(int i) {
        return yPos[i];
    }

    public void setyPos(int i, int yPos) {
        this.yPos[i] = yPos;
    }
}
