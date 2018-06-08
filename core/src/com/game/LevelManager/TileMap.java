package com.game.LevelManager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

import java.util.ArrayList;

/**
 * simplifies drawing tilemaps and creating hitboxes, does both at the same time
 */
public class TileMap {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    MapProperties aspects;
    public Vector2 mapSize = new Vector2();
    private Vector2 playerPos = new Vector2();
    private ArrayList<Vector2> enemyPos = new ArrayList<Vector2>();

    private ArrayList<Rectangle> killZones = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> damageZones = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> endZones = new ArrayList<Rectangle>();
    private ArrayList<Rectangle> floatZones = new ArrayList<Rectangle>();

    public TileMap(String filePath, World world) {
        map = new TmxMapLoader().load(filePath);
        renderer = new OrthogonalTiledMapRenderer(map);
        aspects = map.getProperties();
        mapSize.set(aspects.get("width", Integer.class),aspects.get("height", Integer.class));
        mapSize.scl(96f);
        buildShapes(map,world);
        getEntityPoints(map);
        getZones(map);
    }

    public void buildShapes(Map map, World world) {
        MapObjects objects = map.getLayers().get("Col").getObjects();
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = getPolylineShape((PolylineMapObject) object);
            }
            else {
                continue;
            }
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1).setUserData("TileBox");
            shape.dispose();
        }
    }

    private void getEntityPoints(Map map){
        MapObject player = map.getLayers().get("Player").getObjects().get("Player");
        playerPos.set((Float)player.getProperties().get("x"),(Float)player.getProperties().get("y"));

        try {
            MapObjects enemies = map.getLayers().get("Enemy").getObjects();
            for (MapObject enemy : enemies) {
                enemyPos.add(new Vector2((Float) enemy.getProperties().get("x"), (Float) enemy.getProperties().get("y")));
            }
        }catch (Exception e){

        }
    }

    private void getZones(Map map){
        try {
            MapObjects objects = map.getLayers().get("Zones").getObjects();
            for (MapObject obj : objects) {
                if (obj instanceof RectangleMapObject) {
                    RectangleMapObject rec = (RectangleMapObject) obj;
                    if (rec.getName().equals("Kill")) {
                        killZones.add(rec.getRectangle());
                    } else if (rec.getName().equals("End")) {
                        endZones.add(rec.getRectangle());
                    } else if (rec.getName().equals("Damage")) {
                        damageZones.add(rec.getRectangle());
                    } else if (rec.getName().equals("Float")) {
                        floatZones.add(rec.getRectangle());
                    }
                }
            }
        }catch (Exception e){

        }
    }

    private static ChainShape getPolylineShape(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = (vertices[i * 2]) / GameMain.PPM;
            worldVertices[i].y = (vertices[i * 2 + 1]) / GameMain.PPM;
        }
        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    public boolean isInEndZone(Vector2 pos){
        for(Rectangle rec : endZones){
            if(rec.contains(pos))
                return true;
        }
        return false;
    }

    public boolean isInKillZone(Vector2 pos){
        for(Rectangle rec : killZones){
            if(rec.contains(pos))
                return true;
        }
        return false;
    }

    public boolean isInDamageZone(Vector2 pos){
        for(Rectangle rec : damageZones){
            if(rec.contains(pos))
                return true;
        }
        return false;
    }

    public boolean isInFloatZone(Vector2 pos){
        for(Rectangle rec : floatZones){
            if(rec.contains(pos))
                return true;
        }
        return false;
    }
    /**
     * Responsible for rendering the textured tiles of the tilemap onto the screen
     *
     * @param camera
     */
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public Vector2 getPlayerPos(){
        return playerPos;
    }

    public ArrayList<Vector2> getEnemyPositions() {
        return enemyPos;
    }
}
