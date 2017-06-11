package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen implements com.badlogic.gdx.Screen {

    final Screen game;
    OrthographicCamera camera;
    public Animation<TextureRegion> runningAnimation;
    Texture walk;
    Texture background;
    Texture name;
    Texture bin;


    Texture play;
    float playHeight = 75;
    float playWidth = 160;
    Texture shop;
    float shopHeight = 103 - (130 / 8);
    float shopWidth = 202 - (202 / 8);
    Texture stats;
    float statsHeigh = 73 - (73 / 8);
    float statsWidth = 214 - (214 / 8);
    Texture reset;
    float resetHeigh = 71 - (71 / 8);
    float resetWidth = 218 - (218 / 8);
    Texture exit;
    float exitHeight = 78 - (78 / 8);
    float exitWidth = 168 - (168 / 8);
    TextureRegion playRegion;
    TextureRegionDrawable playDrawable;
    ImageButton playButton;
    int counter = 0;

    Stage stage;
    TextureRegion currentFrame;
    Music music;
    float stateTime;
    DataAll all;

    public MainMenuScreen(final Screen game) {

        this.game = game;

        music = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.mp3"));
        background = new Texture(Gdx.files.internal("Test.png"));
        name = new Texture(Gdx.files.internal("name.png"));
        bin = new Texture(Gdx.files.internal("t5.png"));
        walk = new Texture(Gdx.files.internal("Hat_man.png"));
        play = new Texture(Gdx.files.internal("play.png"));
        shop = new Texture(Gdx.files.internal("shop.png"));
        stats = new Texture(Gdx.files.internal("stats.png"));
        reset = new Texture(Gdx.files.internal("reset.png"));
        exit = new Texture(Gdx.files.internal("exit.png"));
        playRegion = new TextureRegion(play);
        playDrawable = new TextureRegionDrawable(playRegion);
        playButton = new ImageButton(playDrawable);
        playButton.addListener(new EventListener()
        {
            @Override
            public boolean handle(Event event)
            {
                game.setScreen(new Game(game));
                dispose();
                return true;
            }
        });

        all = SaveLoad.loadState();
        //System.out.println(all.getGold());




       // game.stage = new Stage(new ScreenViewport());
       // game.stage.addActor(playButton);
        Gdx.input.setInputProcessor(game.stage);

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
        runningAnimation = new Animation<TextureRegion>(0.2f, walkFrames);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stateTime = 0f;

       // if(dataall.getLvl() == 0){
       //     dataall.scenarijA();
       // }

        music.setLooping(true);
        music.play();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = runningAnimation.getKeyFrame(stateTime, true);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //game.stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        //game.stage.draw();

        game.batch.begin();
            game.batch.draw(background, 0, 0);
            game.batch.draw(name, 150, 330);
            game.batch.draw(currentFrame, 50, 330);
            game.batch.draw(bin, 700, 340);
            game.batch.draw(play, 330, 200, playWidth, playHeight);
            game.batch.draw(shop, 100,120, shopWidth, shopHeight);
            game.batch.draw(stats, 520,130, statsWidth, statsHeigh);
            game.batch.draw(reset, 50,50, resetWidth, resetHeigh);
            game.batch.draw(exit, 600,50, exitWidth, exitHeight);
            //game.font.draw(game.batch, "Touch mah screen!!!", 200, 100);
        game.batch.end();


        if(Gdx.input.isTouched()){
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);
            Rectangle playBounds=new Rectangle(330,200,playWidth,playHeight);
            Rectangle exitBounds=new Rectangle(600,50,exitWidth,exitHeight);
            Rectangle resetBounds=new Rectangle(330,200,playWidth,playHeight);
            if(playBounds.contains(tmp.x,tmp.y))
            {
                game.setScreen(new Game(game));
                dispose();
            }
            if(exitBounds.contains(tmp.x, tmp.y)){
                dispose();
                System.exit(0);
            }
        }

        if(counter == 80){
            counter = 0;
        }
        if(counter >= 0 && counter < 40){
            increaseText();
        }
        if(counter >= 40 && counter < 80){
            decreseText();
        }
        counter++;

       // if (Gdx.input.isTouched()) {
        //    game.setScreen(new Game(game));
        //    dispose();
       // }
    }

    public void dispose () {
        walk.dispose();
        music.dispose();
        background.dispose();
        name.dispose();
        bin.dispose();
//        game.stage.dispose();
    }

    public void show() {

    }

    public void hide() {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void resize(int width, int height) {
    }

    public void increaseText(){
        playHeight = playHeight + 0.15f;
        playWidth = playWidth + 0.15f;
        shopHeight = shopHeight - 0.1f;
        shopWidth = shopWidth - 0.1f;
        statsWidth = statsWidth - 0.1f;
        statsHeigh = statsHeigh - 0.1f;
        resetWidth = resetWidth + 0.1f;
        resetHeigh = resetHeigh + 0.1f;
        exitHeight = exitHeight + 0.1f;
        exitWidth = exitWidth + 0.1f;
    }

    public void decreseText(){
        playHeight = playHeight - 0.15f;
        playWidth = playWidth - 0.15f;
        shopHeight = shopHeight + 0.1f;
        shopWidth = shopWidth + 0.1f;
        statsWidth = statsWidth + 0.1f;
        statsHeigh = statsHeigh + 0.1f;
        resetWidth = resetWidth - 0.1f;
        resetHeigh = resetHeigh - 0.1f;
        exitHeight = exitHeight - 0.1f;
        exitWidth = exitWidth - 0.1f;
    }

}