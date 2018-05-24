package com.game.LevelManagment;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.PlayerManager.SensorStates;
import com.game.PlayerManager.Side;

public class CollisionManager implements ContactListener{

    SensorStates playerState;

    public CollisionManager(SensorStates state){
        playerState = state;
    }

    @Override
    public void beginContact(Contact contact) {
        /**
         * TO CHANGE WHEN ADDING SENSORS TO ENEMY ENTITIES
         */
        if((contact.getFixtureA().getUserData() instanceof Side || contact.getFixtureB().getUserData() instanceof Side) && !(contact.getFixtureA().getUserData() instanceof Side && contact.getFixtureB().getUserData() instanceof Side))
            proccesPlayerSensor(true,contact);

    }

    @Override
    public void endContact(Contact contact) {
        /**
         * TO CHANGE WHEN ADDING SENSORS TO ENEMY ENTITIES
         */
        if((contact.getFixtureA().getUserData() instanceof Side || contact.getFixtureB().getUserData() instanceof Side) && !(contact.getFixtureA().getUserData() instanceof Side && contact.getFixtureB().getUserData() instanceof Side))
            proccesPlayerSensor(false,contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    /**
     * processes the sensor state of the player when a collision had occurred
     *
     *      TO CHANGE WHEN ADDING SENSORS TO ENEMY ENTITIES
     *
     * @param beginContact
     * @param contact
     */
    private void proccesPlayerSensor(boolean beginContact,Contact contact){
        Fixture sensor;
        Fixture other;
        if(contact.getFixtureA().getUserData() instanceof Side){
            sensor = contact.getFixtureA();
            other = contact.getFixtureB();
        }
        else{
            sensor = contact.getFixtureB();
            other = contact.getFixtureA();
        }
        if(other.getUserData().toString().equals("TileBox")){
            switch ((Side)sensor.getUserData()){
                case Bottem:
                    playerState.bottem = beginContact;
                    break;
                case Top:
                    playerState.top = beginContact;
                    break;
                case TopLeft:
                    playerState.topLeft = beginContact;
                    break;
                case TopRight:
                    playerState.topRight = beginContact;
                    break;
                case BottemLeft:
                    playerState.bottemRight = beginContact;
                    break;
                case BottemRight:
                    playerState.bottemRight = beginContact;
                    break;
            }
        }
    }
}
