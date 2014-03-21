package com.zachturk.pong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pong implements ApplicationListener {
	public static final String TAG = Pong.class.getName();
		
	private Renderer renderer;
	private Controller controller;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	@Override
	public void create() {		
//		renderer = new Renderer();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		batch.begin();
		batch.draw(paddle(), 0, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	private Texture paddle() {
		Texture texture = null;
		Pixmap pixmap = new Pixmap(8, 64, Format.RGB888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fill();
		texture = new Texture(pixmap);
		return texture;
	}
}