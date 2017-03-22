package com.ruffles.galaga;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen {

	PlayerShip playership;
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	ArrayList<Enemy> enemyList;
	
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
		
		enemyList = new ArrayList<Enemy>();
		
		enemyList.add(new BasicEnemy(200, 400));
		
//		ArrayList<Integer> testvalues = new ArrayList<Integer>();
//		testvalues.add(1);
//		testvalues.add(34);
//		testvalues.add(67);
//		testvalues.add(112);
//		testvalues.add(199);
//		testvalues.add(213);
//		
//		testvalues = Interpolation.interpolateArray(testvalues, 4);
//		
//		for (int i = 0; i < testvalues.size(); i++) {
//			System.out.println(testvalues.get(i));
//		}
		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		/*
		 * Drawing the playership
		 */
		playership.getShaperenderer().begin();
		playership.getShaperenderer().set(ShapeType.Filled);
		playership.getShaperenderer().rect(playership.getPosX(), playership.getPosY(), playership.getBounds().width, playership.getBounds().height);
		playership.getShaperenderer().end();
		
		/*
		 * Drawing the enemies
		 */
		
		for(int i = 0; i < enemyList.size(); i++){
			enemyList.get(i).shaperenderer.begin();
			enemyList.get(i).getShaperenderer().set(ShapeType.Filled);
			enemyList.get(i).getShaperenderer().rect(enemyList.get(i).getPosX(), enemyList.get(i).getPosY(), enemyList.get(i).getBounds().width, enemyList.get(i).getBounds().height);
			enemyList.get(i).getShaperenderer().end();
		}
		
		batch.end();
		
		
		Gdx.graphics.setTitle("Galaga | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}

	private void update(float delta) {

		/*
		 * Input Management
		 */
		if(Gdx.input.isKeyPressed(Keys.D)){
			playership.setPosX(playership.getPosX() + 7);
			
			if(playership.getPosX() > 430 - playership.getBounds().getWidth()){
				playership.setPosX((int) (430 - playership.getBounds().getWidth()));
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			playership.setPosX(playership.getPosX() - 7);
			
			if(playership.getPosX() < 0){
				playership.setPosX(0);
			}
		}
		
		/*
		 * Update enemies
		 */
		
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).getPosX() < 0){
				//TODO
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			enemyList.get(i).update(delta);
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
