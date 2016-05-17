package com.leepresswood.handlers

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leepresswood.NGame
import com.leepresswood.entities.Player

/**
 * Created by Lee on 3/29/2016.
 */
public class HUD {
    private Player player
    private TextureRegion[] blocks

    public HUD(Player player){
        this.player = player

        Texture texture = NGame.resources.getTexture("hud")
        blocks = new TextureRegion[3]
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16)
        }
    }

    public void render(SpriteBatch batch){

    }
}
