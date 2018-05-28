package com.game.PlayerManager.Components;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.PlayerManager.Player;
import com.game.PlayerManager.Sensor.AABBSensor;
import com.game.PlayerManager.Sensor.Position;
import com.game.PlayerManager.Sensor.PositionStorage;

public class PlayerSensorController {
    Player player;
    World world;
    AABBSensor sensor;
    PositionStorage<Rectangle> sensorBoxes;

    public PlayerSensorController(Player player) {
        this.player = player;
        world = player.world;
        sensor = new AABBSensor(world);
        sensorBoxes = new PositionStorage<Rectangle>();
        sensorBoxes.set(Position.Top, new Rectangle());
        sensorBoxes.set(Position.Bottem, new Rectangle());
        sensorBoxes.set(Position.TopLeft, new Rectangle());
        sensorBoxes.set(Position.BottemLeft, new Rectangle());
        sensorBoxes.set(Position.TopRight, new Rectangle());
        sensorBoxes.set(Position.BottemRight, new Rectangle());
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
        for(Position pos: Position.values()){
            updatePos(pos);
        }
    }

    private void updatePos(Position position) {
        switch (position) {
            case Top:
                sensorBoxes.get(position).set(player.getX() + 20, player.getY() + player.getHeight() - 6, player.getWidth() - 40, 1);
                break;
            case Bottem:
                sensorBoxes.get(position).set(player.getX() + 18, player.getY() + 3, player.getWidth() - 36, 1);
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

    public boolean getState(Position position) {
        Rectangle rec;
        updatePos(position);
        rec = sensorBoxes.get(position);
        return sensor.inTile(new Vector2(rec.x, rec.y), new Vector2(rec.x + rec.width, rec.y + rec.height));
    }

    @Override
    public String toString() {
        String o = "";
        for(Position pos : Position.values()){
            o+=pos.toString()+" "+getState(pos)+"     ";
        }
        return o;
    }
}
