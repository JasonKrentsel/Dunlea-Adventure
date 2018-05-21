package com.game.PlayerManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

public class SideSensor {
    protected final World world;
    protected Body body;
    protected final Side side;
    protected Player player;
    protected float x;
    protected float y;
    protected float w;
    protected float h;

    public SideSensor(Player player, Side side){
        this.player = player;
        this.world = player.getWorld();
        this.side = side;
        InitializeBody();
        updatePos();
    }

    protected void InitializeBody(){
        PolygonShape shape = new PolygonShape();
        switch (side){
            case Bottem:
                w = player.getWidth()-2;
                h = 4;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
        }

        BodyDef bd = new BodyDef();
        switch (side){
            case Bottem:
                x = player.getX()+1;
                y = player.getY()-w/2;
                bd.position.set((x-GameMain.WIDTH/2+w/2)/GameMain.PPM,(y-GameMain.HEIGHT/2+h/2)/GameMain.PPM);
                break;
        }
        bd.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.shape = shape;
        fd.isSensor = true;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData("Sensor:"+side.toString());
        shape.dispose();
    }

    public void updatePos(){
        switch (side){
            case Bottem:
                x = player.getX()+1;
                y = player.getY()-w/2;
                break;
        }
        body.getPosition().set((x-GameMain.WIDTH/2+w/2)/GameMain.PPM,(y-GameMain.HEIGHT/2+h/2)/GameMain.PPM);
    }
}
