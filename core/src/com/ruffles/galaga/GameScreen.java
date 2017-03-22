package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen {

	PlayerShip playership;
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	public GameScreen(MyGdxGame myGdxGame, int i) {
		this.game = myGdxGame;
	}

	@Override
	public void show() {
		System.out.println("GameScreen shown");
		playership = new PlayerShip();
		
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		playership.getShaperenderer().begin();
		playership.getShaperenderer().rect(playership.getPosX(), playership.getPosY(), playership.getBounds().width, playership.getBounds().height);
		playership.getShaperenderer().end();
		batch.end();
	}

	private void update(float delta) {

		if(Gdx.input.isKeyPressed(Keys.D)){
			playership.setPosX(playership.getPosX() + 7);
			
			if(playership.getPosX() > 490 - playership.getBounds().getWidth()){
				playership.setPosX((int) (490 - playership.getBounds().getWidth()));
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			playership.setPosX(playership.getPosX() - 7);
			
			if(playership.getPosX() < 0){
				playership.setPosX(0);
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
