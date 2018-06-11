package com.game.EntityManager.Components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.EntityManager.Enemy;
import com.game.EntityManager.Sensor.AABBSensor;
import com.game.EntityManager.Sensor.Position;
import com.game.EntityManager.Sensor.PositionStorage;

import org.w3c.dom.css.Rect;

public class PlayerPunchSensor {
    com.game.EntityManager.Player player;
    World world;
    AABBSensor sensor;
    Rectangle right = new Rectangle();
    Rectangle left = new Rectangle();

    public PlayerPunchSensor(com.game.EntityManager.Player player) {
        this.player = player;
        world = player.world;
        sensor = new AABBSensor(world);
        updatePos(false);
        updatePos(true);
    }

    public void drawSensorBoxes(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(right.x,right.y,right.width,right.height);
        shapeRenderer.rect(left.x,left.y,left.width,left.height);
    }

    public void updatePos(Boolean isRight) {
        if(isRight)
        right.set(player.getX()-50,player.getY()+player.getHeight()/2-15,60,20);
        else
        left.set(player.getX()+player.getWidth()-10,player.getY()+player.getHeight()/2-15,60,20);
    }


    public Enemy isInEnemy(boolean isRight) {
        Rectangle rec;
        updatePos(!isRight);

        if(!isRight)
            rec = right;
        else
            rec = left;

        if(rec == null)
            return null;
        if(sensor.inEnemyObject(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height))==null)
            return null;
        return (Enemy)sensor.inEnemyObject(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height)).getUserData();
    }
}
