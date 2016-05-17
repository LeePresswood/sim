package com.leepresswood.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

/**
 * Created by Lee on 3/27/2016.
 */
class ResourceManager {
    private HashMap<String, Texture> textures

    public ResourceManager(){
        textures = new HashMap<>()
        loadTexture("images/bunny.png", "bunny")
        loadTexture("images/crystal.png", "crystal")
        loadTexture("images/hud.png", "hud")
    }

    public void loadTexture(String path, String key){
        Texture texture = new Texture(Gdx.files.internal(path))
        textures.put(key, texture)
    }

    public Texture getTexture(String key){
        return textures.get(key)
    }

    public void disposeTexture(String key){
        textures.get(key)?.dispose()
    }

    public void disposeAllTextures(){
        for(Texture texture : textures.values()){
            texture.dispose()
        }
    }
}
