package com.leepresswood;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leepresswood.constants.ApplicationConstants;
import com.leepresswood.constants.InputGameConstants;
import com.leepresswood.handlers.GameStateManager;
import com.leepresswood.handlers.ResourceManager;
import com.leepresswood.input.InputGame;
import com.sun.org.apache.xpath.internal.functions.FuncFalse;

public class NGame extends ApplicationAdapter {
	private float time_accumulator;

	private SpriteBatch batch;
	private OrthographicCamera game_cam;
	private OrthographicCamera hud_cam;

	private GameStateManager gsm;

	public static ResourceManager resources;

	@Override
	public void create() {
		Gdx.input.setInputProcessor(new InputGame());

		resources = new ResourceManager();
		resources.loadTexture("images/bunny.png", "bunny");

		batch = new SpriteBatch();
		game_cam = new OrthographicCamera();
		game_cam.setToOrtho(false, ApplicationConstants.V_WIDTH, ApplicationConstants.V_HEIGHT);
		hud_cam = new OrthographicCamera();
		hud_cam.setToOrtho(false, ApplicationConstants.V_WIDTH, ApplicationConstants.V_HEIGHT);

		gsm = new GameStateManager(this);
	}

	@Override
	public void render() {
		time_accumulator += Gdx.graphics.getDeltaTime();
		while(time_accumulator >= ApplicationConstants.STEP){
			time_accumulator -= ApplicationConstants.STEP;
			gsm.update(ApplicationConstants.STEP);
			gsm.render();
			InputGameConstants.update();
		}
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getGameCam() {
		return game_cam;
	}

	public OrthographicCamera getHudCam() {
		return hud_cam;
	}
}
