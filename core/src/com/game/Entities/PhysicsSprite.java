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
    World world;
    public Body body;
    String name;

    public PhysicsSprite(String name,Texture texture, World PhysicsWorld, float x, float y, boolean canMove){
        super(texture);
        this.name = name;
        this.world = PhysicsWorld;
        setPosition(x,y);
        setOrigin(getWidth()/2,getHeight()/2);
        InitializeBody(canMove);
    }

    public PhysicsSprite(String name,Texture texture, Level wrld, float x, float y, boolean canMove){
        super(texture);
        this.name = name;
        this.world = wrld.getWorld();
        setPosition(x,y);
        setOrigin(getWidth()/2,getHeight()/2);
        InitializeBody(canMove);
    }

    protected void InitializeBody(boolean cm){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2f/GameMain.PPM,getHeight()/2f/GameMain.PPM);

        BodyDef bd = new BodyDef();
        bd.position.set((getX()-GameMain.WIDTH/2+getWidth()/2)/GameMain.PPM,(getY()-GameMain.HEIGHT/2+getHeight()/2)/GameMain.PPM);

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

    private void updateSprite(){
        setPosition((body.getPosition().x)*GameMain.PPM+GameMain.WIDTH/2-getWidth()/2,(body.getPosition().y)*GameMain.PPM+GameMain.HEIGHT/2-getHeight()/2);
        setRotation((float)Math.toDegrees((double) body.getAngle()));
    }

    @Override
    public void draw(Batch batch){
        updateSprite();
        super.draw(batch);
    }

    public  void setPos(float x, float y){

    }
}
