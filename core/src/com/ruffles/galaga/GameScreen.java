package com.ruffles.galaga;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen implements Screen {

	PlayerShip playership;
	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	ArrayList<Enemy> enemyList;
	ArrayList<Bullet> bulletList;
	ArrayList<EnemyBullet> enemyBulletList;
	ArrayList<Swarm> swarmList;
	
	Swarm swarm;
	Swarm swarm2;
	
	ArrayList<Point2D> pathLeft;
	ArrayList<Point2D> pathRight;
	
	Random rand;
	
	ShapeRenderer renderer;
	
	Texture background;
	int backgroundYpos = 0;
	
	FreeTypeFontGenerator generator;
	private BitmapFont scoreFont;
	private BitmapFont lifeFont;

	int score = 0;
	int lifes = 3;
	
	Texture smallShip;
	
	float attackTimer = 0;
	int numberOfAttackers = 3;
	
	public ArrayList<Point2D> getPathLeft() {
		return pathLeft;
	}
	
	public ArrayList<Point2D> getPathRight() {
		return pathRight;
	}

	public GameScreen(MyGdxGame myGdxGame, int i) {
		this.game = myGdxGame;
	}
	
	public ArrayList<Point2D> generateRandomPath(){
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		
		for(int i = 0; i < 10; i++){
			result.add(new Point2D(rand.nextInt(400), rand.nextInt(200) + 350));
		}
		
		result.add(new Point2D(result.get(0).x, result.get(0).y));
		result = Interpolation.interpolateArray(result, 4);
		return result;
	}
	
	public ArrayList<Enemy> generateEnemies(int count){
		ArrayList<Enemy> result = new ArrayList<Enemy>();
		
		for(int i = 0; i < count; i++){
			result.add(new Enemy(-100, 0, this));
			result.get(i).setCurrentPath(generateRandomPath());
		}
		
		return result;
	}
	

	@Override
	public void show() {
		rand = new Random();

		background = new Texture(Gdx.files.internal("backgrounds/starfield.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		smallShip = new Texture((Gdx.files.internal("blueship/1.png")));
		
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
		pathLeft.add(new Point2D(65, 350));
		
		pathLeft = Interpolation.interpolateArray(pathLeft, 3);
		
		
		
		pathRight = new ArrayList<Point2D>();
		
		pathRight.add(new Point2D(400, 200));
		pathRight.add(new Point2D(350, 250));
		pathRight.add(new Point2D(300, 250));
		pathRight.add(new Point2D(250, 300));
		pathRight.add(new Point2D(250, 350));
		pathRight.add(new Point2D(335, 350));
		
		pathRight = Interpolation.interpolateArray(pathRight, 3);
		
		
		playership = new PlayerShip(MyGdxGame.V_WIDTH / 2 - 45, 20);
		
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
		enemyBulletList = new ArrayList<EnemyBullet>();
		
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
		
//		swarmList = new ArrayList<Swarm>();
//		
//		swarm = new Swarm(5, Direction.RIGHT, this);
//		swarmList.add(swarm);
//		
//		swarm2 = new Swarm(5, Direction.LEFT, this);
//		swarmList.add(swarm2);
		
		enemyList.addAll(generateEnemies(10));
		
		renderer = new ShapeRenderer();
		renderer.setAutoShapeType(true);
		renderer.setColor(Color.RED);
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/timeburnerbold.ttf"));
		FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter1.size = 16;
		parameter2.size = 18;
		scoreFont = generator.generateFont(parameter2);
		lifeFont = generator.generateFont(parameter1);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		
		
		renderer.begin();
		batch.begin();
		batch.setProjectionMatrix(cam.combined);
		
		batch.draw(background, 0, 0, 800, 601, 0, backgroundYpos, 800, 601, false, false);
		batch.draw(smallShip, 360, 3, 35, 35);
		
		renderer.set(ShapeType.Line);
		
		/*
		 * Drawing the playership
		 */
		playership.draw(batch);
		//renderer.rect(playership.getBounds().x, playership.getBounds().y, playership.getBounds().width, playership.getBounds().height);
		
		/*
		 * Drawing the enemies
		 */
		for(int i = 0; i < enemyList.size(); i++){
			enemyList.get(i).draw(batch);
			//renderer.rect(enemyList.get(i).getBounds().x, enemyList.get(i).getBounds().y, enemyList.get(i).getBounds().width, enemyList.get(i).getBounds().height);
		}
		
		/*
		 * Drawing the bullets
		 */
		renderer.setColor(Color.YELLOW);
		for(int i = 0; i < bulletList.size(); i++){
			bulletList.get(i).draw(batch);
			//renderer.rect(bulletList.get(i).getPosX(), bulletList.get(i).getPosY(), bulletList.get(i).bounds.width, bulletList.get(i).bounds.height);
		}
		
		for(int i = 0; i < enemyBulletList.size(); i++){
			enemyBulletList.get(i).draw(batch);
			//renderer.rect(bulletList.get(i).getPosX(), bulletList.get(i).getPosY(), bulletList.get(i).bounds.width, bulletList.get(i).bounds.height);
		}
		
		scoreFont.draw(batch, "Score: " + score, 5, 590);
		lifeFont.draw(batch, "x " + lifes, 400, 20);
		
		batch.end();
		renderer.end();
		
		
		Gdx.graphics.setTitle("Galaga | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}

	float bullettimer = 0;
	float nextAttack = 3;
	
	private void update(float delta) {

		cam.update();
		
		attackTimer += delta;
		
		if(attackTimer > nextAttack && enemyList.size() > 0){
			toggleAttack(rand.nextInt(numberOfAttackers) + 1);
			attackTimer = 0;
			nextAttack = rand.nextFloat() * (5 - 1) + 1;
			//System.out.println("Attack toggled! Next attack in " + nextAttack + " seconds!");
		}
		
		/*
		 * Background loop
		 */
		if(backgroundYpos > Integer.MIN_VALUE){
			backgroundYpos -= 1;
		}
		else{
			backgroundYpos = 0;
		}
		
		
		/*
		 * Input Management
		 */
		if(Gdx.app.getType() == ApplicationType.Desktop){
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
		}
		
		if(Gdx.app.getType() == ApplicationType.Android){
			if(Gdx.input.isTouched()){
				Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
				cam.unproject(touchPos);
				
				playership.setPosX((int) (touchPos.x - playership.getBounds().getWidth() / 2));
				
				if(playership.getPosX() > 430 - playership.getBounds().getWidth()){
					playership.setPosX((int) (430 - playership.getBounds().getWidth()));
				}
				
				if(playership.getPosX() < 0){
					playership.setPosX(0);
				}

			}
		}
		
		/*
		 * Update playership
		 */
		
		playership.update(delta);
		
		/*
		 * Shoot bullets
		 */
		bullettimer += delta;
		
		if(bullettimer > 0.125){
			if(Gdx.input.isKeyPressed(Keys.SPACE) && Gdx.app.getType() == ApplicationType.Desktop){
				bulletList.add(new Bullet(playership.getPosX() + (int)playership.getBounds().width / 2 + 2, playership.getPosY() + 90, 10, this));
			}
			
			if(Gdx.input.isTouched() && Gdx.app.getType() == ApplicationType.Android){
				bulletList.add(new Bullet(playership.getPosX() + (int)playership.getBounds().width / 2 + 2, playership.getPosY() + 90, 10, this));
			}
			bullettimer = 0;
		}
		
		/*
		 * Update enemies
		 */
		for(int i = 0; i < enemyList.size(); i++){
			enemyList.get(i).update(delta);
		}
		
		/*
		 * Updating Bullets
		 */
		for(int i = 0; i < bulletList.size(); i++){
			bulletList.get(i).update(delta);
		}
		
		for(int i = 0; i < enemyBulletList.size(); i++){
			enemyBulletList.get(i).update(delta);
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
		}
		
		/*
		 * Check for enemyBullet / playerShip collision
		 */
		for(int i = 0; i < enemyBulletList.size(); i++){
				if(Intersector.overlaps(enemyBulletList.get(i).bounds, playership.getBounds())){
					playership.setHit(true);
					enemyBulletList.get(i).setHit(true);
				}
		}
		
		/*
		 * Update swarms
		 */
//		for(int i = 0; i < swarmList.size(); i++){
//			swarmList.get(i).update(delta);
//		}
		
	}
	
	private void toggleAttack(int numberOfAttackers) {
		for(int i = 0; i < numberOfAttackers; i++){
			if(enemyList.size() != 0){
				enemyList.get(rand.nextInt(enemyList.size())).attack();
			}
		}
	}

	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}

	public ArrayList<Bullet> getBulletList(){
		return bulletList;
	}
	
	public ArrayList<EnemyBullet> getEnemyBulletList(){
		return enemyBulletList;
	}
	
	@Override
	public void resize(int width, int height) {
		port.update(width, height);
		renderer.setProjectionMatrix(cam.combined);
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
