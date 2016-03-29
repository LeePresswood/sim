package com.leepresswood.handlers

import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * Created by Lee on 3/27/2016.
 */
class SpriteAnimation {
    private TextureRegion[] frames
    private float time
    private float delay             //Time between each frame.
    private int current_frame
    private int times_played        //How many times we've gone through this animation.

    public SpriteAnimation(){

    }

    public SpriteAnimation(TextureRegion[] frames){
        this(frames, 1f / 12f as float)
    }

    public SpriteAnimation(TextureRegion[] frames, float delay){
        this.frames = frames
        this.delay = delay
        time = 0f;
        current_frame = 0
        times_played = 0
    }

    public void update(float delta){
        if (delay <= 0){
            return
        }

        time += delta
        while(time >= delay){
            step()
        }
    }

    private void step(){
        time -= delay
        current_frame++
        if (current_frame == frames.length){
            current_frame = 0
            times_played++
        }
    }

    public TextureRegion getFrame(){
        return frames[current_frame]
    }

    public int getTimesPlayed(){
        return times_played
    }
}
