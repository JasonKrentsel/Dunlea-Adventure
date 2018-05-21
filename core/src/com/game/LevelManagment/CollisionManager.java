package com.game.LevelManagment;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.game.PlayerManager.PlayerSensorState;

public class CollisionManager implements ContactListener{

    PlayerSensorState playerState;

    public CollisionManager(PlayerSensorState state){
        playerState = state;
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().isSensor() || contact.getFixtureB().isSensor()){
            Fixture sensor;
            Fixture other;
            if(contact.getFixtureA().isSensor()) {
                sensor = contact.getFixtureA();
                other = contact.getFixtureB();
            }
            else{
                sensor = contact.getFixtureB();
                other = contact.getFixtureA();
            }
            if(other.getUserData().equals("Player")){
                String name = sensor.getUserData().toString();
                if(name.equals("Sensor:Bottem")){
                    playerState.bottem = true;
                }

            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().isSensor() || contact.getFixtureB().isSensor()){
            Fixture sensor;
            Fixture other;
            if(contact.getFixtureA().isSensor()) {
                sensor = contact.getFixtureA();
                other = contact.getFixtureB();
            }
            else{
                sensor = contact.getFixtureB();
                other = contact.getFixtureA();
            }
            if(other.getUserData().equals("Player")){
                String name = sensor.getUserData().toString();
                if(name.equals("Sensor:Bottem")){
                    playerState.bottem = false;
                }
            }
        }
    }



    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
