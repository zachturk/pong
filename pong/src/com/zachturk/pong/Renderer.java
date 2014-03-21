package com.zachturk.pong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Renderer {
	public static final String TAG = Renderer.class.getName();
	
	private OrthographicCamera camera;
	private SpriteBatch batch;

	/**
	 * Renderer handles graphical output
	 */
	public Renderer() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
	}
	
	public Renderer(int width, int height) {
		camera = new OrthographicCamera(1, height/width);
	}
	
	public void setBatch(SpriteBatch batch) {
		if (batch != null)
			this.batch = batch;
	}

	public void render(SpriteBatch batch) {
		batch.begin();
	}
	
	public void resize(int width, int height) {
		
	}
	
}
