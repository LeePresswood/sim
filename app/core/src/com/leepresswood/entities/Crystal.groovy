package com.leepresswood.entities

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.leepresswood.NGame

/**
 * Created by Lee on 3/28/2016.
 */
public class Crystal extends B2DSprite{
    public Crystal(Body body){
        super(body)

        Texture texture = NGame.resources.getTexture("crystal")
        TextureRegion[] sprites = TextureRegion.split(texture, 16, 16)[0]

        setAnimation(sprites, 1f / 12f as float)
    }
}
