package com.game.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.game.Entities.Level;
import com.game.Entities.PhysicsSprite;
import com.game.Entities.Player;
import com.game.GameMain;

public class TestLevel implements Screen {

    OrthographicCamera camera = new OrthographicCamera(GameMain.WIDTH/GameMain.PPM, GameMain.HEIGHT/GameMain.PPM);
    Box2DDebugRenderer debugR = new Box2DDebugRenderer();

    GameMain game;
    SpriteBatch batch;

    Player p;

    Level wrld;

    Music m = Gdx.audio.newMusic(Gdx.files.internal("Sound/Music/MenuTheme.ogg"));

    public TestLevel(GameMain g){
        m.setLooping(true);
        m.play();

        game = g;
        batch = g.batch;
        wrld = new Level(new Vector2(0,-9.8f*2),batch);
        p = new Player(wrld,200,200);
        wrld.addSprite(p);
        wrld.addSprite(new PhysicsSprite("item",new Texture("badlogic.jpg"),wrld.getWorld(),500f,500f,true));
        Texture t = new Texture("tile.png");
        for(int x = 0 ; x < 10; x++)
            wrld.addSprite(new PhysicsSprite("TILE",t,wrld,t.getWidth()*(x+1),0,false));
        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,t.getWidth()*10,1*t.getHeight(),false));
        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,t.getWidth()*10,2*t.getHeight(),false));
        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,t.getWidth()*10,3*t.getHeight(),false));

        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,0,1*t.getHeight(),false));
        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,0,2*t.getHeight(),false));
        wrld.addSprite(new PhysicsSprite("TILE",t,wrld,0,3*t.getHeight(),false));
        //new DebugBox(wrld.getWorld(),10,1,10,1,0);
    }
    
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.3f,0.3f,.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        wrld.render();

        //debugR.render(wrld.getWorld(),camera.combined);
        wrld.getWorld().step(Gdx.graphics.getDeltaTime(),6,2);
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
