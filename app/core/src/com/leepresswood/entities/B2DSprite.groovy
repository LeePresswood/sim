package com.leepresswood.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.leepresswood.constants.PlayStateConstants
import com.leepresswood.handlers.SpriteAnimation

/**
 * Created by Lee on 3/27/2016.
 */
class B2DSprite {
    protected Body body
    protected SpriteAnimation animation
    protected float width
    protected float height

    public B2DSprite(Body body){
        this.body = body
    }

    public void setAnimation(TextureRegion[] regions, float delay){
        animation = new SpriteAnimation(regions, delay)
        width = regions[0].getRegionWidth()
        height = regions[0].getRegionHeight()
    }

    public void update(float delta){
        animation.update(delta)
    }

    public void render(SpriteBatch batch){
        batch.begin()
            batch.draw(animation.getFrame(), body.getPosition().x * PlayStateConstants.PIXELS_PER_METER - width / 2f as float, body.getPosition().y * PlayStateConstants.PIXELS_PER_METER - height / 2f as float)
        batch.end()
    }

    public Body getBody(){
        return body
    }

    public Vector2 getPosition(){
        return body.getPosition()
    }

    public float getWidth(){
        return width
    }

    public float getHeight(){
        return height
    }
}
