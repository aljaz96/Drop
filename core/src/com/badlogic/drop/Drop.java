package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Drop extends ApplicationAdapter {
	private Texture trashImage;
	private Texture bucketImage;
    private Texture background;
    private Texture blueBin;
    private Texture redBin;
    private Texture greenBin;
    private Texture yellowBin;
	private Texture basicBin;
	private Texture tree;
	private Sound dropSound;
	private Sound paperSound;
	private Sound metalSound;
	private Sound plasticSound;
	private Sound glassSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Rectangle bucket;
	private Rectangle tree1;
	private Rectangle tree2;
	private Rectangle tree3;

	//POLJA Z SMETMI/KOŠIM/SMETEH NA TLEH
	private Array<Trash> vseSmeti;
    private Array<Rectangle> bins;
	private boolean[] booleanBins = new boolean[4];
	private Array<Trash> smeti;

	//INVENTORY
	private Array<Trash> Inventory;
	private int inventorySize = 10;
	private int currentInventory = 0;
	private int score = 0;

	//private long lastDropTime;

    ////////// MOJE
    private Viewport viewport;
    private long timer;
    private int counter;
    private int advance;
	private int numberOfTrash;
	private BitmapFont font;
	private BitmapFont inventoryFont;
	private BitmapFont scoreFont;
	private BitmapFont timerFont;
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
	private float speed;
    private Sprite player;
	float oldX;
	float oldY;
	boolean zvok;
    private Animation<TextureRegion> walkAnimation;
    private Texture[] walkSheet;
	Random rand;
	private int binChance = 30;
    /////////
	
	@Override
	public void create () {
		rand = new Random();
		font = new BitmapFont();
		inventoryFont = new BitmapFont();
		scoreFont = new BitmapFont();
		timerFont = new BitmapFont();

        background = new Texture(Gdx.files.internal("background2.png"));
		//trashImage = new Texture(Gdx.files.internal("t1.png"));
		bucketImage = new Texture(Gdx.files.internal("Hat_man1.png"));
        blueBin = new Texture(Gdx.files.internal("blueBin.png"));
        redBin = new Texture(Gdx.files.internal("redBin.png"));
        greenBin = new Texture(Gdx.files.internal("greenBin.png"));
        yellowBin = new Texture(Gdx.files.internal("orangeBin.png"));
		basicBin = new Texture(Gdx.files.internal("basicBin.png"));
		tree = new Texture(Gdx.files.internal("tree.png"));

		// load the sound effects and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		paperSound = Gdx.audio.newSound(Gdx.files.internal("papir.mp3"));
		glassSound = Gdx.audio.newSound(Gdx.files.internal("steklo.mp3"));
		metalSound = Gdx.audio.newSound(Gdx.files.internal("kovina.mp3"));
		plasticSound = Gdx.audio.newSound(Gdx.files.internal("plastika.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

      //... more to come ...
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 160;
		bucket.y = 160;
		bucket.width = 64;
		bucket.height = 90;

		//////////// MOJE
		speed = 8;
        timer = 1000000000;
        counter = 0;
        //advance = 10;
		//ŠTEVILO ODPADKOV
		numberOfTrash = 20;
		//////////////////
		font.getData().setScale(1.5f,1.5f);
		inventoryFont.getData().setScale(1.5f, 1.5f);
		scoreFont.getData().setScale(1.5f, 1.5f);
		timerFont.getData().setScale(1.2f, 1.2f);
		camera.viewportWidth = 1200;
		camera.viewportHeight = 700;
		//float aspectRation = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
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
	public void render () {
		oldX = bucket.x;
		oldY = bucket.y;
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//litterText = "Litter left: " + numberOfTrash;
		litterText = "Litter left: " + numberOfTrash;
		inventoryText = "Inventory space: " + currentInventory + "/" + inventorySize;
		scoreText = "Score: " + score;
		if(currentInventory == inventorySize){ inventoryText = "Inventory space: FULL"; }
		camera.position.x = bucket.getX() + 64 / 2;
		camera.position.y = bucket.getY() + 90 / 2;
		if(camera.position.x < 605){ camera.position.x = 605;}
		if(camera.position.x > 3480){ camera.position.x = 3480;}
		if(camera.position.y < 350){ camera.position.y = 350;}
		if(camera.position.y > 1680){ camera.position.y = 1680;}
		camera.update();

        batch.setProjectionMatrix(camera.combined);

		bucket.setX(bucket.getX() + touchpad.getKnobPercentX()*speed);
		bucket.setY(bucket.getY() + touchpad.getKnobPercentY()*speed);

		batch.begin();
		//ZAČETEK RISANJA NA ZASLON
        batch.draw(background,0,0);  //OZADJE
        int counter = 0;
		//KOŠI ZA SMETI
        for(Rectangle bin: bins){
            if(counter == 0)
                batch.draw(blueBin, bin.x, bin.y);
            if(counter == 1)
                batch.draw(redBin, bin.x, bin.y);
            if(counter == 2)
                batch.draw(greenBin, bin.x, bin.y);
            if(counter == 3)
                batch.draw(yellowBin, bin.x, bin.y);
			if(counter == 4)
				batch.draw(basicBin, bin.x, bin.y);
            counter++;
        }
        //SMETI
        for(Trash t: vseSmeti) {
			trashImage = new Texture(Gdx.files.internal(t.getImg()));
            batch.draw(trashImage, t.getSmet().x, t.getSmet().y);
        }
        //IGRALEC
		batch.draw(bucketImage, bucket.x, bucket.y);
		//DREVESA
		batch.draw(tree, tree1.x, tree1.y);
		batch.draw(tree, tree2.x, tree2.y);
		batch.draw(tree, tree3.x, tree3.y);
		//BESEDILA
		font.draw(batch, litterText, camera.position.x - 500, camera.position.y + 330);
		inventoryFont.draw(batch, inventoryText, camera.position.x + 350, camera.position.y + 330);
		scoreFont.draw(batch, scoreText, camera.position.x - 50, camera.position.y + 330);
		//KONEC RISANJA NA ZASLON
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 66 * speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 66 * speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 66 * speed * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 66 * speed * Gdx.graphics.getDeltaTime();

		if(bucket.y < 105) bucket.y = 105;
		if(bucket.y > 1815 ) bucket.y = 1815;
        if(bucket.x < 60) bucket.x = 60;
        if(bucket.x > 3950 ) bucket.x = 3950;

		//Onemogočitev zaletavanja v drevo
		if(bucket.x > 1810 && bucket.x < 2005 && bucket.y < 1247 && bucket.y > 1115){
			bucket.x = oldX;
			bucket.y = oldY;
		}
		if(bucket.x > 1375 && bucket.x < 1571 && bucket.y < 609 && bucket.y > 450){
			bucket.x = oldX;
			bucket.y = oldY;
		}
		if(bucket.x > 3386 && bucket.x < 3572 && bucket.y < 1560 && bucket.y > 1401){
			bucket.x = oldX;
			bucket.y = oldY;
		}
		Iterator<Trash> iter = vseSmeti.iterator();
		while(iter.hasNext()) {
			Trash trash = iter.next();
			if(trash.getSmet().overlaps(bucket)) {
				if(currentInventory + trash.getWeight() <= inventorySize) {
					Inventory.add(trash);
					currentInventory = currentInventory + trash.getWeight();
					dropSound.play();
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



	}
	
	@Override
	public void dispose () {
		trashImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		blueBin.dispose();
		redBin.dispose();
		yellowBin.dispose();
		greenBin.dispose();
		tree.dispose();
		metalSound.dispose();
		plasticSound.dispose();
		paperSound.dispose();
		glassSound.dispose();
		background.dispose();
	}

	private void spawnTrash() {
		Trash nov = smeti.get(rand.nextInt(16 - 0) + 0).create_clone();
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

    private void ustvariAnimacijo(){
        walkSheet[0] = new Texture(Gdx.files.internal("animation_sheet.png"));
    }

    private void createBins(){
        int stevec = 150;
        for(int i=0; i<4; i++) {
            Rectangle bin = new Rectangle();
            bin.x = 100;
            bin.y = 600 + stevec;
            bin.width = 140;
            bin.height = 140;
            stevec = stevec + 150;
            bins.add(bin);
        }
        Rectangle bin = new Rectangle();
		bin.x = 600;
		bin.y = 150;
		bin.width = 140;
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
		tree1.width = 166;
		tree1.height = 237;
		tree2.x = 1430;
		tree2.y = 520;
		tree2.width = 166;
		tree2.height = 237;
		tree3.x = 3440;
		tree3.y = 1480;
		tree3.width = 166;
		tree3.height = 237;
	}

	private void dajVKos(int b, TrashType t, boolean isFull){
		zvok = false;
		if(bucket.overlaps(bins.get(b)) && !isFull){
			for (int i=0; i<Inventory.size; i++) {
				if (Inventory.get(i).getType() == t) {
					score = score + Inventory.get(i).getValue();
					currentInventory = currentInventory - Inventory.get(i).getWeight();
					Inventory.removeIndex(i);
					zvok = true;
				}
			}
		}
		if(bucket.overlaps(bins.get(b)) && b == 4){
			for (int i=0; i<Inventory.size; i++) {
				score = score + Inventory.get(i).getValue() / 2;
				currentInventory = currentInventory - Inventory.get(i).getWeight();
				Inventory.removeIndex(i);
			}
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
	}
}
