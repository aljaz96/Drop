package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;


public class Game implements com.badlogic.gdx.Screen {

	final Screen game;
	//////////// TEXTURE   ////////////
	private Texture bucketImage;
    private Texture background;
    private Texture blueBin;
    private Texture redBin;
    private Texture greenBin;
    private Texture yellowBin;
	private Texture basicBin;
	private Texture tree;
	private TextureRegion[] regions;
	private TextureRegion playerImage;
	private Texture bagImage;
	private Texture popUP;
	private Texture winText;
	private Texture failText;
	private Texture menu;
	private Texture retry;
	private Texture continuee;
	private Texture standing;
	private Texture badboy;
	private Texture x;
	private Texture minigame_screen;
	private Texture minigame_character;
	private Texture minigame_boy;
	private Texture minigame_tree;
	private Texture minigame_x;
	private Texture minigame_checkmate;
	private Texture escaped;
	private Texture caught;
    ////////////////VELIKOST//////////////////
	private int height = 4;
	private int width = 8;
	//////// ZVOKI /////////////////
	private Sound crackSound;
	private Sound dropSound;
	private Sound paperSound;
	private Sound metalSound;
	private Sound plasticSound;
	private Sound glassSound;
	private Sound laugh;
	private Music music;
	private Sound error;
	private Sound winSound;
	private OrthographicCamera camera;
	private SpriteBatch batch;
    ////////////////////////////////
	////// RECTANGLI ///////////////
	private Rectangle bucket;
	private Rectangle[] trees;
	private Rectangle bag;
	private Rectangle swap;
	private Rectangle tester;
    ////////////////////////////////
	//POLJA Z SMETMI/KOŠIM/SMETEH NA TLEH
	private Array<Trash> vseSmeti;
    private Array<Rectangle> bins;
	private boolean[] booleanBins = new boolean[4];
	private Array<Trash> smeti;
	/////////////////////////////////
	//INVENTORY  ///////////////////
	private Array<Trash> Inventory;
	private int inventorySize;
	private int currentInventory = 0;
	private int score = 0;

    ////////// OTHER
    private Viewport viewport;
	private Badboy[] boys;
	private Minigame minigame;
	/////// INT/DOUBLE
    private int counter;
	private int counter2 = 0;
	private int numberOfTrash;
	float deltaTime;
	private float speed;
	private float oldX;
	private float oldY;
	private int binChance = 20;
	int totalScore = 0;
	private Random rand;
	private int wait;
	////// FONTI/TEXTI
	private BitmapFont font;
	private BitmapFont inventoryFont;
	private BitmapFont scoreFont;
	private BitmapFont timerFont;
	////// UPORABLJENI STRINGI
	private String litterText;
	private String inventoryText;
	private String scoreText;
	private String timerText;

	private Touchpad touchpad;
	private Skin touchpadSkin;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private Stage stage;
	boolean zvok;
    private Animation<TextureRegion> runningAnimation;
	private Texture walk;
    private Texture[] walkSheet;
	private boolean reciklira;
	private boolean playerDirection = true;
	private float stateTime;
	private int totalTrash;


	float time;
	boolean gameOn;
	boolean minigameOn = false;
	boolean win;
    //// MY CLASSES
    Ozadje ozadje;
	DataAll all;

	public Game(final Screen gam) {
		this.game = gam;
		all = SaveLoad.loadState();
		inventorySize = all.getInventorySpace();
		speed = all.getSpeed() * 5;
		rand = new Random();
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		//ozadje = new Ozadje();
		//createRegions(ozadje);
		//height = 3; //rand.nextInt(12-5) + 4;
		//width = 3; //rand.nextInt(12-5) + 4;
		height = 6;//rand.nextInt(12-5) + 4;
		width = 6;//rand.nextInt(12-5) + 4;
		ozadje = new Ozadje(width,height);
		CreateCustomRegions(ozadje);
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		/////// ZA OZADJA /////////////////////////////////////
		time = all.getTime() + (all.lvl * 5) + height * width;
		trees = new Rectangle[height + width + rand.nextInt((height + width) - 1) + 0];//rand.nextInt(13-3) + 3
		gameOn = true;
		win = false;
		font = new BitmapFont();
		inventoryFont = new BitmapFont();
		scoreFont = new BitmapFont();
		timerFont = new BitmapFont();

        background = new Texture(Gdx.files.internal("background2.png"));
		bucketImage = new Texture(Gdx.files.internal("Hat_man1.png"));
		badboy = new Texture(Gdx.files.internal("bad_boy1.png"));
        blueBin = new Texture(Gdx.files.internal("blueBin.png"));
        redBin = new Texture(Gdx.files.internal("redBin.png"));
        greenBin = new Texture(Gdx.files.internal("greenBin.png"));
        yellowBin = new Texture(Gdx.files.internal("orangeBin.png"));
		basicBin = new Texture(Gdx.files.internal("basicBin.png"));
		tree = new Texture(Gdx.files.internal("tree.png"));
		bagImage = new Texture(Gdx.files.internal("bag.png"));
		popUP = new Texture(Gdx.files.internal("popUp.png"));
		failText = new Texture(Gdx.files.internal("stage_failed.png"));
		winText = new Texture(Gdx.files.internal("stage_complete.png"));
		menu = new Texture(Gdx.files.internal("menu.png"));
		retry = new Texture(Gdx.files.internal("retry.png"));
		continuee = new Texture(Gdx.files.internal("continue.png"));
		standing = new Texture(Gdx.files.internal("hat_man.png"));
		escaped = new Texture(Gdx.files.internal("escaped.png"));
		caught = new Texture(Gdx.files.internal("caught.png"));
		x = new Texture(Gdx.files.internal("x.png"));
		minigame_screen = new Texture(Gdx.files.internal("minigame.jpg"));
		minigame_character = new Texture(Gdx.files.internal("minigame_me.png"));
		minigame_boy = new Texture(Gdx.files.internal("minigame_boy.png"));
		minigame_tree = new Texture(Gdx.files.internal("minigame_tree.png"));
		minigame_x = new Texture(Gdx.files.internal("minigame_x.png"));
		minigame_checkmate = new Texture(Gdx.files.internal("minigame_checkmate.png"));
		// load the sound effects and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("gameTheme.mp3"));
		paperSound = Gdx.audio.newSound(Gdx.files.internal("papir.mp3"));
		glassSound = Gdx.audio.newSound(Gdx.files.internal("steklo.mp3"));
		metalSound = Gdx.audio.newSound(Gdx.files.internal("kovina.mp3"));
		plasticSound = Gdx.audio.newSound(Gdx.files.internal("plastika.mp3"));
		crackSound = Gdx.audio.newSound(Gdx.files.internal("crackSound.mp3"));
		laugh = Gdx.audio.newSound(Gdx.files.internal("smirk.mp3"));
		error = Gdx.audio.newSound(Gdx.files.internal("error.wav"));
		winSound = Gdx.audio.newSound(Gdx.files.internal("win.mp3"));

		// start the playback of the background music immediately
		music.setLooping(true);
		music.play();

      //... more to come ...
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 160;
		bucket.y = 160;
		bucket.width = 64;
		bucket.height = 90;

		bag = new Rectangle();
		bag.width = 57;
		bag.height = 51;

		//////////// MOJE
		stateTime = 0f;
		reciklira = false;
        counter = 0;
		//ŠTEVILO ODPADKOV
		totalTrash = numberOfTrash = 3 + all.getLvl() + (int)(all.getLvl() / 3);
		//////////////////
		font.getData().setScale(1.5f,1.5f);
		inventoryFont.getData().setScale(1.5f, 1.5f);
		scoreFont.getData().setScale(1.5f, 1.5f);
		timerFont.getData().setScale(1.2f, 1.2f);
		camera.viewportWidth = 1200;
		camera.viewportHeight = 700;
		//float aspectRation = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		walk = standing;
		TextureRegion[][] tmp = TextureRegion.split(walk,
				walk.getWidth() / 2,
				walk.getHeight() / 2);

		TextureRegion[] walkFrames = new TextureRegion[2 * 2];
		int index = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		runningAnimation = new Animation<TextureRegion>(0.1f, walkFrames);

		boys = new Badboy[10];
		for(int i=0; i<boys.length; i++){
			boys[i] = new Badboy(height,width);
			float s = ((float)(130 + all.getLvl() + rand.nextInt(30)) / 100);
			boys[i].setSpeed(s);
			boys[i].ID = i + 1;
			boys[i].startTime = rand.nextInt((int)time - ((int)time / 2)) + ((int)time / 2);
			boys[i].numberOfTrash = rand.nextInt(4-0) + 0;
		}

        createTouchPad();

        bins = new Array<Rectangle>();
		smeti = new Array<Trash>();
		vseSmeti = new Array<Trash>();
		Inventory = new Array<Trash>();
		createBins();
		for(int i=0; i<4; i++){
			booleanBins[i] = false;
		}
		//DREVESA
		narediDrevesa();
		createTrash();
		//spawnRaindrop();
		for(int i=0; i<numberOfTrash; i++){
			spawnTrash();
		}

		

	}

	//dodaj spreminjajoče kante/inventory/nove smeti/ mogoče drevesa/mogoče ozadaje poštimati

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		if(gameOn && !minigameOn) {
			deltaTime = Gdx.graphics.getDeltaTime();
			time -= deltaTime;
			if(time <= 0){
				gameOn = false;
				win = false;
			}
		}

		if(gameOn && numberOfTrash <= 0 && currentInventory == 0 && vseSmeti.size == 0){
			win = true;
			gameOn = false;
		}

		if((int)oldX == (int)bucket.x && (int)oldY == (int)bucket.y){
			playerImage = new TextureRegion(bucketImage, 100, 100);
		}
		else{
			playerImage = runningAnimation.getKeyFrame(stateTime, true);
		}
		if(oldX > bucket.x){ playerDirection = true;}
		if(oldX < bucket.x){ playerDirection = false;}

		oldX = bucket.x;
		oldY = bucket.y;

        int Xpos = (int)camera.position.x;
        int Ypos = (int)camera.position.y;

		//BOYS
		if(!minigameOn) {
			for (Badboy boy : boys) {
				if (boy.isActive) checkBoy(boy);
				//checkBoy(boy);
				if (boy.isActive) boy.currentImage = boy.runAnimation.getKeyFrame(stateTime, true);
				//if (!boy.isActive) boy.currentImage = new TextureRegion(badboy, 100, 100);
			}
		}
		//litterText = "Litter left: " +    touchpad.getKnobPercentX() + "_____"  +  touchpad.getKnobPercentY();
		//litterText ="x: " + boys[0].deltaX + "__" +  boys[0].directionX + "__" + boys[0].defaultX + " y:" + boys[0].directionY + "__" + boys[0].defaultY;
		litterText = "Litter left: " +  numberOfTrash;
		timerText = "Time left: " + String.format("%d",(long)time);
		inventoryText = " " + currentInventory + "/" + inventorySize;
		scoreText = "Score: " + score;
		if(currentInventory == inventorySize){ inventoryText = " FULL"; }
		camera.position.x = bucket.getX() + 64 / 2;
		camera.position.y = bucket.getY() + 90 / 2;
		if(camera.position.x < 605){ camera.position.x = 605;}
		if(camera.position.x > (width * 512) - 612){ camera.position.x = (width * 512) - 612;}  //3480
		if(camera.position.y < 350){ camera.position.y = 350;}
		if(camera.position.y > (height * 512) - 368){ camera.position.y = (height * 512) - 368;} //1680
		camera.update();

        batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//ZAČETEK RISANJA NA ZASLON:
            //OZADJE		//32 privzeto
            for(int i=0; i<width * height; i++){
                if(Ypos + 650 > (ozadje.getyPos(i) + 270) && Ypos - 650 < ozadje.getyPos(i) + 270 &&
                        Xpos + 1200 > (ozadje.getxPos(i) + 580) && Xpos - 600 < ozadje.getxPos(i) + 580) {
					batch.draw(ozadje.getXTexture(i), ozadje.getxPos(i), ozadje.getyPos(i)); //Z TEXTURAMI
					//batch.draw(ozadje.getXRegion(i), ozadje.getxPos(i), ozadje.getyPos(i)); // Z REGIONS
                }
            }
			//KOŠI ZA SMETI
			batch.draw(blueBin, bins.get(0).x, bins.get(0).y);
			batch.draw(redBin, bins.get(1).x, bins.get(1).y);
			batch.draw(greenBin, bins.get(2).x, bins.get(2).y);
			batch.draw(yellowBin, bins.get(3).x, bins.get(3).y);
			batch.draw(basicBin, bins.get(4).x, bins.get(4).y);
			//SMETI
			for(Trash t: vseSmeti) {
				batch.draw(t.getText(), t.getSmet().x, t.getSmet().y);
			}
			//BOYS
			for (Badboy boy: boys) {
				if(boy.isActive) {
					batch.draw(boy.currentImage, boy.direction ? boy.boy.x + 100 : boy.boy.x, boy.boy.y, boy.direction ? -100 : 100, 100);
				}
			}
			//IGRALEC
			batch.draw(playerImage, playerDirection ? bucket.x+100 : bucket.x, bucket.y, playerDirection ? -100 : 100, 100);
			//spriteBatch.draw(currentFrame, flip ? x+width : x, y, flip ? -width : width, height);
			//DREVESA
			for (Rectangle item:trees) {
				//batch.draw(tree, item.x, item.y);
				batch.draw(tree, item.x - 25, item.y - 33);
			}
			//BESEDILA
			batch.draw(bagImage, camera.position.x + 300, camera.position.y + 290);
			font.draw(batch, litterText, camera.position.x - 500, camera.position.y + 330);
			inventoryFont.draw(batch, inventoryText, camera.position.x + 360, camera.position.y + 325);
			scoreFont.draw(batch, scoreText, camera.position.x - 50, camera.position.y + 290);
			scoreFont.draw(batch, timerText, camera.position.x - 50, camera.position.y + 330);
			batch.draw(x, camera.position.x + 470, camera.position.y + 260, 100, 100);
			//RISANJE POP UPA
			if(!gameOn){
				batch.draw(popUP, camera.position.x - 356.25f, camera.position.y - 225.75f, popUP.getWidth() * 1.5f, popUP.getHeight() * 1.5f);
				float percent = ((float)score/(float)totalScore) * 100;
				if(win){
					batch.draw(winText, camera.position.x - 295, camera.position.y + 100, winText.getWidth(), winText.getHeight());
					batch.draw(retry, camera.position.x - 280, camera.position.y - 170);
					batch.draw(menu, camera.position.x + 80, camera.position.y - 170);
					batch.draw(continuee, camera.position.x - 175, camera.position.y - 90, continuee.getWidth() * 1.1f, continuee.getHeight() * 1.1f);
					timerText = "Time left: " + String.format("%d",(long)time);
					scoreText = "Score achieved: " + score + "/" + totalScore + "               " + String.format("%d",(long)percent) + "%";
					scoreFont.draw(batch, timerText, camera.position.x - 250, camera.position.y + 70);
					font.draw(batch, scoreText, camera.position.x - 250, camera.position.y + 30);
				}
				if(!win){
					batch.draw(failText, camera.position.x - 285, camera.position.y + 100, winText.getWidth(), winText.getHeight());
					batch.draw(retry, camera.position.x - 280, camera.position.y - 170);
					batch.draw(menu, camera.position.x + 80, camera.position.y - 170);
					litterText = "Litter missed: " +  (numberOfTrash + Inventory.size);
					scoreText = "Score achieved: " + score + "/" + totalScore + "               " + String.format("%d",(long)percent) + "%";
					font.draw(batch, litterText, camera.position.x - 250, camera.position.y + 50);
					font.draw(batch, scoreText, camera.position.x - 250, camera.position.y);
				}
			}
			//RISANJE MINIGAMA
			if(minigameOn){
				batch.draw(minigame_screen, camera.position.x - minigame.drawPosX, camera.position.y - minigame.drawPosY, minigame_screen.getWidth() * 1.3f, minigame_screen.getHeight() * 1.3f);
				scoreFont.draw(batch, "Time left: " + minigame.timer, camera.position.x - 60, camera.position.y + 317);
				scoreFont.draw(batch, "Your speed: " + minigame.mySpeed, camera.position.x - 200, camera.position.y + 295);
				scoreFont.draw(batch, "Boy speed: " + minigame.boySpeed, camera.position.x + 70, camera.position.y + 290);
				scoreFont.draw(batch, "Boy move in: " + minigame.boySpeedTimer, camera.position.x + 70, camera.position.y + 270);
				if(minigame.mySpeedTimer > 0){
					scoreFont.draw(batch, "Your move in: " + minigame.mySpeedTimer, camera.position.x - 200, camera.position.y + 270);
				}
				else{
					scoreFont.draw(batch, "MOVE READY!", camera.position.x - 200, camera.position.y + 270);
				}
				for(int i=0; i<8; i++){
					for(int j = 0; j<8; j++){
						if(minigame.zones[i][j] == 3) {
							batch.draw(minigame_tree, camera.position.x - minigame.drawPosX + (47 * 1.3f * i), camera.position.y - minigame.drawPosY + (47 * 1.3f * j), minigame_tree.getWidth() * 1.3f, minigame_tree.getHeight() * 1.3f);
						}
						if(minigame.zones[i][j] == 1){
							batch.draw(minigame_character, camera.position.x - minigame.drawPosX + (47 * 1.3f * i), camera.position.y - minigame.drawPosY + (47 * 1.3f * j), minigame_character.getWidth() * 1.3f, minigame_character.getHeight() * 1.3f);
						}
						if(minigame.zones[i][j] == 2){
							batch.draw(minigame_boy, camera.position.x - minigame.drawPosX + (47 * 1.3f * i), camera.position.y - minigame.drawPosY + (47 * 1.3f * j), minigame_boy.getWidth() * 1.3f, minigame_boy.getHeight() * 1.3f);
						}
						if(minigame.zones[i][j] == 4){
							batch.draw(minigame_x, camera.position.x - minigame.drawPosX + (47 * 1.3f * i), camera.position.y - minigame.drawPosY + (47 * 1.3f * j), minigame_x.getWidth() * 1.3f, minigame_x.getHeight() * 1.3f);
						}
						if(minigame.zones[i][j] == 5){
							batch.draw(minigame_checkmate, camera.position.x - minigame.drawPosX + (47 * 1.3f * i), camera.position.y - minigame.drawPosY + (47 * 1.3f * j), minigame_checkmate.getWidth() * 1.3f, minigame_checkmate.getHeight() * 1.3f);
						}
					}
				}
				if(minigame.hasEnded){
					minigame.minigameEndedTimer -=  deltaTime;
					if(minigame.minigameEndedTimer >= 0){
						if(minigame.win == 1){
							batch.draw(caught, camera.position.x - caught.getWidth()/2, camera.position.y - caught.getHeight()/2, caught.getWidth(), caught.getHeight());
						}
						if(minigame.win == -1){
							batch.draw(escaped, camera.position. x- escaped.getWidth()/2, camera.position.y - escaped.getHeight()/2, escaped.getWidth(), escaped.getHeight());
						}
					}
					if(minigame.minigameEndedTimer < 0){
						minigameOn = false;
					}
				}
			}
			//KONEC RISANJA NA ZASLON
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

		////////////////////////PREMIKANJE///////////////////////////////////////
		if(wait <= 0 && gameOn && !minigameOn) {
			//Premikaje characterja
			bucket.setX(bucket.getX() + touchpad.getKnobPercentX() * speed);
			bucket.setY(bucket.getY() + touchpad.getKnobPercentY() * speed);
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				bucket.x -= 66 * speed * Gdx.graphics.getDeltaTime();
				playerDirection = true;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				bucket.x += 66 * speed * Gdx.graphics.getDeltaTime();
				playerDirection = false;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				bucket.y += 66 * speed * Gdx.graphics.getDeltaTime();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				bucket.y -= 66 * speed * Gdx.graphics.getDeltaTime();
			}

			//Premikanje boys
			for (Badboy boy : boys) {
				if(boy.isActive) {
					//METANJE ODPADKOV OSEBE
					if(boy.numberOfTrash > 0 && boy.trashDroped != numberOfTrash && boy.canDrop){
						int r = rand.nextInt(1000-1) + 1;
						if(r == 250){
							boy.trashDroped++;
							boy.isBad = true;
							boy.wasBlamed = false;
							Trash nov = smeti.get(rand.nextInt(16 - 0) + 0).create_clone();
							nov.setText(new Texture(Gdx.files.internal(nov.getImg())));
							counter = counter + 1;
							Rectangle smet = new Rectangle();
							smet.x = boy.boy.x;
							smet.y = boy.boy.y;
							for (Rectangle item:trees) {
								if(smet.overlaps(item)){
									smet.y = smet.y - 30;
								}
							}
							smet.width = nov.width;
							smet.height = nov.height;
							nov.setSmet(smet);
							totalScore = nov.getValue() + totalScore;
							vseSmeti.add(nov);
						}
					}
					//////////////////////////////////

					boy.oldX[boy.positionCounter] = (int)boy.boy.x;
					boy.oldY[boy.positionCounter] = (int)boy.boy.y;
					boy.positionCounter++;
					if(boy.positionCounter >= 2) boy.positionCounter = 0;

					int up_down = 0;
					int left_right = 0;
					boy.isBumping = false;
					for (Rectangle item : trees) {
						boy.boy.x += 20;
						if (boy.boy.overlaps(item) && boy.defaultX == 1) {
							left_right++;
							boy.isBumping = true;
						}
						boy.boy.x -= 40;
						if (boy.boy.overlaps(item) && boy.isBumping == false && boy.defaultX == -1) {
							left_right++;
							boy.isBumping = true;
						}
						boy.boy.x += 20;
						boy.boy.y += 20;
						if (boy.boy.overlaps(item) && boy.isBumping == false && boy.defaultY == 1) {
							up_down++;
							boy.isBumping = true;
						}
						boy.boy.y -= 40;
						if (boy.boy.overlaps(item) && boy.isBumping == false && boy.defaultY == -1) {
							up_down++;
							boy.isBumping = true;
						}
						boy.boy.y += 20;
					}
					boy.counter--;
					if (boy.counter < 0) {
						boy.directionX = boy.defaultX;
					}
					if (up_down > 0 || left_right > 0) {
						if (up_down > 0 && left_right > 0 || (boy.oldX[0] == boy.oldX[1] && boy.oldY[0] == boy.oldY[1])) {
							boy.counter = 200;
							boy.directionX = boy.defaultX * -1;
							//boy.direction = !boy.direction;
							//boy.boy.x = boy.boy.x + boy.deltaX * boy.speed * boy.directionX;
							boy.isBumping = false;
						}
						else if (up_down > 0 && left_right == 0) {
							if(boy.deltaX > 0) {
								boy.boy.x = boy.boy.x + 1 * boy.directionX;
							}
							if(boy.deltaX < 0){
								boy.boy.x = boy.boy.x - 1 * boy.directionX;
							}
						}
						else if (up_down == 0 && left_right > 0) {
							if(boy.deltaY > 0) {
								boy.boy.y = boy.boy.y + 1 * boy.defaultY;
							}
							if(boy.defaultY < 0){
								boy.boy.y = boy.boy.y - 1 * boy.defaultY;
							}
						}
					}
					boy.calculateDelta();
				}
			}
			for (Badboy boy: boys) {
				if(boy.startTime > time && !boy.isActive && boy.playSound == 1){
					boy.isActive = true;
					laugh.play();
					boy.playSound++;
				}
				if (boy.isActive == true && boy.isBumping == false) {
					boy.boy.x = boy.boy.x + boy.deltaX * boy.speed * boy.directionX;
					boy.boy.y = boy.boy.y + boy.deltaY * boy.speed;
				}
			}
		}
		//////////////////////////KONEC PREMIKANJA////////////////////////


		if(gameOn) {
			if (bucket.y < 130) bucket.y = 130;
			if (bucket.y > (height * 512) - 200) bucket.y = (height * 512) - 200; //1815, 233
			if (bucket.x < 60) bucket.x = 60;
			if (bucket.x > (width * 512) - 146) bucket.x = (width * 512) - 146; //3950

			//Omogočitev zaletavanja v drevo
			for (Rectangle item : trees) {
				if (item.overlaps(bucket)) {
					bucket.x = oldX;
					bucket.y = oldY;
				}
			}

			Iterator<Trash> iter = vseSmeti.iterator();
			while (iter.hasNext()) {
				Trash trash = iter.next();
				if (trash.getSmet().overlaps(bucket)) {
					if (currentInventory + trash.getWeight() <= inventorySize) {
						Inventory.add(trash);
						currentInventory = currentInventory + trash.getWeight();
						dropSound.play();
						trash.odstrani();
						iter.remove();
						numberOfTrash--;
					}
				}
			}

			dajVKos(0, TrashType.METAL, booleanBins[0]);
			dajVKos(1, TrashType.GLASS, booleanBins[1]);
			dajVKos(2, TrashType.PAPER, booleanBins[2]);
			dajVKos(3, TrashType.PLASTIC, booleanBins[3]);
			dajVKos(4, TrashType.ALL, true);

			wait--;
		}

		// ITERAKCIJE Z ZASLONOM /////////////////////////////////////////////////

		if(gameOn && !minigameOn){
			for (Badboy boy: boys) {
				if(bucket.overlaps(boy.boy) && Gdx.input.justTouched() && minigameOn == false && boy.wasBlamed == false) {
					Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(tmp);
					Rectangle gamestart=new Rectangle(camera.position.x ,camera.position.y - 400, 800, 800);
					if (gamestart.contains(tmp.x, tmp.y)) {
						boy.wasBlamed = true;
						if (boy.isBad == true) {
							minigameOn = true;
							minigame = new Minigame(boy.ID, trees.length, all.getLvl(), all.getSpeed(), boy.speed);
						} else if (boy.isBad == false) {
							score -= (150 + 10 * all.getLvl());
							error.play();
							boy.setSpeed(boy.getSpeed() * 2);
						}
					}
				}
			}
		}
		/////(///
		/////////////
		///////////// MINIGAME /////////////////
		if(minigameOn && minigame.win == 0){
			if(!minigame.hasEnded) {
				minigame.timer -= deltaTime;
				minigame.timer = Math.round(minigame.timer * 100);
				minigame.timer = minigame.timer / 100;
				minigame.mySpeedTimer -= deltaTime;
				minigame.mySpeedTimer = Math.round(minigame.mySpeedTimer * 100);
				minigame.mySpeedTimer = minigame.mySpeedTimer / 100;
				minigame.boySpeedTimer -= deltaTime;
				minigame.boySpeedTimer = Math.round(minigame.boySpeedTimer * 100);
				minigame.boySpeedTimer = minigame.boySpeedTimer / 100;
			}
			if(minigame.boySpeedTimer <= 0.05){
				minigame.oldBx = minigame.Bx;
				minigame.oldBy = minigame.By;
				int bestX = -1;
				int bestY = -1;
				for(int i=0; i<minigame.dificulty; i++) {
 					int newX = -1;
					int newY = -1;
					while ((newY < 0 || newY > 7) || (newX < 0 || newX > 7)) {
						int r = rand.nextInt(8 - 1) + 1;
						if (r == 1) { //gor
							newX = minigame.Bx;
							newY = minigame.By + 1;
						} else if (r == 2) { //desno gor
							newX = minigame.Bx + 1;
							newY = minigame.By + 1;
						} else if (r == 3) { //desno
							newX = minigame.Bx + 1;
							newY = minigame.By;
						} else if (r == 4) { //desno dol
							newX = minigame.Bx + 1;
							newY = minigame.By - 1;
						} else if (r == 5) { //dol
							newX = minigame.Bx;
							newY = minigame.By - 1;
						} else if (r == 6) { //levo dol
							newX = minigame.Bx - 1;
							newY = minigame.By - 1;
						} else if (r == 7) { //levo
							newX = minigame.Bx;
							newY = minigame.By + 1;
						} else if (r == 8) { //levo gor
							newX = minigame.Bx - 1;
							newY = minigame.By + 1;
						}
						//if(newX == minigame.oldBx && newY == minigame.oldBy){
						//	continue;
						//}
						if((newX > 7 || newX < 0) || (newY > 7 || newY < 0)){
							continue;
						}
						if(minigame.zones[newX][newY] == 1 || minigame.zones[newX][newY] == 3){
							continue;
						}
						if (bestX == -1 && bestY == -1) {
							bestX = newX;
							bestY = newY;
						}
						else{
							double previousDistance = Math.sqrt(((minigame.Cx - bestX)*(minigame.Cx - bestX))+((minigame.Cy - bestY)*(minigame.Cy - bestY)));
							double newdistance = Math.sqrt(((minigame.Cx - newX)*(minigame.Cx - newX))+((minigame.Cy - newY)*(minigame.Cy - newY)));
							if(newdistance > previousDistance){
								bestX = newX;
								bestY = newY;
							}
						}
					}
				}
				if(minigame.zones[minigame.oldBx][minigame.oldBy] == 5){
					minigame.zones[minigame.oldBx][minigame.oldBy] = 4;
				}
				else{
					minigame.zones[minigame.oldBx][minigame.oldBy] = 0;
				}
				if(bestX == -1 || bestY == -1){
					bestX = minigame.oldBx;
					bestY = minigame.oldBy;
				}
				minigame.zones[bestX][bestY] = 2;
				minigame.Bx = bestX;
				minigame.By = bestY;
				minigame.boySpeedTimer = 2.8f - minigame.boySpeed;
			}

			if(minigame.mySpeedTimer <= 0 && !minigame.hasEnded){
				int Xx = 0;
				int Xy = 0;
				int myX = 0;
				int myY = 0;
				boolean xOn = false;
				for(int x=0; x<8; x++) {
					for (int y = 0; y < 8; y++) {
						if (minigame.zones[x][y] == 4 || minigame.zones[x][y] == 5) {
							Xx = x;
							Xy = y;
							xOn = true;
						}
						if(minigame.zones[x][y] == 1){
							myX = x;
							myY = y;
						}
					}
				}
				if(xOn == true){
					minigame.zones[myX][myY] = 0;
					minigame.zones[Xx][Xy] = 1;
					minigame.Cx = Xx;
					minigame.Cy = Xy;
					minigame.Xx = -1;
					minigame.Xy = -1;
					minigame.mySpeedTimer = 3 - minigame.mySpeed;
				}
			}

			if(minigame.zones[minigame.Bx][minigame.By] != 5 && minigame.Xx != -1){
				minigame.zones[minigame.Xx][minigame.Xy] = 4;
			}

			if(Gdx.input.isTouched() && minigame.win == 0 && !minigame.hasEnded){
				Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
				for(int x=0; x<8; x++){
					for(int y=0; y<8; y++){
						Rectangle clickBounds=new Rectangle(minigame.drawPosX + 10 + (42 * x), minigame.drawPosY - 175 + (40 * y), 40, 40);
						if(clickBounds.contains(tmp.x, tmp.y)){
							if((minigame.Cx == x || minigame.Cx == x+1 || minigame.Cx == x-1) && (minigame.Cy == (y - 7) * -1 || minigame.Cy == ((y - 7) * -1)+1 || minigame.Cy ==((y - 7) * -1)-1)){
								if(minigame.zones[x][((y - 7) * -1)] != 3 && minigame.zones[x][((y - 7) * -1)] != 1 && minigame.zones[x][((y - 7) * -1)] != 5) {
								//	if((minigame.Cx == y && minigame.Cy != x) || (minigame.Cx != y && minigame.Cy == x) || (minigame.Cx != y && minigame.Cy != x)) {
										if (minigame.Xx != -1) {
											minigame.zones[minigame.Xx][minigame.Xy] = 0;
										}
										if(minigame.zones[x][((y - 7) * -1)] == 2){
											minigame.zones[x][((y - 7) * -1)] = 5;
										}
										else{
											minigame.zones[x][((y - 7) * -1)] = 4;
										}
										minigame.Xx = x;
										minigame.Xy = ((y - 7) * -1);
								//	}
								}
							}
						}
					}
				}
			}
			if( touchpad.getKnobPercentX() != 0 &&  touchpad.getKnobPercentY() != 0){
				 int posX = 0;
				 int posY = 0;
				 float x =  touchpad.getKnobPercentX();
				 float y =  touchpad.getKnobPercentY();
				 boolean doMove = false;
				 if(x < 0.33f && x > -0.33f && y > 0.2f){
					 //NARAVNOST GOR
					 posX = minigame.Cx;
					 posY = minigame.Cy + 1;
					 doMove = true;
				 }
				 else if(x > 0.33f && x < 0.8f && y > 0.2f){
					 //DESNO GOR
					 posX = minigame.Cx + 1;
					 posY = minigame.Cy + 1;
					 doMove = true;
				 }
				 else if(x < -0.33f && x > -0.8f && y > 0.2f){
					 //LEVO GOR
					 posX = minigame.Cx - 1;
					 posY = minigame.Cy + 1;
					 doMove = true;
				 }
				 else if(x > 0.8f && y < 0.33f && y > -0.33f){
					 //DESNO
					 posX = minigame.Cx + 1;
					 posY = minigame.Cy;
					 doMove = true;
				 }
				 else if(x > 0.33f && x < 0.8f && y < -0.2f){
					 //DESNO DOL
					 posX = minigame.Cx + 1;
					 posY = minigame.Cy - 1;
					 doMove = true;
				 }
				else if(x < 0.33f && x > -0.33f && y < -0.2f){
					//NARAVNOST DOL
					posX = minigame.Cx;
					posY = minigame.Cy - 1;
					 doMove = true;
				}
				else if(x < -0.33f && x > -0.8f && y < -0.2f){
					//LEVO DOL
					posX = minigame.Cx - 1;
					posY = minigame.Cy - 1;
					 doMove = true;
				}
				else if(x < -0.8f && y < 0.33f && y > -0.33f){
					//LEVO
					posX = minigame.Cx - 1;
					posY = minigame.Cy;
					 doMove = true;
				}
				if(posX <= 7 && posX >= 0 && posY <= 7 && posY >= 0 && doMove){
					if((minigame.Cx == posX || minigame.Cx == posX+1 || minigame.Cx == posX-1) && (minigame.Cy == posY || minigame.Cy == posY+1 || minigame.Cy == posY-1)){
						if(minigame.zones[posX][posY] != 3 && minigame.zones[posX][posY] != 1 && minigame.zones[posX][posY] != 5) {
							//	if((minigame.Cx == y && minigame.Cy != x) || (minigame.Cx != y && minigame.Cy == x) || (minigame.Cx != y && minigame.Cy != x)) {
							if (minigame.Xx != -1) {
								minigame.zones[minigame.Xx][minigame.Xy] = 0;
							}
							if(minigame.zones[posX][posY] == 2){
								minigame.zones[posX][posY] = 5;
							}
							else{
								minigame.zones[posX][posY] = 4;
							}
							minigame.Xx = posX;
							minigame.Xy = posY;
							//	}
						}
					}

				}
			}
			/// PREGLEJ ČE JE KONEC IGRE
			boolean isNotOver = false;
			for(int x=0; x<8; x++) {
				for (int y = 0; y < 8; y++) {
					if(minigame.zones[x][y] == 2 || minigame.zones[x][y] == 5){
						isNotOver = true;
					}
				}
			}
			//if(isNotOver == true){

				//something

			//}
			if(!isNotOver && !minigame.hasEnded){
				minigame.isActive = false;
				//minigameOn = false;
				minigame.hasEnded = true;
				minigame.win = 1;
				for(int i=0; i<boys.length; i++){
					if(i+1 == minigame.boyID){
						boys[i].canDrop = false;
						boys[i].setSpeed(boys[i].getSpeed() * 2);
					}
				}
				score = score + 250 + 10 * minigame.dificulty;
				totalScore = score +  250 + 10 * minigame.dificulty;
				winSound.play();
			}
			if(minigame.timer <= 0) {
				minigame.win = -1;
				minigame.isActive = false;
				//minigameOn = false;
				error.play();
				minigame.hasEnded = true;
				for (int i = 0; i < boys.length; i++) {
					if (i + 1 == minigame.boyID) {
						boys[i].setSpeed(boys[i].getSpeed() * 2);
					}
				}
			}

		}

		if(!gameOn){
			if(Gdx.input.justTouched() && !win){
				Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
				camera.unproject(tmp);
				Rectangle resetBounds=new Rectangle(camera.position.x - 280,camera.position.y - 170, 181, 84);
				Rectangle menuBounds=new Rectangle(camera.position.x + 80,camera.position.y - 170, 176,77);
				if(resetBounds.contains(tmp.x,tmp.y))
				{
					all.setTotalTrashPickups(all.getTotalTrashPickups() + totalTrash - numberOfTrash);
					all.setGamesPlayed(all.getGamesPlayed() + 1);
					all.setGamesFailed(all.getGamesFailed() + 1);
					SaveLoad.saveState(all);
					dispose();
					game.setScreen(new Game(game));
				}
				if(menuBounds.contains(tmp.x, tmp.y)){
					all.setTotalTrashPickups(all.getTotalTrashPickups() + totalTrash - numberOfTrash);
					all.setGamesPlayed(all.getGamesPlayed() + 1);
					all.setGamesFailed(all.getGamesFailed() + 1);
					SaveLoad.saveState(all);
					dispose();
					game.setScreen(new MainMenuScreen(game));
				}
			}
			if(Gdx.input.justTouched() && win){
				Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
				camera.unproject(tmp);
				Rectangle resetBounds=new Rectangle(camera.position.x - 280,camera.position.y - 170, 181, 84);
				Rectangle menuBounds=new Rectangle(camera.position.x + 80,camera.position.y - 170, 176,77);
				Rectangle continueBounds=new Rectangle(camera.position.x - 175,camera.position.y - 80, 379,85);
				if(resetBounds.contains(tmp.x,tmp.y))
				{
					all.setGamesPlayed(all.getGamesPlayed() + 1);
					all.setTotalTrashPickups(all.getTotalTrashPickups() + totalTrash);
					SaveLoad.saveState(all);
					dispose();
					game.setScreen(new Game(game));
				}
				if(menuBounds.contains(tmp.x, tmp.y))
				{
					all.setGamesPlayed(all.getGamesPlayed() + 1);
					all.setTotalTrashPickups(all.getTotalTrashPickups() + totalTrash);
					all.setLvl(all.getLvl() + 1);
					all.setGold(all.getGold() + score);
					SaveLoad.saveState(all);
					dispose();
					game.setScreen(new MainMenuScreen(game));
				}
				if(continueBounds.contains(tmp.x, tmp.y))
				{
					all.setGamesPlayed(all.getGamesPlayed() + 1);
					all.setTotalTrashPickups(all.getTotalTrashPickups() + totalTrash);
					all.setLvl(all.getLvl() + 1);
					all.setGold(all.getGold() + score);
					SaveLoad.saveState(all);
					dispose();
					game.setScreen(new Game(game));
				}
			}
		}
		if(Gdx.input.justTouched() && gameOn) {
			Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(tmp);
			Rectangle xBounds = new Rectangle(camera.position.x + 470, camera.position.y + 260, 100, 100);
			if (xBounds.contains(tmp.x, tmp.y)) {
				all.setGamesAborted(all.getGamesAborted() + 1);
				SaveLoad.saveState(all);
				dispose();
				game.setScreen(new MainMenuScreen(game));
			}
		}
		// //////////////////////////////////////////////////////////7
	}
	
	@Override
	public void dispose () {
		background.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		music.dispose();
		batch.dispose();
		blueBin.dispose();
		redBin.dispose();
		yellowBin.dispose();
		greenBin.dispose();
		basicBin.dispose();
		tree.dispose();
		metalSound.dispose();
		plasticSound.dispose();
		paperSound.dispose();
		glassSound.dispose();
		background.dispose();
		bagImage.dispose();
		popUP.dispose();
		failText.dispose();
		winText.dispose();
		retry.dispose();
		menu.dispose();
		continuee.dispose();
		standing.dispose();
		x.dispose();
		font.dispose();
		inventoryFont.dispose();
		scoreFont.dispose();
		timerFont.dispose();
		touchpadSkin.dispose();
		stage.dispose();
	}

	private void spawnTrash() {
		Trash nov = smeti.get(rand.nextInt(16 - 0) + 0).create_clone();
		nov.setText(new Texture(Gdx.files.internal(nov.getImg())));
        counter = counter + 1;
		Rectangle smet = new Rectangle();
		smet.x = rand.nextInt((512*width - 250)-200)+200;
		smet.y = rand.nextInt((512*height - 250) - 105) + 105;
		for (Rectangle item:trees) {
			if(smet.overlaps(item)){
				smet.y = smet.y - 30;
			}
		}
		smet.width = nov.width;
		smet.height = nov.height;
		nov.setSmet(smet);
		totalScore = nov.getValue() + totalScore;
		vseSmeti.add(nov);
	}

	private void checkBoy(Badboy boy){
			if (boy.boy.y < 125 && boy.playSound == 2) { //125
				laugh.play();
				boy.playSound++;
				boy.isActive = false;
				numberOfTrash = numberOfTrash + boy.trashDroped;
				totalTrash += boy.trashDroped;
			}
			if (boy.boy.y > (height * 512) - 180 && boy.playSound == 2) { //145
				laugh.play();
				boy.playSound++;
				boy.isActive = false;
				numberOfTrash = numberOfTrash + boy.trashDroped;
				totalTrash += boy.trashDroped;
			}//1815, 233
			if (boy.boy.x < 65 && boy.playSound == 2) { //55
				laugh.play();
				boy.playSound++;
				boy.isActive = false;
				numberOfTrash = numberOfTrash + boy.trashDroped;
				totalTrash += boy.trashDroped;
			}
			if (boy.boy.x > (width * 512) - 140 && boy.playSound == 2) { //142
				laugh.play();
				boy.playSound++;
				boy.isActive = false;
				numberOfTrash = numberOfTrash + boy.trashDroped;
				totalTrash += boy.trashDroped;
			}
	}

	private void createTouchPad(){
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture(Gdx.files.internal("touchBackground.png")));
        touchpadSkin.add("touchKnob", new Texture(Gdx.files.internal("touchKnob.png")));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);
        viewport = new FitViewport(1200, 700, new OrthographicCamera() );
        stage = new Stage(viewport, batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    private void createBins(){
        int stevec = 150;
        for(int i=0; i<4; i++) {
            Rectangle bin = new Rectangle();
            bin.x = 100;
            bin.y = 600 + stevec;
            bin.width = 100;
            bin.height = 140;
            stevec = stevec + 250;
            bins.add(bin);
        }
        Rectangle bin = new Rectangle();
		bin.x = 600;
		bin.y = 150;
		bin.width = 100;
		bin.height = 140;
		bins.add(bin);
    }

    private void createTrash(){
		smeti.add(new Trash(21, 47, "pl_voda", 100, 1, "t1.png", TrashType.PLASTIC));
		smeti.add(new Trash(42, 57, "pl_mleko", 150, 2, "t2.png", TrashType.PLASTIC));
		smeti.add(new Trash(38, 46, "pl_čistolo", 200, 2, "t3.png", TrashType.PLASTIC));
		smeti.add(new Trash(30, 48, "pl_kozarec", 100, 1, "t4.png", TrashType.PLASTIC));
		smeti.add(new Trash(61, 32, "pa_pizza", 250, 3, "t5.png", TrashType.PAPER));
		smeti.add(new Trash(55, 51, "pa_box", 300, 3, "t6.png", TrashType.PAPER));
		smeti.add(new Trash(55, 31, "pa_paper", 250, 2, "t7.png", TrashType.PAPER));
		smeti.add(new Trash(38, 48, "pa_vrecka", 100, 1, "t8.png", TrashType.PAPER));
		smeti.add(new Trash(25, 37, "st_vrc", 150, 1, "t9.png", TrashType.GLASS));
		smeti.add(new Trash(20, 53, "st_pivo1", 180, 1, "t10.png", TrashType.GLASS));
		smeti.add(new Trash(18, 52, "st_pivo2", 180, 1, "t11.png", TrashType.GLASS));
		smeti.add(new Trash(31, 28, "st_loncek", 150, 1, "t12.png", TrashType.GLASS));
		smeti.add(new Trash(20, 36, "me_cola", 110, 1, "t13.png", TrashType.METAL));
		smeti.add(new Trash(25, 32, "me_spraj", 150, 1, "t14.png", TrashType.METAL));
		smeti.add(new Trash(18, 42, "me_konzerva1", 120, 1, "t15.png", TrashType.METAL));
		smeti.add(new Trash(25, 41, "me_konzerva2", 200, 2, "t16.png", TrashType.METAL));
	}

	private void narediDrevesa(){
		for (int i=0; i<trees.length; i++) {
			trees[i] = new Rectangle();
			trees[i].x = rand.nextInt(((512 * width) - 400)-400) + 400;
			trees[i].y = rand.nextInt(((512 * height) - 400)-400) + 400;
			trees[i].height = 67;
			trees[i].width = 65;
			for(int j=0; j<i; j++){
				if(trees[i].overlaps(trees[j]) && i!=j ){
					trees[i].x = trees[i].x + 150;
				}
			}
		}
		int i, j;
		for (i = 0; i < trees.length-1; i++) {
			for (j = 0; j < trees.length - i - 1; j++) {
				if (trees[j].y < trees[j + 1].y) {
					swap = trees[j];
					trees[j] = trees[j + 1];
					trees[j + 1] = swap;
				}
			}
		}
	}

	private void dajVKos(int b, TrashType t, boolean isFull){
		zvok = false;
		if(bucket.overlaps(bins.get(b)) && !isFull && !reciklira){
			reciklira = true;
			for (int i=Inventory.size-1; i>=0; i--) {
				if (Inventory.get(i).getType() == t) {
					score = score + Inventory.get(i).getValue();
					currentInventory = currentInventory - Inventory.get(i).getWeight();
					Inventory.removeIndex(i);
					zvok = true;
					wait = 20;
				}
			}
		}
		if(bucket.overlaps(bins.get(b)) && b == 4 && !reciklira){
			if(currentInventory > 0) {
				zvok = true;
				reciklira = true;
				wait = 40;
			}
			for (int i=Inventory.size-1; i>=0; i--) {
				score = score + Inventory.get(i).getValue() / 2;
				currentInventory = currentInventory - Inventory.get(i).getWeight();
				Inventory.removeIndex(i);
			}
			if(zvok)
				crackSound.play();
		}
		else if(zvok){
			if(t == TrashType.GLASS)
				glassSound.play();
			else if(t == TrashType.METAL)
				metalSound.play();
			else if(t == TrashType.PLASTIC)
				plasticSound.play();
			else if(t == TrashType.PAPER)
				paperSound.play();
			if(rand.nextInt(100 - 0) + 0 < binChance){
				booleanBins[b] = true;
				if(b == 0)
					blueBin = new Texture(Gdx.files.internal("blueBin2.png"));
				else if(b == 1)
					redBin = new Texture(Gdx.files.internal("redBin2.png"));
				else if(b == 2)
					greenBin = new Texture(Gdx.files.internal("greenBin2.png"));
				else if(b == 3)
					yellowBin = new Texture(Gdx.files.internal("orangeBin2.png"));
			}
		}
		reciklira = false;
	}

	private void CreateCustomRegions(Ozadje o){
		int x = 0;
		int y = 0;
		o.setTexture(0, "background_images/BottomLeft.png");
		o.setxPos(0, x);
		o.setyPos(0, y);
		o.setTexture(1, "background_images/TopLeft.jpg");
		o.setxPos(1, x);
		o.setyPos(1, (512 * height) - 512);
		o.setTexture(2, "background_images/BottomRight.jpg");
		o.setxPos(2, (512 * width) - 512);
		o.setyPos(2, y);
		o.setTexture(3, "background_images/TopRight.jpg");
		o.setxPos(3, (512 * width) - 512);
		o.setyPos(3, (512 * height) - 512);
		int ii = 4;
		x = 512;
		int r;
		//Ograja na dnu
		for(int i = 0; i<width-2; i++){
			r = rand.nextInt(6-1) + 1;
			o.setTexture(ii, "background_images/BottomFence" + r + ".jpg");
			o.setxPos(ii, x);
			o.setyPos(ii, y);
			x = x + 512;
			ii++;
		}
		x = 512;
		for(int i = 0; i<width-2; i++){
			r = rand.nextInt(6-1) + 1;
			o.setTexture(ii,"background_images/TopFence" + r + ".jpg");
			o.setxPos(ii, x);
			o.setyPos(ii, (512 * height) - 512);
			x = x + 512;
			ii++;
		}
		x = 0;
		y = 512;
		for(int i = 0; i<height-2; i++){
			r = rand.nextInt(6-1) + 1;
			o.setTexture(ii, "background_images/LeftFence" + r + ".jpg");
			o.setxPos(ii, x);
			o.setyPos(ii, y);
			y = y + 512;
			ii++;
		}
		y = 512;
		for(int i = 0; i<height-2; i++){
			r = rand.nextInt(6-1) + 1;
			o.setTexture(ii, "background_images/RightFence" + r + ".jpg");
			o.setxPos(ii,(512 * width) - 512);
			o.setyPos(ii, y);
			y = y + 512;
			ii++;
		}
		y = 0;
		for(int i=0; i<height-2; i++){
			x = 512;
			y = y + 512;
			for(int j=0; j<width-2; j++){
				r = rand.nextInt(51-1) + 1;
				o.setTexture(ii, "background_images/Box" + r + ".jpg");
				o.setxPos(ii, x);
				o.setyPos(ii, y);
				x = x + 512;
				ii++;
			}
		}
	}

	private void createRegions(Ozadje o){
		int x = 0;
		int y = 0;
		for(int i=0; i<32; i++){
			o.setXRegion(i, new TextureRegion(background,x,y, 512, 512));
            o.setxPos(i, x);
            o.setyPos(i, 1536 - y);
			x = x + 512;
			if(x == 4096){
				y = y + 512;
				x = 0;
			}
		}
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		music.play();
	}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}



}
