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

import java.util.ArrayList;

public class Player extends PhysicsSprite {
    /**
     * Player Movement Vars
     */
    private float speed = 6;
    private float jumpVelocity = 15;
    private float dropVelocity = 10;
    private float slideVelocity = 2;

    private MovementState movementState = new MovementState();
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

    public SensorStates sensorState = new SensorStates();           // pointer to the sensor states that are passed to and edited by the CollisionManager
    private ArrayList<SideSensor> sensors;                          // list of sensors(4), cleaner looking code rather than having 4 lines dedicated for updating their position

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
        sensors = new ArrayList<SideSensor>();
        sensors.add(new SideSensor(this, Side.Bottem));
        sensors.add(new SideSensor(this, Side.Top));
        sensors.add(new SideSensor(this, Side.TopLeft));
        sensors.add(new SideSensor(this, Side.TopRight));
        sensors.add(new SideSensor(this, Side.BottemRight));
        sensors.add(new SideSensor(this, Side.BottemLeft));

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
        bd.position.set((getX() - GameMain.WIDTH / 2 + getWidth() / 2) / GameMain.PPM, (getY() - GameMain.HEIGHT / 2 + getHeight() / 2) / GameMain.PPM);
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
        elapsed += Gdx.graphics.getDeltaTime();
        super.draw(batch);
        move();
        for (SideSensor s : sensors) {
            s.updatePos();
        }
    }

    /**
     * Change Dunlea's velocity based on user's input
     */
    private void move() {
        /**
         * jump and fall
         */
        // slide stuff

        // jump
         if (Gdx.input.isKeyPressed(Input.Keys.UP) && (sensorState.bottem || movementState.state == MovementState.State.sliding)) {
            body.setLinearVelocity(body.getLinearVelocity().x, jumpVelocity);
            if (movementState.state != MovementState.State.sliding)
                movementState.setJump(true);
        }
        // quick fall
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !sensorState.bottem) {
            super.body.setLinearVelocity(body.getLinearVelocity().x, -dropVelocity);
        } else if (sensorState.bottem) {
            movementState.setJump(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT) && ((sensorState.topRight && !sensorState.bottem) || (sensorState.topLeft && !sensorState.bottem))) {
            if (movementState.isRight()) {
                body.setLinearVelocity(-5, body.getLinearVelocity().y);
            } else {
                body.setLinearVelocity(5, body.getLinearVelocity().y);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && sensorState.topRight && !sensorState.bottem) {
            body.setLinearVelocity(body.getLinearVelocity().x, -slideVelocity);
            movementState.state = MovementState.State.sliding;
            movementState.setSide(true);
            movementState.setJump(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && sensorState.topLeft && !sensorState.bottem) {
            body.setLinearVelocity(body.getLinearVelocity().x, -slideVelocity);
            movementState.state = MovementState.State.sliding;
            movementState.setSide(false);
            movementState.setJump(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            movementState.state = MovementState.State.halted;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !sensorState.bottemRight) {
            body.setLinearVelocity(speed, body.getLinearVelocity().y);
            movementState.state = MovementState.State.moving;
            movementState.setSide(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !sensorState.bottemLeft) {
            body.setLinearVelocity(-speed, body.getLinearVelocity().y);
            movementState.state = MovementState.State.moving;
            movementState.setSide(false);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            movementState.state = MovementState.State.halted;
        }
        chooseTexture();
    }

    private void chooseTexture() {
        if (movementState.isJumping()) {
            if (movementState.isRight()) {
                setTexture(jumpRight);
            } else {
                setTexture(jumpLeft);
            }
            return;
        }

        switch (movementState.state) {
            case halted:
                if (movementState.isRight()) {
                    setTexture(haltRight.getKeyFrame(elapsed, true));
                } else {
                    setTexture(haltLeft.getKeyFrame(elapsed, true));
                }
                break;
            case moving:
                if (movementState.isRight()) {
                    setTexture(runRight.getKeyFrame(elapsed, true));
                } else {
                    setTexture(runLeft.getKeyFrame(elapsed, true));
                }
                break;
            case sliding:
                if (movementState.isRight()) {
                    setTexture(slideRight);
                } else {
                    setTexture(slideLeft);
                }
                break;
        }
    }
    private Vector2 midpoint = new Vector2();
    public Vector2 getMidpoint(){
        midpoint.set(getX()+(getWidth()/2),getY()+(getHeight()/2));
        return midpoint;
    }
}
