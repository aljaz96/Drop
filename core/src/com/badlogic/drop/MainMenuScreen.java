package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainMenuScreen implements Screen {

    final Igra game;
    OrthographicCamera camera;
    public Animation<TextureRegion> runningAnimation;
    Texture walk;
    Texture background;
    Texture name;
    Texture bin;
    TextureRegion currentFrame;
    Music music;
    float stateTime;

    public MainMenuScreen(final Igra game) {
        this.game = game;
        music = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.mp3"));
        walk = new Texture(Gdx.files.internal("Hat_man.png"));
        background = new Texture(Gdx.files.internal("Test.png"));
        name = new Texture(Gdx.files.internal("name.png"));
        bin = new Texture(Gdx.files.internal("t5.png"));
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

        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.draw(name, 150, 330);
        game.batch.draw(currentFrame, 50, 330);
        game.batch.draw(bin, 620, 340);
        game.font.draw(game.batch, "Touch mah screen!!!", 200, 100);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new Drop(game));
            dispose();
        }
    }

    public void dispose () {
        walk.dispose();
        music.dispose();
        background.dispose();
        name.dispose();
        bin.dispose();
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


}