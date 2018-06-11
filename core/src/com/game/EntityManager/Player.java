package com.game.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.Entities.PhysicsSprite;
import com.game.EntityManager.Components.PlayerPunchSensor;
import com.game.EntityManager.Sensor.Position;
import com.game.GameMain;
import com.game.EntityManager.Components.PlayerSensorController;
import com.game.EntityManager.Sensor.AABBSensor;
import com.game.LevelManager.Level;

public class Player extends PhysicsSprite {
    /**
     * Player Movement Vars
     */
    private float speed = 7;
    private float jumpSpeed = 15;
    private float dropForce = 17;
    private float slideVelocity = 2;
    private boolean immune = false;
    public int health = 3;
    public boolean ended = false;
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
    private final Texture invis = new Texture("dunlea/invis.png");

    private final Animation<Texture> punchRight = new Animation<Texture>(.08f, new Texture("dunlea/punchRight/F0.gif"), new Texture("dunlea/punchRight/F1.gif"), new Texture("dunlea/punchRight/F2.gif"));
    private final Animation<Texture> punchLeft = new Animation<Texture>(.08f, new Texture("dunlea/punchLeft/F0.gif"), new Texture("dunlea/punchLeft/F1.gif"), new Texture("dunlea/punchLeft/F2.gif"));
    private final Texture dead = new Texture("dunlea/dead.png");
    private final Texture deadRed = new Texture("dunlea/deadRed.png");
    private final Sprite backgroundDead = new Sprite(new Texture("dunlea/gameOver.png"));

    private final Sound hurt = Gdx.audio.newSound(Gdx.files.internal("dunlea/sound/hurt.wav"));
    private final Sound punch = Gdx.audio.newSound(Gdx.files.internal("dunlea/sound/punch.wav"));
    private final Sound jump = Gdx.audio.newSound(Gdx.files.internal("dunlea/sound/Jump.wav"));
    private final Sound fall = Gdx.audio.newSound(Gdx.files.internal("dunlea/sound/fall.wav"));

    private float elapsed = 0;                                      // elapsed time used for animation timing
    Vector2 init = new Vector2();
    public PlayerSensorController sensorController = new PlayerSensorController(this);           // pointer to the sensor states that are passed to and edited by the CollisionManager
    private Level lvl;
    public PlayerPunchSensor punchSensor = new PlayerPunchSensor(this);

    /**
     * Creates player with Sensors.
     * Initializes the sprite with Dunlea's right-side idle frame 0 texture.
     * Which is 27x42.
     *
     * @param lvl
     * @param x
     * @param y
     */
    public Player(Level lvl, float x, float y) {
        super("Player", new Texture("dunlea/idleRight/F0.png"), lvl.world, x, y, true);
        this.lvl = lvl;
        init.set(x, y);
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
        shape.setAsBox((getWidth() / 2f - 8) / GameMain.PPM, getHeight() / 2f / GameMain.PPM);
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

    @Override
    public void update(SpriteBatch batch) {
        draw(batch);
    }

    float punchElapsed = 0;
    boolean isPunching;
    float elapsedHurt = 3f;
    Vector2 playPos = new Vector2();

    public float deadElapsed;
    float deadY;
    boolean deadDown = false;
    float transperency = 0;
    float yUp = -200;
    public boolean isDead = false;

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
        if (lvl.tileMap.isInEndZone(new Vector2(getX(), getY()))) {
            lvl.ui.isPaused = true;
            ended = true;
        } else {
            if (health <= 0) {
                if (!isDead)
                    fall.play();
                isDead = true;
                lvl.ui.isPaused = true;
                deadElapsed += Gdx.graphics.getDeltaTime();
                if (deadElapsed < 2) {
                    if (deadY < getY() + 100 && !deadDown) {
                        deadY += 5;
                    } else {
                        deadDown = true;
                    }
                    if (deadDown && deadY > -400) {
                        deadY -= 5;
                    }
                    batch.draw(dead, getMidpoint().x - dead.getWidth() / 2, getY() + deadY);
                } else {
                    if (transperency <= 1) {
                        transperency += .05;
                    } else {
                        if (yUp < 500)
                            yUp += 50;
                    }
                    backgroundDead.setPosition(lvl.pX - GameMain.WIDTH / 2, lvl.pY - GameMain.HEIGHT / 2);
                    backgroundDead.draw(batch, transperency);
                }
            } else {
                playPos.set(getX(), getY());
                if (lvl.tileMap.isInDamageZone(playPos) && elapsedHurt > 2) {
                    hurt();
                }
                if (lvl.tileMap.isInEndZone(playPos)) {
                    // end level
                }
                if (lvl.tileMap.isInFloatZone(playPos)) {
                    if (body.getLinearVelocity().y < 15)
                        body.applyForceToCenter(0, 100, true);
                }
                if (lvl.tileMap.isInKillZone(playPos)) {
                    hurt();
                    health = 0;
                }

                elapsedHurt += Gdx.graphics.getDeltaTime();
                if ((sensorController.isInEnemy(Position.Top) || sensorController.isInEnemy(Position.BottemRight) || sensorController.isInEnemy(Position.BottemLeft) || sensorController.isInEnemy(Position.TopRight) || sensorController.isInEnemy(Position.TopLeft)) && elapsedHurt > 2) {
                    hurt();
                }
                immune = elapsedHurt < 2f;
                punchElapsed += Gdx.graphics.getDeltaTime();
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && punchElapsed > .2 && !inAir && body.getLinearVelocity().x < 6 && elapsedHurt > 2) {
                    punch.play();
                    punchElapsed = 0;
                    if (punchSensor.isInEnemy(isRight) != null) {
                        punchSensor.isInEnemy(isRight).kill();
                    }
                }

                if (punchElapsed > .2 || inAir) {
                    super.draw(batch);
                    isPunching = false;
                } else {
                    isPunching = true;
                    super.updateSprite();
                    if (isRight)
                        batch.draw(punchRight.getKeyFrame(elapsed, true), getX(), getY());
                    else
                        batch.draw(punchLeft.getKeyFrame(elapsed, true), getX() - 20, getY());
                }

                if (sensorController.isOnEnemy() && inAir) {
                    sensorController.isInEnemy().kill();
                    body.setLinearVelocity(body.getLinearVelocity().x, jumpSpeed);
                    inAir = true;
                }

                if (getY() < -500) {
                    health = 0;
                }
                elapsed += Gdx.graphics.getDeltaTime();
                move();
            }
        }
    }

    /**
     * Change Dunlea's velocity based on user's input
     */
    boolean isRight = true;

    boolean inAir = false;

    enum State {
        Slide,
        Halt,
        Move
    }

    State state;

    private void hurt() {
        elapsedHurt = 0;
        health--;
        hurt.play();
    }

    private void move() {
        if (!lvl.ui.isPaused()) {
            /**
             * Left and Right Movement
             */
            if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
                state = State.Halt;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.BottemRight)) {
                if (body.getLinearVelocity().x < 0)
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                if (body.getLinearVelocity().x < speed)
                    body.applyForceToCenter(30, 0, true);
                state = State.Move;
                isRight = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.A) && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.BottemLeft)) {
                if (body.getLinearVelocity().x > 0)
                    body.setLinearVelocity(0, body.getLinearVelocity().y);
                if (body.getLinearVelocity().x > -speed)
                    body.applyForceToCenter(-30, 0, true);
                state = State.Move;
                isRight = false;
            } else {
                body.setLinearVelocity(0, body.getLinearVelocity().y);
                state = State.Halt;
            }
            /**
             * Wall sliding
             */
            if (Gdx.input.isKeyPressed(Input.Keys.A) && sensorController.isInTile(com.game.EntityManager.Sensor.Position.TopLeft) && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem)) {
                body.setLinearVelocity(body.getLinearVelocity().x, -slideVelocity);
                state = State.Slide;
                isRight = false;
                inAir = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D) && sensorController.isInTile(com.game.EntityManager.Sensor.Position.TopRight) && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem)) {
                body.setLinearVelocity(body.getLinearVelocity().x, -slideVelocity);
                state = State.Slide;
                isRight = true;
                inAir = false;
            }
            /**
             * Jumping
             */
            if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.S)) {

            } else if (Gdx.input.isKeyPressed(Input.Keys.W) && sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem) && !((sensorController.isInTile(com.game.EntityManager.Sensor.Position.TopLeft) && Gdx.input.isKeyPressed(Input.Keys.A)) || (sensorController.isInTile(com.game.EntityManager.Sensor.Position.TopRight) && Gdx.input.isKeyPressed(Input.Keys.D)))) {
                jump.play(.2f);
                body.setLinearVelocity(body.getLinearVelocity().x, jumpSpeed);
                inAir = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S) && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem)) {
                body.applyForceToCenter(0f, -dropForce, true);
                inAir = true;
            } else if (sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem)) {
                inAir = false;
            } else {
                inAir = true;
            }
            if (!isPunching)
                chooseTexture();
        }
    }

    float elapsedFlash = 0;

    private void chooseTexture() {
        switch (state) {
            case Slide:
                if (isRight)
                    setTexture(slideRight);
                else
                    setTexture(slideLeft);
                break;
            case Halt:
                if (isRight)
                    setTexture(haltRight.getKeyFrame(elapsed, true));
                else
                    setTexture(haltLeft.getKeyFrame(elapsed, true));
                break;
            case Move:
                if (isRight)
                    setTexture(runRight.getKeyFrame(elapsed, true));
                else
                    setTexture(runLeft.getKeyFrame(elapsed, true));
                break;
        }
        if (state != State.Slide && !sensorController.isInTile(com.game.EntityManager.Sensor.Position.Bottem)) {
            if (isRight)
                setTexture(jumpRight);
            else
                setTexture(jumpLeft);
        }
        if (immune) {
            elapsedFlash += Gdx.graphics.getDeltaTime();
            float x = elapsedFlash - (int) elapsedFlash;
            int y = (int) (x * 100);
            if (y % 3 == 0)
                setTexture(invis);
        }
    }

    private Vector2 midpoint = new Vector2();

    public Vector2 getMidpoint() {
        midpoint.set(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
        return midpoint;
    }
}
