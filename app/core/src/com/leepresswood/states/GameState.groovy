package com.leepresswood.states

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.leepresswood.NGame

/**
 * Created by Lee on 3/23/2016.
 */
class abstract class GameState {
    protected GameStateManager gsm;
    protected NGame game;

    protected SpriteBatch batch;
    protected OrthographicCamera game_cam;
    protected OrthographicCamera hud_cam;

    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        game = gsm.game;
        batch = game.getBatch();
        game_cam = game.getGameCam();
        hud_cam = game.getHudCam();
    }
}
