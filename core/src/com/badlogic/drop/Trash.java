package com.badlogic.drop;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mikik on 17. 05. 2017.
 */

public class Trash{
    int width;
    int height;
    String name;
    int weight;
    int value;
    String img;
    TrashType type;

    Rectangle smet;

    Trash create_clone(){
        return new Trash(width,height,name,value,weight,img,type,smet);
    };

    Trash(int x, int y, String n, int v, int w, String i ,TrashType t){
        width = x;
        height = y;
        name = n;
        weight = w;
        value = v;
        img = i;
        type = t;
    }

    Trash(int x, int y, String n, int v, int w, String i ,TrashType t, Rectangle r){
        width = x;
        height = y;
        name = n;
        weight = w;
        value = v;
        img = i;
        type = t;
        smet = r;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public TrashType getType() {
        return type;
    }

    public void setType(TrashType type) {
        this.type = type;
    }

    public void setSmet(Rectangle smet){
        this.smet = smet;
    }

    public void setSmet(int x, int y, int w, int h) {
        this.smet = new Rectangle();
        this.smet.height = h;
        this.smet.width = w;
        this.smet.x = x;
        this.smet.y = y;
    }

    public Rectangle getSmet() {
        return smet;
    }


}


