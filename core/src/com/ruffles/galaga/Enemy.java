package com.ruffles.galaga;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;


/*
 * Class, which models enemies in general
 */
public class Enemy extends Sprite {

	int hitpoints = 10;
	
	
	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	boolean hit = false;
	
	int pointsOnKill = 100;
	
	
	
	
	public enum State {FLYINGLEFT, FLYINGRIGHT, IDLE, ARRIVING};
	State currentState = State.ARRIVING;
	
	GameScreen gameScreen;
	
	@SuppressWarnings("rawtypes")
	Animation shipDefault;
	@SuppressWarnings("rawtypes")
	Animation explosion;
	TextureAtlas atlas;
	private float stateTimer = 0;
	private float explosionStateTimer = 0;
	Texture flashTexture;
	boolean flashing;

	@SuppressWarnings({ })
	public Enemy(int xSpawn, int ySpawn, GameScreen gameScreen){
		
		super(Assets.redShip);
		flashTexture = Assets.flashTexture;
		setBounds(50, 50, (float) (50), (float) (50));
		
		bounds = new Rectangle(xSpawn, ySpawn, 50, 50);
		
		posX = xSpawn;
		posY = ySpawn;
		
		
		this.gameScreen = gameScreen;
		
		
		shipDefault = Assets.shipDefault;
		explosion = Assets.explosionAnimation;
	}
	
	float timer = 0;
	float timer1 = 0;


	private ArrayList<Point2D> currentPath = new ArrayList<Point2D>();
	private ArrayList<Point2D> normalPath = new ArrayList<Point2D>();
	int pathStep = 0;


	private float flashTimer;
	
	public void update(float delta){
		
		
		if(flashTimer > 0){
			setRegion(flashTexture);
			flashTimer -= delta;
		}
		
		else{
			setRegion((TextureRegion) shipDefault.getKeyFrame(stateTimer, true));
		}
		
		stateTimer += delta;
		setPosition(posX, posY);
		timer += delta;
		
		if(timer > 0.04){
			if(pathStep < currentPath.size()){
				posX = (int) currentPath.get(pathStep).x;
				posY = (int) currentPath.get(pathStep).y;
				pathStep++;
				
				if(pathStep >= currentPath.size()){
					pathStep = 0;
				}
			}
			timer = 0;
		}
		
		bounds.setPosition(getPosX(), getPosY());
		
		
		
		if(hit){
			hitpoints--;
			
			
			if(hitpoints <= 0){
				
				setRegion((TextureRegion)explosion.getKeyFrame(explosionStateTimer, false));
				explosionStateTimer += delta;
				
				if(explosion.isAnimationFinished(explosionStateTimer)){
					gameScreen.enemyList.remove(this);
					gameScreen.setScore(gameScreen.getScore() + pointsOnKill);
				}
			}
			
			else{
				flashTimer = 0.10f;
				hit = false;
			}
		}
		
		
		/*
		 * End flyattack and return to normal path
		 */
		if(posY < 50){
			currentPath = normalPath;
			pathStep = 0;
		}
		
	}
	
	public int getPointsOnKill(){
		return pointsOnKill;
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setHit(boolean b) {
		hit = b;
	}

	public void setCurrentPath(ArrayList<Point2D> path){
		currentPath = path;
		normalPath = path;
	}

	public void attack() {
		gameScreen.getEnemyBulletList().add(new EnemyBullet(this.posX, this.posY, -7, gameScreen));
	}

	public void flyAttack(int posX2, int posY2) {
		ArrayList<Point2D> attackPath = new ArrayList<Point2D>();
		
		attackPath.add(new Point2D(posX, posY));
		attackPath.add(new Point2D(posX2, posY2));
		currentPath = Interpolation.interpolateArray(attackPath, 5);
		pathStep = 0;
	}
	
//	@SuppressWarnings("unchecked")
//	public void startFlyInLeft(int offset) {
//		currentPath = (ArrayList<Point2D>) gameScreen.getPathLeft().clone();
//		currentPath.add(new Point2D((currentPath.get(currentPath.size()-1).x + (offset * 30)), currentPath.get(currentPath.size()-1).y));
//	}
//
//
//	@SuppressWarnings("unchecked")
//	public void startFlyInRight(int offset) {
//		currentPath = (ArrayList<Point2D>) gameScreen.getPathRight().clone();
//		currentPath.add(new Point2D((currentPath.get(currentPath.size()-1).x - (offset * 30)), currentPath.get(currentPath.size()-1).y));
//	}
	
}
