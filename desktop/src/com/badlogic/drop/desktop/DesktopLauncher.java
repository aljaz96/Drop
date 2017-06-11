package com.badlogic.drop.desktop;

import com.badlogic.drop.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Trash Control";
        config.width = 800;
        config.height = 480;
		config.useGL30 = true;
		new LwjglApplication(new Screen(), config);
	}
}
