package com.leepresswood.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.leepresswood.NGame

/**
 * Created by Lee on 3/27/2016.
 */
class Player extends B2DSprite{
    private int numCrystals
    private int totalCrystals

    public Player(Body body) {
        super(body)

        Texture texture = NGame.resources.getTexture("bunny")
        TextureRegion[] sprites = TextureRegion.split(texture, 32, 32)[0]
        setAnimation(sprites, 1f / 12f as float)
    }

    public void collectCrystal(){
        numCrystals++
    }

    public int getNumCrystals(){
        return numCrystals
    }

    public void setTotalCrystals(int i){
        totalCrystals = i
    }

    public int getTotalCrystals(){
        return totalCrystals
    }
}
