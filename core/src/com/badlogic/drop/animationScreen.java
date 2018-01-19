package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by mikik on 19. 01. 2018.
 */

public class animationScreen implements com.badlogic.gdx.Screen {
    final Screen game;
    SpriteBatch batch;
    Animation<TextureRegion> animation;
    float elapsed;
    float timer = 9f;
    boolean test = false;
    Texture name;

    public animationScreen(final Screen game){
        this.game = game;
        batch = new SpriteBatch();
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("TC_anim2.gif").read());
        name = new Texture(Gdx.files.internal("name.png"));
    }


    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsed), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
       // timer -= Gdx.graphics.getDeltaTime();
        if(timer <= 0){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
        if(Gdx.input.justTouched()){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
