package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mikik on 22. 05. 2017.
 */

public class Ozadje {
    TextureRegion[] regions;
    Texture[] texture;
    int[] xPos;
    int[] yPos;

    Ozadje(){
        regions = new TextureRegion[32];
        xPos = new int[32];
        yPos = new int[32];
    }

    Ozadje(int x, int y){
        texture = new Texture[x * y];
        xPos = new int[x * y];
        yPos = new int[x * y];
    }

    public TextureRegion[] getRegions() {
        return regions;
    }

    public void setRegions(TextureRegion[] regions) {
        this.regions = regions;
    }

    public void setTexture(int i, String t) {
        this.texture[i] = new Texture(Gdx.files.internal(t));
    }

    public void setXRegion(int i, TextureRegion t){
        this.regions[i] = t;
    }

    public TextureRegion getXRegion(int i){
        return this.regions[i];
    }

    public Texture getXTexture(int i){
        return this.texture[i];
    }

    public Texture getTexture(int i){
        return this.texture[i];
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
