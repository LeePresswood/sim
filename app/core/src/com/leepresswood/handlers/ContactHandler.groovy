package com.leepresswood.handlers

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

/**
 * Created by Lee on 3/24/2016.
 */
class ContactHandler implements ContactListener{
    //Collision detection
    @Override
    /**
     * Called when two fixtures start to collide.
     */
    void beginContact(Contact contact) {
//        println contact.fixtureA.userData.banana
//        println contact.fixtureB.userData.banana

        if (contact.fixtureA.userData?.foot){
            println "Foot sensed at A."
        }
        if (contact.fixtureB.userData?.foot){
            println "Foot sensed at B."
        }
    }

    @Override
    /**
     * Called when two fixtures stop colliding.
     */
    void endContact(Contact contact) {
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
}
