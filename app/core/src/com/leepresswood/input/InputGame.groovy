package com.leepresswood.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.leepresswood.constants.InputGameConstants

/**
 * Created by Lee on 3/24/2016.
 */
class InputGame extends InputAdapter{
    @Override
    boolean keyDown(int keycode) {
        if (keycode == Input.Keys.Z){
            InputGameConstants.setKey(InputGameConstants.BUTTON1, true)
        }
        if (keycode == Input.Keys.X){
            InputGameConstants.setKey(InputGameConstants.BUTTON2, true)
        }

        return true
    }

    @Override
    boolean keyUp(int keycode) {
        if (keycode == Input.Keys.Z){
            InputGameConstants.setKey(InputGameConstants.BUTTON1, false)
        }
        if (keycode == Input.Keys.X){
            InputGameConstants.setKey(InputGameConstants.BUTTON2, false)
        }

        return true
    }
}
