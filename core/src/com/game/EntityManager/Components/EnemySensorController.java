package com.game.EntityManager.Components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.EntityManager.Enemy;
import com.game.EntityManager.Sensor.AABBSensor;
import com.game.EntityManager.Sensor.Position;
import com.game.EntityManager.Sensor.PositionStorage;

public class EnemySensorController {
    Enemy enemy;
    World world;
    AABBSensor sensor;
    PositionStorage<Rectangle> sensorBoxes;

    public EnemySensorController(Enemy enemy) {
        this.enemy = enemy;
        world = enemy.world;
        sensor = new AABBSensor(world);
        sensorBoxes = new PositionStorage<Rectangle>();
        sensorBoxes.set(Position.TopLeft, new Rectangle());
        sensorBoxes.set(Position.TopRight, new Rectangle());
        sensorBoxes.set(Position.BottemLeft, new Rectangle());
        sensorBoxes.set(Position.TopLeft.BottemRight,new Rectangle());
        updateAll();
    }

    public void drawSensorBoxes(ShapeRenderer shapeRenderer) {
        for (Rectangle rec : sensorBoxes.getList()) {
            if (rec != null) {
                shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);
            }
        }
    }

    public void updateAll() {
        for(com.game.EntityManager.Sensor.Position pos: com.game.EntityManager.Sensor.Position.values()){
            updatePos(pos);
        }
    }

    public void updatePos(com.game.EntityManager.Sensor.Position position) {
        switch (position) {
            case TopLeft:
                sensorBoxes.get(position).set(enemy.getX()-15, enemy.getY() + (enemy.getHeight()/2-5), 14, 10);
                break;
            case TopRight:
                sensorBoxes.get(position).set(enemy.getX()+enemy.getWidth()+1, enemy.getY() + (enemy.getHeight()/2-5), 14, 10);
                break;
            case BottemLeft:
                sensorBoxes.get(position).set(enemy.getX()-15, enemy.getY() -10, 10, 1);
                break;
            case BottemRight:
                sensorBoxes.get(position).set(enemy.getX()+enemy.getWidth()+15, enemy.getY() -10, 10, 1);
                break;
        }
    }

    public boolean getState(com.game.EntityManager.Sensor.Position position) {
        Rectangle rec;
        updatePos(position);
        rec = sensorBoxes.get(position);
        return sensor.inEnemy(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height)) || sensor.inTile(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height));
    }
}
