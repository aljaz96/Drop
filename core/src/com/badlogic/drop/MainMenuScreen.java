package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
    Texture popUP;
    Texture resetProgress;
    Texture resetWarning;
    Texture yes;
    Texture no;
    Texture increaseSpeed;
    Texture increaseBagSize;
    Texture increaseTimer;
    Texture plus;
    Texture money;
    Texture x;

    int counter = 0;

    Stage stage;
    TextureRegion currentFrame;
    Music music;
    float stateTime;
    DataAll all;
    ShaderProgram shaderProgram;
    ShaderProgram blurProgram;
    boolean useGrayScale;

    boolean shopScreen;
    boolean resetScreen;
    boolean statsScreen;
    boolean menuScreen;

    private BitmapFont font;

    public MainMenuScreen(final Screen game) {

        this.game = game;
        menuScreen = true;
        font = new BitmapFont();
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
        popUP = new Texture(Gdx.files.internal("popUp.png"));
        resetWarning = new Texture(Gdx.files.internal("progress_will_be_lost.png"));
        resetProgress = new Texture(Gdx.files.internal("reset_progress.png"));
        no = new Texture(Gdx.files.internal("no.png"));
        yes = new Texture(Gdx.files.internal("yes.png"));
        increaseBagSize = new Texture(Gdx.files.internal("current_bag.png"));
        increaseSpeed = new Texture(Gdx.files.internal("current_speed.png"));
        increaseTimer = new Texture(Gdx.files.internal("current_time.png"));
        plus =  new Texture(Gdx.files.internal("plus.png"));
        money =  new Texture(Gdx.files.internal("money.png"));
        x =  new Texture(Gdx.files.internal("x.png"));
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
        String vertexShader = Gdx.files.internal("vertex.glsl").readString();
        String fragmentShader = Gdx.files.internal("fragment_grayscale.glsl").readString();
        String blurShader = Gdx.files.internal("fragment_blur.glsl").readString();
        ShaderProgram.pedantic = false;
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        System.out.println(ShaderProgram.COLOR_ATTRIBUTE);
        blurProgram = new ShaderProgram(vertexShader,blurShader);
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

        font.getData().setScale(1.5f);
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
        game.batchOptions.setProjectionMatrix(camera.combined);

        //game.stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        //game.stage.draw();

        game.batch.begin();
            //Nastavi-odstrani grayShader
            //game.batch.setShader(useGrayScale ? blurProgram : null);
            game.batch.setShader(useGrayScale ? shaderProgram : null);
            ////
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

        game.batchOptions.begin();
        //////////////////// SHOP/STATS/RESET
            if(resetScreen == true){
                game.batchOptions.draw(popUP, camera.position.x - 300f, camera.position.y - 180, popUP.getWidth() * 1.2f, popUP.getHeight() * 1.2f);
                game.batchOptions.draw(resetProgress, camera.position.x - 265f, camera.position.y + 70, resetProgress.getWidth() * 0.8f, resetProgress.getHeight() * 0.8f);
                game.batchOptions.draw(resetWarning, camera.position.x - 260f, camera.position.y + 30, resetWarning.getWidth() * 0.55f, resetWarning.getHeight() * 0.55f);
                game.batchOptions.draw(no, camera.position.x + 50f, camera.position.y - 100);
                game.batchOptions.draw(yes, camera.position.x - 210f, camera.position.y - 100);
            }
            if(shopScreen == true){
                int value = (int)((all.getSpeed() - 0.95f) * 6000);
                float mySpeed = all.getSpeed();
                mySpeed = Math.round(mySpeed * 100);
                mySpeed = mySpeed / 100;
                while(value % 100 != 0){
                    value++;
                }

                String bagString = all.getInventorySpace() + "       " + ((all.getInventorySpace() - 5) * 400) + "$";
                String speedString = mySpeed + "       " + value + "$";
                String timeString = all.getTime() + "       " + ((all.getTime() - 80) * 100) + "$";
                String moneyString = all.getGold() + "$ ";
                game.batchOptions.draw(popUP, camera.position.x - 360f, camera.position.y - 180, popUP.getWidth() * 1.5f, popUP.getHeight() * 1.2f);
                game.batchOptions.draw(x, camera.position.x + 240f, camera.position.y + 90);
                game.batchOptions.draw(money, camera.position.x - 300f, camera.position.y + 85, money.getWidth() * 0.50f, money.getHeight() * 0.50f);
                game.batchOptions.draw(increaseBagSize, camera.position.x - 300f, camera.position.y, increaseBagSize.getWidth() * 0.50f, increaseBagSize.getHeight() * 0.50f);
                game.batchOptions.draw(increaseSpeed, camera.position.x - 300f, camera.position.y - 60, increaseSpeed.getWidth() * 0.50f, increaseSpeed.getHeight() * 0.50f);
                game.batchOptions.draw(increaseTimer, camera.position.x - 300f, camera.position.y - 120, increaseTimer.getWidth() * 0.50f, increaseTimer.getHeight() * 0.50f);
                game.batchOptions.draw(plus, camera.position.x + 200f, camera.position.y + 0, plus.getWidth() * 0.6f, plus.getHeight() * 0.6f);
                game.batchOptions.draw(plus, camera.position.x + 200f, camera.position.y  - 60, plus.getWidth() * 0.6f, plus.getHeight() * 0.6f);
                game.batchOptions.draw(plus, camera.position.x + 200f, camera.position.y - 120, plus.getWidth() * 0.6f, plus.getHeight() * 0.6f);
                font.draw(game.batchOptions, moneyString, camera.position.x, camera.position.y + 113);
                font.draw(game.batchOptions, bagString, camera.position.x + 40, camera.position.y + 28);
                font.draw(game.batchOptions, speedString, camera.position.x , camera.position.y - 32);
                font.draw(game.batchOptions, timeString, camera.position.x - 30, camera.position.y - 95);
            }
        game.batchOptions.end();


        if(Gdx.input.justTouched() && menuScreen == true){
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);
            Rectangle playBounds=new Rectangle(330,200,playWidth,playHeight);
            Rectangle exitBounds=new Rectangle(600,50,exitWidth,exitHeight - 10);
            Rectangle resetBounds=new Rectangle(50,50,resetWidth,resetHeigh - 10);
            Rectangle shopBounds=new Rectangle(100,130,shopWidth,shopHeight - 10);
            Rectangle statsBounds=new Rectangle(520,130,statsWidth,statsHeigh - 10);
            if(playBounds.contains(tmp.x,tmp.y))
            {
                game.setScreen(new Game(game));
                dispose();
            }
            if(exitBounds.contains(tmp.x, tmp.y)){
                dispose();
                System.exit(0);
            }
            if(resetBounds.contains(tmp.x,tmp.y))
            {
                useGrayScale = !useGrayScale;
                menuScreen = false;
                //System.out.println(useGrayScale);
                resetScreen = true;
            }
            if(shopBounds.contains(tmp.x, tmp.y)){
                useGrayScale = !useGrayScale;
                shopScreen = true;
                menuScreen = false;
            }
            if(statsBounds.contains(tmp.x, tmp.y)){
                useGrayScale = !useGrayScale;
            }
        }

        if(resetScreen == true){
            if(Gdx.input.justTouched()){
                Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                camera.unproject(tmp);
                Rectangle yesBounds=new Rectangle(200,150,yes.getWidth(),yes.getHeight());
                Rectangle noBounds=new Rectangle(450,150,no.getWidth(),no.getHeight());
                if(yesBounds.contains(tmp.x,tmp.y))
                {
                    all = new DataAll().scenarijA();
                    SaveLoad.saveState(all);
                    resetScreen = false;
                    menuScreen = true;
                    useGrayScale = !useGrayScale;
                }
                if(noBounds.contains(tmp.x, tmp.y)){
                    resetScreen = false;
                    menuScreen = true;
                    useGrayScale = !useGrayScale;
                }
            }
        }

        if(shopScreen){
            if(Gdx.input.justTouched()){
                Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                camera.unproject(tmp);
                Rectangle exitBounds=new Rectangle(camera.position.x + 240f, camera.position.y + 90,x.getWidth(),x.getHeight());
                Rectangle bagBounds=new Rectangle(camera.position.x - 300f, camera.position.y,plus.getWidth(),plus.getHeight());
                Rectangle speedBounds=new Rectangle(camera.position.x - 300f, camera.position.y - 60,plus.getWidth(),plus.getHeight());
                Rectangle timeBounds=new Rectangle(camera.position.x - 300f, camera.position.y - 120,plus.getWidth(),plus.getHeight());
                if(exitBounds.contains(tmp.x,tmp.y))
                {
                    shopScreen = false;
                    menuScreen = true;
                    useGrayScale = !useGrayScale;
                }
                if(bagBounds.contains(tmp.x, tmp.y)){
                    if(all.getGold() >= ((all.getInventorySpace() - 5) * 400)) {
                        all.setGold(all.getGold() - ((all.getInventorySpace() - 5) * 400));
                        all.setInventorySpace(all.getInventorySpace() + 1);
                        SaveLoad.saveState(all);
                        all = SaveLoad.loadState();
                    }
                }
                if(speedBounds.contains(tmp.x, tmp.y)){
                    int value = (int)((all.getSpeed() - 0.95f) * 6000);
                    while(value % 100 != 0){
                        value++;
                    }
                    if(all.getGold() >= value) {
                        all.setGold(all.getGold() - value);
                        all.setSpeed(all.getSpeed() + 0.050f);
                        SaveLoad.saveState(all);
                        all = SaveLoad.loadState();
                    }
                }
                if(timeBounds.contains(tmp.x, tmp.y)){
                    if(all.getGold() >= ((all.getTime() - 80) * 100)) {
                        all.setGold(all.getGold() - ((all.getTime() - 80) * 100));
                        all.setTime(all.getTime() + 1);
                        SaveLoad.saveState(all);
                        all = SaveLoad.loadState();
                    }
                }
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
        popUP.dispose();
        yes.dispose();
        no.dispose();
        resetProgress.dispose();
        resetWarning.dispose();
        x.dispose();
        increaseBagSize.dispose();
        increaseSpeed.dispose();
        increaseTimer.dispose();
        money.dispose();
        plus.dispose();
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