package com.game.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Entities.PhysicsSprite;
import com.game.EntityManager.Components.EnemySensorController;
import com.game.EntityManager.Sensor.Position;
import com.game.GameMain;
import com.game.LevelManager.Level;

public class Enemy extends PhysicsSprite{
    Animation<Texture> right = new Animation<Texture>(.13f,new Texture("Enemy/Flopper/Right/F0.png"),new Texture("Enemy/Flopper/Right/F1.png"),new Texture("Enemy/Flopper/Right/F2.png"));
    Animation<Texture> left = new Animation<Texture>(.13f,new Texture("Enemy/Flopper/Left/F0.png"),new Texture("Enemy/Flopper/Left/F1.png"),new Texture("Enemy/Flopper/Left/F2.png"));
    Animation<Texture> die = new Animation<Texture>(.1f, new Texture("Enemy/Die/F0.png"), new Texture("Enemy/Die/F1.png"), new Texture("Enemy/Die/F2.png"), new Texture("Enemy/Die/F3.png"), new Texture("Enemy/Die/F4.png"));
    Sound death = Gdx.audio.newSound(Gdx.files.internal("Enemy/die.wav"));

    public EnemySensorController sensorController = new EnemySensorController(this);
    Level lvl;
    Fixture fixture;

    public Enemy(Level level, World world, float x, float y) {
        super("Enemy",new Texture("Enemy/Flopper/Right/F0.png"),world,x,y,true);
        lvl = level;
    }

    protected void InitializeBody(boolean cm) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f - 8) / GameMain.PPM, getHeight() / 2f / GameMain.PPM);
        BodyDef bd = new BodyDef();
        bd.position.set((getX() + getWidth() / 2) / GameMain.PPM, (getY() + getHeight() / 2) / GameMain.PPM);
        bd.fixedRotation = true;
        bd.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bd);
        body.setFixedRotation(true);
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.shape = shape;
        body.setUserData(this);
        fixture = body.createFixture(fd);
        fixture.setUserData(name);
        shape.dispose();
    }

    float elapsedDead = 0;
    @Override
    public void update(SpriteBatch batch) {
        if (killed) {
            elapsedDead += Gdx.graphics.getDeltaTime();
            batch.draw(die.getKeyFrame(elapsedDead,false),(getX()+getWidth()/2)-die.getKeyFrame(0).getWidth()/2,getY());
        }else {

            super.update(batch);
        sensorController.updatePos(Position.BottemLeft);
        sensorController.updatePos(Position.BottemRight);
        if (!lvl.ui.isPaused())
            move();
    }
    }

    float elapsed = 0;
    int isRight = 1;
    public void move(){
        elapsed += Gdx.graphics.getDeltaTime();

        if(body.getLinearVelocity().x==0 || !sensorController.getState(Position.BottemLeft)){
            isRight = 1;
        }
        if(body.getLinearVelocity().x==0 || !sensorController.getState(Position.BottemRight)){
            isRight = -1;
        }

        if(isRight == 1)
            setTexture(right.getKeyFrame(elapsed,true));
        else
            setTexture(left.getKeyFrame(elapsed,true));

        body.setLinearVelocity(2f*isRight,body.getLinearVelocity().y);
    }

    boolean dead = false;
    public boolean killed = false;

    public void kill(){
        death.play();
        dead = true;
    }

    public boolean isDead(){
        return dead;
    }
}
