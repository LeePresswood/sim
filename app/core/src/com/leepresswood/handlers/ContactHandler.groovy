package com.leepresswood.handlers

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

/**
 * Created by Lee on 3/24/2016.
 */
class ContactHandler implements ContactListener{
    private boolean player_on_ground

    //Collision detection
    @Override
    /**
     * Called when two fixtures start to collide.
     */
    void beginContact(Contact contact) {
        if (contact.fixtureA.userData?.foot){
            println "Foot sensed at A."
            player_on_ground = true
        }
        if (contact.fixtureB.userData?.foot){
            println "Foot sensed at B."
            player_on_ground = true
        }
    }

    @Override
    /**
     * Called when two fixtures stop colliding.
     */
    void endContact(Contact contact) {
        if (contact.fixtureA.userData?.foot){
            println "Foot lifted at A."
            player_on_ground = false
        }
        if (contact.fixtureB.userData?.foot){
            println "Foot lifted at B."
            player_on_ground = false
        }
    }

    //Collision handling
    @Override
    /**
     * Happens before collision. Can choose to avoid collision here.
     */
    void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    /**
     * Happens after collision. Can choose to avoid collision here.
     */
    void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerOGround(){
        return player_on_ground
    }
}
