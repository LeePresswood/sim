package com.leepresswood.handlers

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Manifold

import java.lang.reflect.Array

/**
 * Created by Lee on 3/24/2016.
 */
class ContactHandler implements ContactListener{
    private int foot_contact_count
    private ArrayList<Body> bodies_to_remove = new ArrayList<>()

    //Collision detection
    @Override
    /**
     * Called when two fixtures start to collide.
     */
    void beginContact(Contact contact) {
        Fixture fa = contact.fixtureA
        Fixture fb = contact.fixtureB

        if (fa.userData?.data == "foot"){
            println "Foot sensed at A."
            foot_contact_count++
        }
        if (fb.userData?.data == "foot"){
            println "Foot sensed at B."
            foot_contact_count++
        }

        if (fa.userData?.data == "crystal"){
            bodies_to_remove.add(fa.getBody())
        }

        if (fb.userData?.data == "crystal"){
            bodies_to_remove.add(fb.getBody())
        }
    }

    @Override
    /**
     * Called when two fixtures stop colliding.
     */
    void endContact(Contact contact) {
        Fixture fa = contact.fixtureA
        Fixture fb = contact.fixtureB

        if (fa.userData?.data == "foot"){
            println "Foot lifted at A."
            foot_contact_count--
        }
        if (fb.userData?.data == "foot"){
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

    public ArrayList<Body> getBodiesToRemove(){
        return bodies_to_remove
    }
}
