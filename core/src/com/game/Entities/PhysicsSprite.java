package com.game.Entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

public class PhysicsSprite extends Sprite {
    public World world;      // physics word to put the body into, initialized in a class that interfaces Screen
    public Body body;           // the physics body of the sprite, used in other classes, but is useless here
    protected String name;      // string to input as userdata for a the fixture of the body, which is used for distinguishing collisions

    public PhysicsSprite(String name,Texture texture, World PhysicsWorld, float x, float y, boolean canMove){
        super(texture);
        this.name = name;
        this.world = PhysicsWorld;
        setPosition(x,y);
        setOrigin(getWidth()/2,getHeight()/2);
        InitializeBody(canMove);
    }

    /**
     * Initializes the body based on the texture size.
     * Often overwritten by child classes
     * @param cm
     */
    protected void InitializeBody(boolean cm){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2f/GameMain.PPM,getHeight()/2f/GameMain.PPM);

        BodyDef bd = new BodyDef();
        bd.position.set((getX()+getWidth()/2)/GameMain.PPM,(getY()+getHeight()/2)/GameMain.PPM);
        if(cm) {
            bd.type = BodyDef.BodyType.DynamicBody;
        }else{
            bd.type = BodyDef.BodyType.StaticBody;
        }
        body = world.createBody(bd);
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.shape = shape;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(name);
        shape.dispose();
    }

    /**
     * Updates the sprites position and rotation based on the body
     */
    private void updateSprite(){
        setPosition((body.getPosition().x)*GameMain.PPM-getWidth()/2,(body.getPosition().y)*GameMain.PPM-getHeight()/2);
        setRotation((float)Math.toDegrees((double) body.getAngle()));
    }

    /**
     * Draws the sprite, but updates position before doing so
     * @param batch
     */
    @Override
    public void draw(Batch batch){
        updateSprite();
        super.draw(batch);
    }
}
