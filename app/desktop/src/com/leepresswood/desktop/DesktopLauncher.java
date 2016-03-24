package com.leepresswood.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leepresswood.NGame;
import com.leepresswood.constants.ApplicationConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = ApplicationConstants.TITLE;
		config.width = ApplicationConstants.SCALE * ApplicationConstants.V_WIDTH;
		config.height = ApplicationConstants.SCALE * ApplicationConstants.V_HEIGHT;
		config.resizable = false;

		new LwjglApplication(new NGame(), config);
	}
}
