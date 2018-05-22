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
                h = 1;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case Top:
                w = player.getWidth()-2;
                h = 1;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case Left:
                w = 1;
                h = player.getHeight()-2;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
            case Right:
                w = 1;
                h = player.getHeight()-2;
                shape.setAsBox(w/2f/GameMain.PPM,h/2f/GameMain.PPM);
                break;
        }

        BodyDef bd = new BodyDef();
        switch (side){
            case Bottem:
                x = player.getX()+1;
                y = player.getY()-h;
                break;
            case Top:
                x = player.getX()+1;
                y = player.getY()+player.getHeight();
                break;
            case Left:
                x = player.getX()-w;
                y = player.getY()+1;
                break;
            case Right:
                x = player.getX()+player.getWidth();
                y = player.getY()+1;
                break;
        }
        bd.position.set((x-GameMain.WIDTH/2+w/2)/GameMain.PPM,(y-GameMain.HEIGHT/2+h/2)/GameMain.PPM);
        bd.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.shape = shape;
        fd.isSensor = false;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(side);
        shape.dispose();
    }

    public void updatePos(){
        switch (side){
            case Bottem:
                x = player.getX()+1;
                y = player.getY()-h;
                break;
            case Top:
                x = player.getX()+1;
                y = player.getY()+player.getHeight();
                break;
            case Left:
                x = player.getX()-w;
                y = player.getY()+1;
                break;
            case Right:
                x = player.getX()+player.getWidth();
                y = player.getY()+1;
                break;
        }
        body.setTransform((x-GameMain.WIDTH/2+w/2)/GameMain.PPM,(y-GameMain.HEIGHT/2+h/2)/GameMain.PPM,0);
    }
}
