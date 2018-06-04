package com.game.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
import com.game.StateUpdate.DrawUpdatable;

public class Enemy extends PhysicsSprite{
    Animation<Texture> right = new Animation<Texture>(.13f,new Texture("Enemy/Flopper/Right/F0.png"),new Texture("Enemy/Flopper/Right/F1.png"),new Texture("Enemy/Flopper/Right/F2.png"));
    Animation<Texture> left = new Animation<Texture>(.13f,new Texture("Enemy/Flopper/Left/F0.png"),new Texture("Enemy/Flopper/Left/F1.png"),new Texture("Enemy/Flopper/Left/F2.png"));

    public EnemySensorController sensorController = new EnemySensorController(this);
    Level lvl;

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
        fd.friction = 0;
        fd.shape = shape;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(name);
        shape.dispose();
    }

    @Override
    public void update(SpriteBatch batch) {
        super.update(batch);
        if(!lvl.ui.isPaused())
        move();
    }

    float elapsed = 0;
    int isRight = 1;
    public void move(){
        elapsed += Gdx.graphics.getDeltaTime();
        if(sensorController.getState(Position.BottemLeft)){
            isRight = 1;
        }
        if(sensorController.getState(Position.BottemRight)){
            isRight = -1;
        }

        if(isRight == 1)
            setTexture(right.getKeyFrame(elapsed,true));
        else
            setTexture(left.getKeyFrame(elapsed,true));

        body.setLinearVelocity(3f*isRight,body.getLinearVelocity().y);
    }
}
