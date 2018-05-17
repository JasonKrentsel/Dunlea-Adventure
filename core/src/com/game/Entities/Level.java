package com.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Level {
    private World world;
    private ArrayList<PhysicsSprite> renderList;
    private SpriteBatch batch;
    private int playerIndex;

    public Level(Vector2 gravity, SpriteBatch batch){
        world = new World(gravity,true);
        renderList = new ArrayList<PhysicsSprite>();
        this.batch = batch;
    }

    public void addSprite(PhysicsSprite physicsSprite){
        renderList.add(physicsSprite);
        if(physicsSprite instanceof Player){
            playerIndex = renderList.size();
        }
    }

    public void render(){
        batch.begin();
        for(PhysicsSprite a : renderList){
            a.draw(batch);
        }
        batch.end();
    }

    public World getWorld() {
        return world;
    }

    public PhysicsSprite getPlayer(){
        return renderList.get(playerIndex);
    }
}
