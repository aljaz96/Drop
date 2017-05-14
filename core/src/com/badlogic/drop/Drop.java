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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

public class Drop extends ApplicationAdapter {
	private Texture dropImage;
	private Texture bucketImage;
    private Texture background;
    private Texture blueBin;
    private Texture redBin;
    private Texture greenBin;
    private Texture yellowBin;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Rectangle bucket;

	private Array<Rectangle> raindrops;
    private Array<Rectangle> bins;
	private long lastDropTime;

    ////////// MOJE
    private Viewport viewport;
    private long timer;
    private int counter;
    private int advance;
	private int numberOfTrash;
	private BitmapFont font;
	private String litterText;
	private Touchpad touchpad;
	private Skin touchpadSkin;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private Stage stage;
	private float speed;
    private Sprite player;

    private Animation<TextureRegion> walkAnimation;
    private Texture[] walkSheet;
    /////////
	
	@Override
	public void create () {
		// load the images for the droplet and the bucket, 64x64 pixels each
		font = new BitmapFont();
        background = new Texture(Gdx.files.internal("background2.png"));
		dropImage = new Texture(Gdx.files.internal("can.png"));
		bucketImage = new Texture(Gdx.files.internal("Hat_man1.png"));
        blueBin = new Texture(Gdx.files.internal("blueBin.png"));
        redBin = new Texture(Gdx.files.internal("redBin.png"));
        greenBin = new Texture(Gdx.files.internal("greenBin.png"));
        yellowBin = new Texture(Gdx.files.internal("orangeBin.png"));



                // load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

      //... more to come ...
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 90;

        //////////// MOJE
		speed = 3;
        timer = 1000000000;
        counter = 0;
        advance = 10;
		numberOfTrash = 10;
		litterText = "Litter left: " + numberOfTrash;
		font.getData().setScale(1.5f,1.5f);
		camera.viewportWidth = 1200;
		camera.viewportHeight = 700;
		//float aspectRation = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        createTouchPad();

        bins = new Array<Rectangle>();
        createBins();
		raindrops = new Array<Rectangle>();
		//spawnRaindrop();
		for(int i=0; i<numberOfTrash; i++){
			spawnRaindrop();
		}
	}

	//dodaj spreminjajoče kante/inventory/nove smeti/ mogoče drevesa/mogoče ozadaje poštimati

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ... more to come here ...
		litterText = "Litter left: " + numberOfTrash;
		camera.position.x = bucket.getX() + 64 / 2;
		camera.position.y = bucket.getY() + 90 / 2;
		camera.update();

        batch.setProjectionMatrix(camera.combined);

		bucket.setX(bucket.getX() + touchpad.getKnobPercentX()*speed);
		bucket.setY(bucket.getY() + touchpad.getKnobPercentY()*speed);

		batch.begin();
        batch.draw(background,0,0);
        batch.draw(bucketImage, bucket.x, bucket.y);
        int counter = 0;
        for(Rectangle bin: bins){
            if(counter == 0)
                batch.draw(blueBin, bin.x, bin.y);
            if(counter == 1)
                batch.draw(redBin, bin.x, bin.y);
            if(counter == 2)
                batch.draw(greenBin, bin.x, bin.y);
            if(counter == 3)
                batch.draw(yellowBin, bin.x, bin.y);
            counter++;
        }
        for(Rectangle raindrop: raindrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
		font.draw(batch, litterText, bucket.getX() - 500, bucket.getY() + 350);

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

		/*if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
			bucket.y = touchPos.y - 64 / 2;
		}*/

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) bucket.y += 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) bucket.y -= 200 * Gdx.graphics.getDeltaTime();

		if(bucket.y < 370) bucket.y = 370;
		if(bucket.y > 2206 ) bucket.y = 2206;
        if(bucket.x < 660) bucket.x = 660;
        if(bucket.x > 4150 ) bucket.x = 4150;

		//if(TimeUtils.nanoTime() - lastDropTime > timer) spawnRaindrop();

		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
		//	raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
		//	if(raindrop.y + 64 < 0) iter.remove();

			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
				numberOfTrash--;
			}
		}

	}
	
	@Override
	public void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	private void spawnRaindrop() {
        counter = counter + 1;
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(660, 4150);
		raindrop.y = MathUtils.random(370, 2206);
		raindrop.width = 40;
		raindrop.height = 50;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
        if(counter == 10 && timer != 100000000){
            advance = advance + 10;
            counter = 0;
            timer = timer - 100000000;
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

    private void ustvariAnimacijo(){
        walkSheet[0] = new Texture(Gdx.files.internal("animation_sheet.png"));
    }

    private void createBins(){
        int stevec = 150;
        for(int i=0; i<4; i++) {
            Rectangle bin = new Rectangle();
            bin.x = 700;
            bin.y = 500 + stevec;
            bin.width = 140;
            bin.height = 140;
            stevec = stevec + 150;
            bins.add(bin);
        }
    }
}
