package com.zachturk.pong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Pong implements ApplicationListener {
	public static final String TAG = Pong.class.getName();
		
	private Renderer renderer;
	private Controller controller;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private float paddle_height = 0;
	
	@Override
	public void create() {		
//		renderer = new Renderer();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
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
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		updatePaddle();
		batch.begin();
		renderPaddle();
		renderBall();
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
	
	private void updatePaddle() {
		if (Gdx.input.isKeyPressed(Keys.W)) {
			paddle_height += 500f*Gdx.graphics.getDeltaTime();
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			paddle_height -= 500f*Gdx.graphics.getDeltaTime();
		}
		paddle_height = MathUtils.clamp(paddle_height, -Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/2 - 64);
	}
	
	private void renderPaddle() {
		batch.draw(paddle(), -Gdx.graphics.getWidth()/2+15, paddle_height);
	}
	
	private void renderBall() {
		batch.draw(ball(), 0, 0);
	}
	
	private Texture ball() {
		Texture texture = null;
		Pixmap pixmap = new Pixmap(16, 16, Format.RGB888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillCircle(4, 4, 4);
		texture = new Texture(pixmap);
		return texture;
	}
}