package com.game.PlayerManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Entities.PhysicsSprite;
import com.game.GameMain;

public class SideSensor {
    protected final World world;    // the PhysicsSprite's world
    protected Body body;            // the body of the sensor
    protected final Side side;      // side of the sprite the sensor will go on
    protected PhysicsSprite sprite; // the sprite to put the sensor on
    protected float x;              // x position
    protected float y;              // y position
    protected float w;              // width of the sensor
    protected float h;              // height of the sensor

    /**
     * Creates a sensor on the given side of the given sprite.
     * @param sprite
     * @param side
     */
    public SideSensor(PhysicsSprite sprite, Side side){
        this.sprite = sprite;
        this.world = sprite.body.getWorld();
        this.side = side;
        InitializeBody();
        updatePos();
    }

    /**
     * Initializes the body of the sensor.
     * Not a normal body but a sensor.
     * Sensor: No collision between other bodyies, but reports them the same to the CollisionManager.
     * Creates the shape based on given side.
     */
    private void InitializeBody(){
        PolygonShape shape = new PolygonShape();
        switch (side){
            case Bottem:
                w = sprite.getWidth()-10;
                h = 10;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case Top:
                w = sprite.getWidth()-2;
                h = 1;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case TopLeft:
                w = 1;
                h = sprite.getHeight()/5;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case TopRight:
                w = 1;
                h = sprite.getHeight()/5;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case BottemLeft:
                w = 1;
                h = sprite.getHeight()/5;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case BottemRight:
                w = 1;
                h = sprite.getHeight()/5;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
        }

        BodyDef bd = new BodyDef();
        updateXY();
        bd.position.set((x+w/2)/GameMain.PPM,(y+h/2)/GameMain.PPM);
        bd.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.isSensor = true;
        fd.shape = shape;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(side);
        shape.dispose();
    }

    /**
     * Updates the x and y variables that based on which side the sensor is.
     */
    private void updateXY(){
        switch (side){
            case Bottem:
                x = sprite.getX()+5;
                y = sprite.getY()-h;
                break;
            case Top:
                x = sprite.getX()+1;
                y = sprite.getY()+sprite.getHeight();
                break;
            case TopLeft:
                x = sprite.getX()-w;
                y = sprite.getY()+sprite.getHeight()-sprite.getHeight()/5-4;
                break;
            case TopRight:
                x = sprite.getX()+sprite.getWidth();
                y = sprite.getY()+sprite.getHeight()-sprite.getHeight()/5-4;
                break;
            case BottemLeft:
                x = sprite.getX()-w;
                y = sprite.getY()+3;
                break;
            case BottemRight:
                x = sprite.getX()+sprite.getWidth();
                y = sprite.getY()+3;
                break;
        }
    }

    /**
     * updates x and y, and uses them to update the actual position of the sensor.
     */
    public void updatePos(){
        updateXY();
        body.setTransform((x+w/2)/GameMain.PPM,(y+h/2)/GameMain.PPM,0);
    }
}
