package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by mikik on 4. 12. 2017.
 */

public class Badboy {
        public int ID;
        public TextureRegion[] walkFrames;
        public int startTime;
        public int xStart;
        public int yStart;
        public int xEnd;
        public int yEnd;
        public float deltaX;
        public float deltaY;
        public float speed = 1.35f;
        public boolean direction;
        public int directionX = 1;
        public int directionY = 1;
        public int defaultX;
        public int defaultY;
        public Rectangle boy;
        public Animation<TextureRegion> runAnimation;
        public TextureRegion currentImage;
        public boolean isActive = false;
        public boolean isBumping = false;
        public int playSound = 1;
        public int counter = 0;
        public int oldX[];
        public int oldY[];
        public int positionCounter = 0;
        public int numberOfTrash;
        public int trashDroped = 0;
        public boolean isBad = false;
        public boolean wasBlamed = false;
        public boolean canDrop = true;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Badboy(int height, int width){
        //isActive = true;
        oldX = new int[2];
        oldY = new int[2];
        oldX[0] = 1;
        oldX[1] = 2;
        oldY[0] = 3;
        oldY[1] = 4;
        walkFrames = new TextureRegion[2 * 2];
        Texture t = new Texture(Gdx.files.internal("bad_boy.png"));
        TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth() / 2, t.getHeight() / 2);
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        runAnimation = new Animation<TextureRegion>(0.1f, walkFrames);
        boy = new Rectangle();
        boy.width = 64;
        boy.height = 90;
        // 1-2-3-4 = levo-desno-dol-gor
        int izbira1 = izberi1(height,width);
        boy.x = xStart;
        boy.y = yStart;
        int izbira2 = izberi2(height,width,izbira1);
        calculateDelta();
    }

    //      if (bucket.y < 130) bucket.y = 130;
	//		if (bucket.y > (height * 512) - 200) bucket.y = (height * 512) - 200; //1815, 233
	//		if (bucket.x < 60) bucket.x = 60;
	//		if (bucket.x > (width * 512)- 146) bucket.x = (width * 512)- 146; //3950

    int izberi1(int height, int width){
        Random rand = new Random();
        int izbira1 = rand.nextInt(5-1) + 1; // 1-2-3-4 = levo-desno-dol-gor
        if(izbira1 == 1){ //levo
            xStart = 65;
            yStart = rand.nextInt(((height * 512)-300)-300) + 300;
        }
        else if(izbira1 == 2){
            xStart = (width * 512)- 148;
            yStart = rand.nextInt(((height * 512)-300)-300) + 300;
        }
        else if(izbira1 == 3){
            xStart = rand.nextInt(((width * 512)-300)-300) + 300;
            yStart = 148;
        }
        else if(izbira1 == 4){
            xStart = rand.nextInt(((width * 512)-300)-300) + 300;
            yStart = (height * 512) - 202;
        }
        boy.x = xStart;
        boy.y = yStart;
        return izbira1;
    }

    int izberi2(int height, int width, int izbira){
        Random rand = new Random();
        int izbira2 = rand.nextInt(5-1) + 1;;
        while(izbira == izbira2){
            izbira2 = rand.nextInt(5-1) + 1; // 1-2-3-4 = levo-desno-dol-gor
        }
        if(izbira2 == 1){ //levo
            xEnd = 0;
            yEnd = rand.nextInt(((height * 512)-300)-300) + 300;
        }
        else if(izbira2 == 2){
            xEnd = (width * 512)- 0;
            yEnd = rand.nextInt(((height * 512)-300)-300) + 300;
        }
        else if(izbira2 == 3){
            xEnd = rand.nextInt(((width * 512)-300)-300) + 300;
            yEnd = 0;
        }
        else if(izbira2 == 4){
            xEnd = rand.nextInt(((width * 512)-300)-300) + 300;
            yEnd = (height * 512) - 0;
        }
        calculateDelta();
        defaultX = directionX;
        defaultY = directionY;
        return izbira2;
    }

    // 1-2-3-4 = levo-desno-dol-gor
    void calculateDelta(){
        int dirX = xEnd - (int)boy.x;
        int dirY = yEnd - (int)boy.y;
        float len = (float)Math.sqrt(dirX * dirX + dirY * dirY );
        deltaX = dirX / len;
        deltaY = dirY / len;
        if(deltaX >= 0){
            direction = false;
            directionX = 1;
        }
        else{
            direction = true;
            directionX = 1;
        }
        if(deltaY >= 0){
            directionY = 1;
        }
        else{
            directionY = -1;
        }
    }
}


