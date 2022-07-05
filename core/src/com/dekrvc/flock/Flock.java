package com.dekrvc.flock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.dekrvc.flock.screens.Manager;

public class Flock extends ApplicationAdapter {
	public static String TITLE = "Flock";
	private Manager manager;

	@Override
	public void create () {
		manager = new Manager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.07f, 0.07f, 0.07f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.render();
		//System.out.println(Gdx.graphics.getFramesPerSecond());
	}
	
	@Override
	public void dispose () {
		manager.dispose();
	}
}
