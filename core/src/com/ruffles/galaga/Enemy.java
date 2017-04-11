package com.ruffles.galaga;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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

	int hitpoints;
	
	
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
	TextureAtlas atlas;
	private float stateTimer = 0;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Enemy(int xSpawn, int ySpawn, GameScreen gameScreen){
		
		super(new Texture(Gdx.files.internal("redship/spaceship_enemy_start.png")));
		setBounds(50, 50, (float) (50), (float) (50));
		
		bounds = new Rectangle(xSpawn, ySpawn, 50, 50);
		
		posX = xSpawn;
		posY = ySpawn;
		
		
		this.gameScreen = gameScreen;
		
		atlas = new TextureAtlas(Gdx.files.internal("redship/basicenemy.pack"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		frames.add(atlas.findRegion("spaceship_enemy_start"));
		frames.add(atlas.findRegion("1"));
		frames.add(atlas.findRegion("2"));
		frames.add(atlas.findRegion("3"));
		frames.add(atlas.findRegion("4"));
		frames.add(atlas.findRegion("5"));
		frames.add(atlas.findRegion("6"));
		frames.add(atlas.findRegion("7"));
		frames.add(atlas.findRegion("8"));
		
		shipDefault = new Animation(0.25f, frames);
		frames.clear();
	}
	
	float timer = 0;
	float timer1 = 0;


	private ArrayList<Point2D> currentPath = new ArrayList<Point2D>();
	int pathStep = 0;
	
	public void update(float delta){
		
		setRegion((TextureRegion) shipDefault.getKeyFrame(stateTimer, true));
		setPosition(posX, posY);
		stateTimer += delta;
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
			gameScreen.enemyList.remove(this);
			gameScreen.setScore(gameScreen.getScore() + pointsOnKill);
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
