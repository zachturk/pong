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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pong implements ApplicationListener {
	public static final String TAG = Pong.class.getName();
		
//	private Renderer renderer;
//	private Controller controller;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Texture paddleTexture;
	private float paddleHeight = 0;
	private Rectangle paddleBounds;
	
	private Texture ballTexture;
	private Vector2 ballSpeed;
	private Vector2 ballPosition;
	private Rectangle ballBounds;
	
	@Override
	public void create() {		
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		
		paddleTexture = paddle();
		paddleBounds = new Rectangle(0, 0, 8f, 64f);
		paddleBounds.x = -Gdx.graphics.getWidth()/2+15;
		
		ballTexture = ball();
		ballSpeed = new Vector2(-1, 0);
		ballPosition = new Vector2(0, 0);
		ballBounds = new Rectangle(0, 0, 8f, 8f);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {		
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		updatePaddle();
		updateBall();
		batch.begin();
		renderBall();
		renderPaddle();
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
			paddleHeight += 500f*Gdx.graphics.getDeltaTime();
		}
		else if (Gdx.input.isKeyPressed(Keys.S)) {
			paddleHeight -= 500f*Gdx.graphics.getDeltaTime();
		}
		paddleHeight = MathUtils.clamp(paddleHeight, -Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/2 - 64);
		paddleBounds.y = paddleHeight;
	}
	
	private void updateBall() {
		ballPosition.x += 300*Gdx.graphics.getDeltaTime()*ballSpeed.x;
		ballPosition.y += 300*Gdx.graphics.getDeltaTime()*ballSpeed.y;
		
		if (ballBounds.overlaps(paddleBounds)) {
			ballSpeed.x *= -1;
			ballPosition.x = paddleBounds.x+8;
			if (Gdx.input.isKeyPressed(Keys.W)) {
				ballSpeed.y += 1;
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				ballSpeed.y -= 1;
			} 
		}
		if (ballPosition.x < -Gdx.graphics.getWidth()/2) {
			//reset
			ballPosition.set(0, 0);
			ballSpeed.set(-1, 0);
		} else if (ballPosition.x > Gdx.graphics.getWidth()/2) {
			//bounce
			MathUtils.clamp(ballPosition.x, -Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/2);
			ballSpeed.x *= -1;
		}
		if (ballPosition.y < -Gdx.graphics.getHeight()/2 || ballPosition.y > Gdx.graphics.getHeight()/2) {
			MathUtils.clamp(ballPosition.y, -Gdx.graphics.getHeight()/2, Gdx.graphics.getHeight()/2);
			ballSpeed.y *= -1;
		}
		
		ballBounds.setPosition(ballPosition);
	}
	
	private void renderPaddle() {
		batch.draw(paddleTexture, -Gdx.graphics.getWidth()/2+15, paddleHeight);
	}
	
	private void renderBall() {
		batch.draw(ballTexture, ballPosition.x, ballPosition.y);
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