package com.game.LevelManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.GameMain;
import com.game.UI.Menu.Actors.ThemedTextButton;
import com.game.UI.Menu.MainMenuScreen;

public class EndScreen implements Screen {
    SpriteBatch batch;
    GameMain game;
    Sprite end = new Sprite(new Texture(Gdx.files.internal("end.png")));
    Music music = Gdx.audio.newMusic(Gdx.files.internal("endMusic.mp3"));

    public EndScreen(GameMain game) {
        music.setLooping(true);
        music.setVolume(1);
        this.game = game;
        batch = GameMain.batch;
        batch.setProjectionMatrix(new OrthographicCamera(GameMain.WIDTH,GameMain.HEIGHT).combined);
        end.setPosition(-GameMain.WIDTH/2,-GameMain.HEIGHT/2);
        end.setRegionWidth(GameMain.WIDTH);
        end.setRegionHeight(GameMain.HEIGHT);
    }

    @Override
    public void show() {
    }

    float elapsed = 0;
    float elapsedEverything = 0;
    BitmapFont font = ThemedTextButton.font;
    int frame = 0;

    @Override
    public void render(float delta) {
        if(!music.isPlaying()){
            music.play();
        }

        elapsedEverything += Gdx.graphics.getDeltaTime();
        if(elapsed/2<1)
            elapsed += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Color c = end.getColor();
        c.a = elapsed/2;
        end.setColor(c);

        batch.begin();
        end.draw(batch);

        if(elapsedEverything > 3){
            frame++;
            if(frame<60){
                font.draw(batch,"Press any Key to Continue",400,-100);
            } else if(frame>120){
                frame = 0;
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)){
                music.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
        batch.end();
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
    public void dispose() {

    }
}
