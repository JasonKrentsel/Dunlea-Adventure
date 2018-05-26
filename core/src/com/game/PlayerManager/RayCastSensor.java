package com.game.PlayerManager;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

public class RayCastSensor {
    private World world;
    private Vector2 fromPoint = new Vector2();
    private Vector2 toPoint = new Vector2();
    private Vector2 collisionPoint = new Vector2();

    public RayCastSensor(World world) {
        shapeRenderer.setAutoShapeType(true);
        this.world = world;
    }

    private void calculateCollisionPoint() {
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                RayCastSensor.this.collisionPoint.set(point);
                return 0;
            }
        };
        world.rayCast(callback, fromPoint, toPoint);
    }

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public boolean cast(Vector2 initialPoint, Vector2 bilinearPoint,float maxDistance) {
        fromPoint.set(initialPoint);
        toPoint.set(bilinearPoint);
        calculateCollisionPoint();

        shapeRenderer.begin();
        shapeRenderer.line(initialPoint.scl(100),collisionPoint.scl(100));
        shapeRenderer.end();

        initialPoint.scl(1/100f);
        collisionPoint.scl(1/100f);

        return getDitance(initialPoint,collisionPoint)<(maxDistance/GameMain.PPM);
    }

    private float getDitance(Vector2 first, Vector2 second){
        return (float)Math.sqrt((first.x-second.x)*(first.x-second.x)+(first.y-second.y)*(first.y-second.y));
    }
}
