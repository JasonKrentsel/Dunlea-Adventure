package com.game.EntityManager.Sensor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

import java.util.ArrayList;

public class AABBSensor {
    World world;
    ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
    public AABBSensor(World world){
        this.world = world;
    }


    /**
     * Finds out if in the given area there is a tile hitbox.
     * WARNING: It must be colliding with atleast 1 thing at any time,
     *          meaning that if the sensor is used for a player, it should
     *          intersect him and extrude out of him at the same time.
     * @param lowerPoint
     * @param upperPoint
     * @return
     */
    public boolean inTile(Vector2 lowerPoint, Vector2 upperPoint){
        fixtures.clear();
        Vector2 lower = new Vector2(lowerPoint.x/GameMain.PPM,lowerPoint.y/GameMain.PPM);
        Vector2 upper = new Vector2(upperPoint.x/GameMain.PPM,upperPoint.y/GameMain.PPM);

        QueryCallback callback = new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                fixtures.add(fixture);
                return true;
            }
        };
        world.QueryAABB(callback,Math.min(lower.x,upper.x),Math.min(lower.y,upper.y),Math.max(upper.x,lower.x),Math.max(upper.y,lower.y));
        for(int x = 0; x < fixtures.size() ; x++){
            if(fixtures.get(x).getUserData().toString().equals("TileBox")){
                return true;
            }
        }
        return false;
    }

    public boolean inEnemy(Vector2 lowerPoint, Vector2 upperPoint){
        fixtures.clear();
        Vector2 lower = new Vector2(lowerPoint.x/GameMain.PPM,lowerPoint.y/GameMain.PPM);
        Vector2 upper = new Vector2(upperPoint.x/GameMain.PPM,upperPoint.y/GameMain.PPM);

        QueryCallback callback = new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                fixtures.add(fixture);
                return true;
            }
        };
        world.QueryAABB(callback,Math.min(lower.x,upper.x),Math.min(lower.y,upper.y),Math.max(upper.x,lower.x),Math.max(upper.y,lower.y));
        for(int x = 0; x < fixtures.size() ; x++){
            if(fixtures.get(x).getUserData().toString().equals("Enemy")){
                return true;
            }
        }
        return false;
    }
}
