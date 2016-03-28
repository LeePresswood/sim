package com.leepresswood.handlers

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold

/**
 * Created by Lee on 3/24/2016.
 */
class ContactHandler implements ContactListener{
    private int foot_contact_count

    //Collision detection
    @Override
    /**
     * Called when two fixtures start to collide.
     */
    void beginContact(Contact contact) {
        if (contact.fixtureA.userData?.foot){
            println "Foot sensed at A."
            foot_contact_count++
        }
        if (contact.fixtureB.userData?.foot){
            println "Foot sensed at B."
            foot_contact_count++
        }
    }

    @Override
    /**
     * Called when two fixtures stop colliding.
     */
    void endContact(Contact contact) {
        if (contact.fixtureA.userData?.foot){
            println "Foot lifted at A."
            foot_contact_count--
        }
        if (contact.fixtureB.userData?.foot){
            println "Foot lifted at B."
            foot_contact_count--
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
        return foot_contact_count > 0
    }
}
