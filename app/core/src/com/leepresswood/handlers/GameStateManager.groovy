package com.leepresswood.handlers

import com.leepresswood.NGame
import com.leepresswood.constants.GameStateManagerConstants
import com.leepresswood.states.GameState
import com.leepresswood.states.Play
import com.sun.media.jfxmedia.events.PlayerEvent

/**
 * Created by Lee on 3/23/2016.
 */
public class GameStateManager {
    private NGame game;
    private Stack<GameState> game_states;

    public GameStateManager(NGame game){
        this.game = game;
        game_states = new Stack<>();
        pushState(GameStateManagerConstants.PLAY);
    }

    public void update(float delta){
        game_states.peek().update(delta);
    }

    public void render(){
        game_states.peek().render();
    }

    public NGame getGame(){
        return this.game;
    }

    private GameState getState(int state){
        switch(state){
            case GameStateManagerConstants.PLAY:
                return new Play(this);
            default:
                return null;
        }
    }

    public void setState(int state){
        popState();
        pushState(state);
    }

    public void pushState(int state){
        game_states.push(getState(state));
    }

    public void popState(){
        GameState state = game_states.pop();
        state.dispose();
    }
}
