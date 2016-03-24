package com.leepresswood.states

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.leepresswood.handlers.GameStateManager

/**
 * Created by Lee on 3/23/2016.
 */
public class Play extends GameState{
    private BitmapFont font = new BitmapFont();

    protected Play(GameStateManager gsm) {
        super(gsm)
    }

    @Override
    void handleInput() {

    }

    @Override
    void update(float delta) {

    }

    @Override
    void render() {
        batch.setProjectionMatrix(game_cam.combined);
        batch.begin();
            font.draw(batch, "Play State", 100, 100);
        batch.end();
    }

    @Override
    void dispose() {

    }
}
