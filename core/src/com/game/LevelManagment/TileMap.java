package com.game.LevelManagment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
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
        buildShapes(map,world);
    }

    public void buildShapes(Map map, World world) {
        MapObjects objects = map.getLayers().get("Col").getObjects();
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = getPolylineShape((PolylineMapObject) object);
            } else {
                continue;
            }
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1).setUserData("TileBox");
            shape.dispose();
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

    /**
     * Responsible for rendering the textured tiles of the tilemap onto the screen
     *
     * @param camera
     */
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }
}
