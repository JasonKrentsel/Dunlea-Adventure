package com.game.PlayerManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.Entities.PhysicsSprite;
import com.game.GameMain;
import com.game.PlayerManager.Components.PlayerSensorController;
import com.game.PlayerManager.Sensor.AABBSensor;
import com.game.PlayerManager.Sensor.Position;

public class Player extends PhysicsSprite {
    /**
     * Player Movement Vars
     */
    private float speed = 6;
    private float jumpVelocity = 15;
    private float dropVelocity = 10;
    private float slideVelocity = 2;

    /**
     * various animations and textures for Dunlea in certain situations
     */
    private final Animation<Texture> haltRight = new Animation<Texture>(.21f, new Texture("dunlea/idleRight/F0.png"), new Texture("dunlea/idleRight/F1.png"));
    private final Animation<Texture> haltLeft = new Animation<Texture>(.21f, new Texture("dunlea/idleLeft/F0.png"), new Texture("dunlea/idleLeft/F1.png"));
    private final Animation<Texture> runRight = new Animation<Texture>(.11f, new Texture("dunlea/moveRight/F0.png"), new Texture("dunlea/moveRight/F1.png"), new Texture("dunlea/moveRight/F2.png"));
    private final Animation<Texture> runLeft = new Animation<Texture>(.11f, new Texture("dunlea/moveLeft/F0.png"), new Texture("dunlea/moveLeft/F1.png"), new Texture("dunlea/moveLeft/F2.png"));
    private final Texture slideRight = new Texture("dunlea/slide/slideRight.png");
    private final Texture slideLeft = new Texture("dunlea/slide/slideLeft.png");
    private final Texture jumpRight = new Texture("dunlea/jump/jumpRight.png");
    private final Texture jumpLeft = new Texture("dunlea/jump/jumpLeft.png");

    private float elapsed = 0;                                      // elapsed time used for animation timing

    public PlayerSensorController sensorController = new PlayerSensorController(this);           // pointer to the sensor states that are passed to and edited by the CollisionManager

    /**
     * Creates player with Sensors.
     * Initializes the sprite with Dunlea's right-side idle frame 0 texture.
     * Which is 27x42.
     *
     * @param world
     * @param x
     * @param y
     */
    public Player(World world, float x, float y) {
        super("Player", new Texture("dunlea/idleRight/F0.png"), world, x, y, true);
    }

    /**
     * Creates player hitbox.
     * Overwritten from PhysicsSprite for creating the body just right
     *
     * @param cm
     */
    @Override
    protected void InitializeBody(boolean cm) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / GameMain.PPM, getHeight() / 2f / GameMain.PPM);
        BodyDef bd = new BodyDef();
        bd.position.set((getX() + getWidth() / 2) / GameMain.PPM, (getY() + getHeight() / 2) / GameMain.PPM);
        bd.fixedRotation = true;
        bd.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bd);
        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0;
        fd.shape = shape;
        Fixture fixture = body.createFixture(fd);
        fixture.setUserData(name);
        shape.dispose();
    }

    /**
     * draws Dunlea's correct texture as well as:
     * updating elapsed time
     * calling the move() method for taking in controls and giving velocity updates
     * updating the sensors' positions to correctly correlate with Dunlea
     *
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        if(getY()<-500){
            body.setTransform(200/GameMain.PPM,300/GameMain.PPM,0);
        }
        elapsed += Gdx.graphics.getDeltaTime();
        move();
    }

    AABBSensor sensor = new AABBSensor(world);

    /**
     * Change Dunlea's velocity based on user's input
     */
    boolean isRight = true;
    boolean inAir = false;
    boolean isSliding = false;
    private void move() {
        if(Gdx.input.isKeyPressed(Input.Keys.A) && sensorController.getState(Position.TopLeft) && !sensorController.getState(Position.Bottem)){
            body.setLinearVelocity(body.getLinearVelocity().x,-2);
            setTexture(slideLeft);
            isSliding = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D) && sensorController.getState(Position.TopRight) && !sensorController.getState(Position.Bottem)){
            body.setLinearVelocity(body.getLinearVelocity().x,-2);
            setTexture(slideRight);
            isSliding = true;
        }else {
            isSliding = false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S)){
            if(!sensorController.getState(Position.Bottem)) {
                if (isRight)
                    setTexture(jumpRight);
                else
                    setTexture(jumpLeft);
            }
            else{
                if(isRight)
                    setTexture(haltRight.getKeyFrame(elapsed,true));
                else
                    setTexture(haltLeft.getKeyFrame(elapsed,true));
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.W) && sensorController.getState(Position.Bottem) && !((sensorController.getState(Position.TopLeft) && Gdx.input.isKeyPressed(Input.Keys.A)) || (sensorController.getState(Position.TopRight) && Gdx.input.isKeyPressed(Input.Keys.D)))){
            body.setLinearVelocity(body.getLinearVelocity().x,jumpVelocity);
            if(!isSliding) {
                if (isRight)
                    setTexture(jumpRight);
                else
                    setTexture(jumpLeft);
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && !sensorController.getState(Position.Bottem)){
            body.setLinearVelocity(body.getLinearVelocity().x,-dropVelocity);
            if(!isSliding) {
                if (isRight)
                    setTexture(jumpRight);
                else
                    setTexture(jumpLeft);
            }
        } else if(!sensorController.getState(Position.Bottem)){
            inAir = true;
            if(!isSliding) {
                if (isRight)
                    setTexture(jumpRight);
                else
                    setTexture(jumpLeft);
            }
        } else {
            inAir = false;
        }


        if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.A)){
            body.setLinearVelocity(0,body.getLinearVelocity().y);
            if(!inAir && !isSliding){
                if(isRight)
                    setTexture(haltRight.getKeyFrame(elapsed,true));
                else
                    setTexture(haltLeft.getKeyFrame(elapsed,true));
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && !sensorController.getState(Position.BottemRight)){
            body.setLinearVelocity(speed,body.getLinearVelocity().y);
            isRight = true;
            if(!inAir && !isSliding)
                setTexture(runRight.getKeyFrame(elapsed,true));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && !sensorController.getState(Position.BottemLeft)){
            body.setLinearVelocity(-speed,body.getLinearVelocity().y);
            isRight = false;
            if(!inAir && !isSliding)
                setTexture(runLeft.getKeyFrame(elapsed,true));
        }
        else {
            body.setLinearVelocity(0,body.getLinearVelocity().y);
            if(!inAir && !isSliding){
                if(isRight)
                    setTexture(haltRight.getKeyFrame(elapsed,true));
                else
                    setTexture(haltLeft.getKeyFrame(elapsed,true));
            }
        }
    }


    private Vector2 midpoint = new Vector2();
    public Vector2 getMidpoint() {
        midpoint.set(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
        return midpoint;
    }
}
