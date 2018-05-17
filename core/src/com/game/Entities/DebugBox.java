package com.game.Entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

public class DebugBox {
    World world;
    Body body;
    float x;
    float y;
    float w;
    float h;
    float r;
    public DebugBox(World world, float x, float y, float w, float h, float r){
        this.world = world;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.r = r;
        InitializeBody();
    }

    private void InitializeBody(){
        BodyDef bd = new BodyDef();
        bd.angle = r;
        bd.position.set(x - GameMain.WIDTH/2f/GameMain.PPM,y - GameMain.HEIGHT/2f/GameMain.PPM);
        bd.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w/2f,h/2f);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.shape = shape;

        Fixture fixture = body.createFixture(fd);
        body.setFixedRotation(true);
    }
}
