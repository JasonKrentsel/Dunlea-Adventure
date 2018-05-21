package com.game.LevelManagment;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameMain;

public class TileMap {
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    public TileMap(String filePath, World world) {
        map = new TmxMapLoader().load(filePath);
        renderer = new OrthogonalTiledMapRenderer(map);
        createBodies(world);
    }

    private void createBodies(World world){
        RectangleMapObject rect;
        for(MapObject mapObject : map.getLayers().get("Col").getObjects()){
            if(mapObject instanceof RectangleMapObject) {
                rect = (RectangleMapObject)mapObject;
                initializeBody(world,rect);
            }
        }
    }

    protected void initializeBody(World world, RectangleMapObject rectangleMapObject){
        com.badlogic.gdx.math.Rectangle rect = rectangleMapObject.getRectangle();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth()/2f/ GameMain.PPM,rect.getHeight()/2f/GameMain.PPM);

        BodyDef bd = new BodyDef();
        bd.position.set((rect.getX()-GameMain.WIDTH/2+rect.getWidth()/2)/GameMain.PPM,(rect.getY()-GameMain.HEIGHT/2+rect.getHeight()/2)/GameMain.PPM);
        bd.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bd);

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1;
        fd.shape = shape;
        Fixture f = body.createFixture(fd);
        f.setUserData("TileBox");
        shape.dispose();
    }

    public void render(OrthographicCamera camera){
        renderer.setView(camera);
        renderer.render();
    }
}
