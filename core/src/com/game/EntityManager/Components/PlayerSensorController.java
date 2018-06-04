package com.game.EntityManager.Components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.EntityManager.Enemy;
import com.game.EntityManager.Sensor.AABBSensor;
import com.game.EntityManager.Sensor.PositionStorage;

public class PlayerSensorController {
    com.game.EntityManager.Player player;
    World world;
    AABBSensor sensor;
    PositionStorage<Rectangle> sensorBoxes;

    public PlayerSensorController(com.game.EntityManager.Player player) {
        this.player = player;
        world = player.world;
        sensor = new AABBSensor(world);
        sensorBoxes = new PositionStorage<Rectangle>();
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.Top, new Rectangle());
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.Bottem, new Rectangle());
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.TopLeft, new Rectangle());
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.BottemLeft, new Rectangle());
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.TopRight, new Rectangle());
        sensorBoxes.set(com.game.EntityManager.Sensor.Position.BottemRight, new Rectangle());
        updateAll();
    }

    public void drawSensorBoxes(ShapeRenderer shapeRenderer) {
        for (Rectangle rec : sensorBoxes.getList()) {
            if (rec != null) {
                shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);
            }
        }
    }

    private void updateAll() {
        for(com.game.EntityManager.Sensor.Position pos: com.game.EntityManager.Sensor.Position.values()){
            updatePos(pos);
        }
    }

    private void updatePos(com.game.EntityManager.Sensor.Position position) {
        switch (position) {
            case Top:
                sensorBoxes.get(position).set(player.getX() + 20, player.getY() + player.getHeight() - 6, player.getWidth() - 40, 1);
                break;
            case Bottem:
                sensorBoxes.get(position).set(player.getX() + 30, player.getY() + 3, player.getWidth() - 60, 1);
                break;
            case TopLeft:
                sensorBoxes.get(position).set(player.getX() +4+11, player.getY() + player.getHeight() - 15, 1, 5);
                break;
            case BottemLeft:
                sensorBoxes.get(position).set(player.getX() +4+11, player.getY() + 10, 1, 5);
                break;
            case TopRight:
                sensorBoxes.get(position).set(player.getX() + player.getWidth() - 6-11, player.getY() + player.getHeight() - 15, 1, 5);
                break;
            case BottemRight:
                sensorBoxes.get(position).set(player.getX() + player.getWidth() - 6-11, player.getY() + 10, 1, 5);
                break;
        }
    }

    public boolean isInTile(com.game.EntityManager.Sensor.Position position) {
        Rectangle rec;
        updatePos(position);
        rec = sensorBoxes.get(position);
        return sensor.inTile(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height));
    }

    public Enemy isInEnemy(com.game.EntityManager.Sensor.Position position) {
        Rectangle rec;
        updatePos(position);
        rec = sensorBoxes.get(position);
        if(sensor.inEnemyObject(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height))==null)
            return null;
        return (Enemy)sensor.inEnemyObject(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height)).getUserData();
    }

    @Override
    public String toString() {
        String o = "";
        for(com.game.EntityManager.Sensor.Position pos : com.game.EntityManager.Sensor.Position.values()){
            o+=pos.toString()+" "+isInTile(pos)+"     ";
        }
        return o;
    }
}
