package com.game.LevelManagment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

/**
 * simplifies drawing tilemaps and creating hitboxes, does both at the same time
 */
public class TileMap {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    public TileMap(String filePath, World world) {
        map = new TmxMapLoader().load(filePath);
        renderer = new OrthogonalTiledMapRenderer(map);
        createBodies(world);
    }

    /**
     * Finds rectangle map objects inside of the tilemap
     * and feeds them into initializeBody()
     * @param world
     */
    private void createBodies(World world){
        PolylineMapObject rect;
        for(MapObject mapObject : map.getLayers().get("Col").getObjects()){
            if(mapObject instanceof RectangleMapObject) {
                rect = (PolylineMapObject)mapObject;
                initializeBody(world,rect);
            }
        }
    }

    /**
     * Method used for creating the hitboxes of the tile map.
     * Uses rectangle map objects created in Tiled(tile map editor).
     * @param world
     * @param polylineMapObject
     */
    private void initializeBody(World world, PolylineMapObject polylineMapObject){
        Shape shape = getPolyline(polylineMapObject);
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bd);
        Fixture f = body.createFixture(shape,1);
        f.setUserData("TileBox");
        shape.dispose();
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / GameMain.PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / GameMain.PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    /**
     * Responsible for rendering the textured tiles of the tilemap onto the screen
     * @param camera
     */
    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }
}
