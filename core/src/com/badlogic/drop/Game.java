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

import java.util.Iterator;
import java.util.Random;

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
	private Texture x;
    //////////////////////////////////
	//////// ZVOKI /////////////////
	private Sound crackSound;
	private Sound dropSound;
	private Sound paperSound;
	private Sound metalSound;
	private Sound plasticSound;
	private Sound glassSound;
	private Music music;
	private OrthographicCamera camera;
	private SpriteBatch batch;
    ////////////////////////////////
	////// RECTANGLI ///////////////
	private Rectangle bucket;
	private Rectangle tree1;
	private Rectangle tree2;
	private Rectangle tree3;
	private Rectangle bag;
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
	/////// INT/DOUBLE
    private int counter;
	private int numberOfTrash;
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
	boolean win;
    //// MY CLASSES
    Ozadje ozadje;
	DataAll all;

	public Game(final Screen gam) {
		this.game = gam;
		all = SaveLoad.loadState();
		time = all.getTime();
		inventorySize = all.getInventorySpace();
		speed = all.getSpeed() * 5;

		gameOn = true;
		win = false;
		rand = new Random();
		font = new BitmapFont();
		inventoryFont = new BitmapFont();
		scoreFont = new BitmapFont();
		timerFont = new BitmapFont();

        background = new Texture(Gdx.files.internal("background2.png"));
		bucketImage = new Texture(Gdx.files.internal("Hat_man1.png"));
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
		standing = new Texture(Gdx.files.internal("Hat_man.png"));
		x = new Texture(Gdx.files.internal("x.png"));
		ozadje = new Ozadje();
		createRegions(ozadje);

		// load the sound effects and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		music = Gdx.audio.newMusic(Gdx.files.internal("gameTheme.mp3"));
		paperSound = Gdx.audio.newSound(Gdx.files.internal("papir.mp3"));
		glassSound = Gdx.audio.newSound(Gdx.files.internal("steklo.mp3"));
		metalSound = Gdx.audio.newSound(Gdx.files.internal("kovina.mp3"));
		plasticSound = Gdx.audio.newSound(Gdx.files.internal("plastika.mp3"));
		crackSound = Gdx.audio.newSound(Gdx.files.internal("crackSound.mp3"));

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

        createTouchPad();

        bins = new Array<Rectangle>();
		smeti = new Array<Trash>();
		vseSmeti = new Array<Trash>();
		Inventory = new Array<Trash>();
		createBins();
		for(int i=0; i<4; i++){
			booleanBins[i] = false;
		}
		createTrash();
		//spawnRaindrop();
		for(int i=0; i<numberOfTrash; i++){
			spawnTrash();
		}
		//DREVESA
		narediDrevesa();
	}

	//dodaj spreminjajoče kante/inventory/nove smeti/ mogoče drevesa/mogoče ozadaje poštimati

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		if(gameOn) {
			float deltaTime = Gdx.graphics.getDeltaTime();
			time -= deltaTime;
			if(time <= 0){
				gameOn = false;
				win = false;
			}
		}

		if(gameOn && numberOfTrash == 0 && currentInventory == 0){
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



		//litterText = "Litter left: " + numberOfTrash;
		litterText = "Litter left: " +  numberOfTrash;
		timerText = "Time left: " + String.format("%d",(long)time);
		inventoryText = " " + currentInventory + "/" + inventorySize;
		scoreText = "Score: " + score;
		if(currentInventory == inventorySize){ inventoryText = " FULL"; }
		camera.position.x = bucket.getX() + 64 / 2;
		camera.position.y = bucket.getY() + 90 / 2;
		if(camera.position.x < 605){ camera.position.x = 605;}
		if(camera.position.x > 3480){ camera.position.x = 3480;}
		if(camera.position.y < 350){ camera.position.y = 350;}
		if(camera.position.y > 1680){ camera.position.y = 1680;}
		camera.update();

        batch.setProjectionMatrix(camera.combined);

		batch.begin();
		//ZAČETEK RISANJA NA ZASLON:
            //OZADJE
            for(int i=0; i<32; i++){
                if(Ypos + 650 > (ozadje.getyPos(i) + 270) && Ypos - 650 < ozadje.getyPos(i) + 270 &&
                        Xpos + 1200 > (ozadje.getxPos(i) + 580) && Xpos - 600 < ozadje.getxPos(i) + 580) {
                    batch.draw(ozadje.getXRegion(i), ozadje.getxPos(i), ozadje.getyPos(i));
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
			//IGRALEC
			batch.draw(playerImage, playerDirection ? bucket.x+100 : bucket.x, bucket.y, playerDirection ? -100 : 100, 100);
			//spriteBatch.draw(currentFrame, flip ? x+width : x, y, flip ? -width : width, height);
			//DREVESA
			batch.draw(tree, tree1.x, tree1.y);
			batch.draw(tree, tree2.x, tree2.y);
			batch.draw(tree, tree3.x, tree3.y);
			//BESEDILA
			batch.draw(bagImage, camera.position.x + 300, camera.position.y + 290);
			font.draw(batch, litterText, camera.position.x - 500, camera.position.y + 330);
			inventoryFont.draw(batch, inventoryText, camera.position.x + 360, camera.position.y + 325);
			scoreFont.draw(batch, scoreText, camera.position.x - 50, camera.position.y + 290);
			scoreFont.draw(batch, timerText, camera.position.x - 50, camera.position.y + 330);
			batch.draw(x, camera.position.x + 470, camera.position.y + 260, 100, 100);
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
			//KONEC RISANJA NA ZASLON
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

		if(wait <= 0 && gameOn) {
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
		}
		if(gameOn) {
			if (bucket.y < 105) bucket.y = 105;
			if (bucket.y > 1815) bucket.y = 1815;
			if (bucket.x < 60) bucket.x = 60;
			if (bucket.x > 3950) bucket.x = 3950;

			//Onemogočitev zaletavanja v drevo
			if (tree1.overlaps(bucket) || tree2.overlaps(bucket) || tree3.overlaps(bucket)) {
				bucket.x = oldX;
				bucket.y = oldY;
			}

			//if(bucket.x > 1810 && bucket.x < 2005 && bucket.y < 1247 && bucket.y > 1115){
			//	bucket.x = oldX;
			//	bucket.y = oldY;
			//}
			//if(bucket.x > 1375 && bucket.x < 1571 && bucket.y < 609 && bucket.y > 450){
			//	bucket.x = oldX;
			//	bucket.y = oldY;
			//}
			//if(bucket.x > 3386 && bucket.x < 3572 && bucket.y < 1560 && bucket.y > 1401){
			//	bucket.x = oldX;
			//	bucket.y = oldY;
			//}


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
		if(!gameOn){
			if(Gdx.input.isTouched() && !win){
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
			if(Gdx.input.isTouched() && win){
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
		if(Gdx.input.isTouched() && gameOn) {
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
		smet.x = rand.nextInt(3950-200)+200;
		smet.y = rand.nextInt(1815 - 105) + 105;
		if(smet.x > 1810 && smet.x < 2005 && smet.y < 1247 && smet.y > 1115){
			smet.y = smet.y - 30;
		}
		if(smet.x > 1375 && smet.x < 1571 && smet.y < 609 && smet.y > 450){
			smet.y = smet.y - 30;
		}
		if(smet.x > 3386 && smet.x < 3572 && smet.y < 1560 && smet.y > 1401){
			smet.y = smet.y - 30;
		}
		smet.width = nov.width;
		smet.height = nov.height;
		nov.setSmet(smet);
		totalScore = nov.getValue() + totalScore;
		vseSmeti.add(nov);
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
		tree1 = new Rectangle();
		tree2 = new Rectangle();
		tree3 = new Rectangle();
		tree1.x = 1870;
		tree1.y = 1170;
		tree1.width = 130;
		tree1.height = 130;
		tree2.x = 1430;
		tree2.y = 520;
		tree2.width = 130;
		tree2.height = 130;
		tree3.x = 3440;
		tree3.y = 1480;
		tree3.width = 130;
		tree3.height = 130;
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
