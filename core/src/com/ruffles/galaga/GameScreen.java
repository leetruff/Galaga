package com.ruffles.galaga;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.ruffles.galaga.Enemy.State;
import com.sun.javafx.geom.Point2D;

public class GameScreen implements Screen {

	PlayerShip playership;
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	ArrayList<Enemy> enemyList;
	ArrayList<Bullet> bulletList;
	ArrayList<Swarm> swarmList;
	
	Swarm swarm;
	
	ArrayList<Point2D> pathLeft;
	ArrayList<Point2D> pathRight;
	
	public ArrayList<Point2D> getPathLeft() {
		return pathLeft;
	}
	
	public ArrayList<Point2D> getPathRight() {
		return pathRight;
	}

	public GameScreen(MyGdxGame myGdxGame, int i) {
		this.game = myGdxGame;
	}

	@Override
	public void show() {
		System.out.println("GameScreen shown");
		
		/*
		 * Creation of paths with interpolation
		 * middle of screen 430 / 2 = 215
		 */
		pathLeft = new ArrayList<Point2D>();
		
		pathLeft.add(new Point2D(0, 200));
		pathLeft.add(new Point2D(50, 250));
		pathLeft.add(new Point2D(100, 250));
		pathLeft.add(new Point2D(150, 300));
		pathLeft.add(new Point2D(150, 350));
		pathLeft.add(new Point2D(100, 350));
		
		pathLeft = Interpolation.interpolateArray(pathLeft, 3);
		
		
		playership = new PlayerShip();
		
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		enemyList = new ArrayList<Enemy>();
		
//		enemyList.add(new BasicEnemy(100, 400));
//		enemyList.add(new BasicEnemy(200, 400));
//		enemyList.add(new BasicEnemy(300, 400));
//		enemyList.add(new BasicEnemy(400, 400));
		
		bulletList = new ArrayList<Bullet>();
		
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
		
		swarmList = new ArrayList<Swarm>();
		
		swarm = new Swarm(5, Direction.LEFT, this);
		swarmList.add(swarm);
		
		
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
		
		/*
		 * Drawing the bullets
		 */
		for(int i = 0; i < bulletList.size(); i++){
			bulletList.get(i).getShaperenderer().begin();
			bulletList.get(i).getShaperenderer().set(ShapeType.Filled);
			bulletList.get(i).getShaperenderer().rect(bulletList.get(i).getPosX(), bulletList.get(i).getPosY(), bulletList.get(i).bounds.width, bulletList.get(i).bounds.height);
			bulletList.get(i).getShaperenderer().end();
		}
		
		
		batch.end();
		
		
		Gdx.graphics.setTitle("Galaga | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}

	float bullettimer = 0;
	
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
		 * Shoot bullets
		 */
		bullettimer += delta;
		
		if(bullettimer > 0.125){
			if(Gdx.input.isKeyPressed(Keys.SPACE)){
				bulletList.add(new Bullet(playership.getPosX() + (int)playership.getBounds().width / 2, playership.getPosY() + 10, 10, this));
			}
			bullettimer = 0;
		}
		
		/*
		 * Update enemies
		 */
		
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).getPosX() < 10){
				for(int j = 0; j < enemyList.size(); j++){
					if(enemyList.get(j).getCurrentState() != State.ARRIVING){
						enemyList.get(j).setCurrentState(State.FLYINGRIGHT);
					}
				}
				break;
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).getPosX() > 420 - enemyList.get(i).getBounds().width){
				for(int j = 0; j < enemyList.size(); j++){
					if(enemyList.get(j).getCurrentState() != State.ARRIVING){
						enemyList.get(j).setCurrentState(State.FLYINGRIGHT);
					}
				}
				break;
			}
		}
		
		for(int i = 0; i < enemyList.size(); i++){
			enemyList.get(i).update(delta);
		}
		
		/*
		 * Updating Bullets
		 */
		for(int i = 0; i < bulletList.size(); i++){
			bulletList.get(i).update(delta);
		}
		
		/*
		 * Check for enemy / bullet collision
		 */
		for(int i = 0; i < bulletList.size(); i++){
			for(int j = 0; j < enemyList.size(); j++){
				if(Intersector.overlaps(bulletList.get(i).bounds, enemyList.get(j).getBounds())){
					enemyList.get(j).setHit(true);
					bulletList.get(i).setHit(true);
				}
			}
			bulletList.get(i).update(delta);
		}
		
		/*
		 * Remove hit enemies
		 */
		for(int i = 0; i < enemyList.size(); i++){
			if(enemyList.get(i).hit){
				enemyList.remove(i);
			}
		}
		
		/*
		 * Update swarms
		 */
		for(int i = 0; i < swarmList.size(); i++){
			swarmList.get(i).update(delta);
		}
		
	}

	public ArrayList<Bullet> getBulletList(){
		return bulletList;
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
