package com.ruffles.galaga;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


/*
 * Abstract class, which models enemies in general
 */
public abstract class Enemy {

	int hitpoints;
	
	
	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	
	ShapeRenderer shaperenderer;
	
	public enum State {FLYINGLEFT, FLYINGRIGHT, IDLE, ARRIVING};
	State currentState = State.FLYINGLEFT;
	
	public Enemy(int xSpawn, int ySpawn){
		bounds = new Rectangle(xSpawn, ySpawn, 20, 20);
		
		posX = xSpawn;
		posY = ySpawn;
		
		shaperenderer = new ShapeRenderer();
		shaperenderer.setAutoShapeType(true);
		shaperenderer.begin();
		shaperenderer.setColor(Color.RED);
		shaperenderer.end();	
	}
	
	float timer = 0;
	public void update(float delta){
		
		timer += delta;
		if(timer > 1){
			
			if(currentState == State.FLYINGLEFT)
				setPosX(getPosX() - 10);
			if(currentState == State.FLYINGRIGHT)
				setPosX(getPosX() + 10);
			
			
			timer = 0;
		}
	}
	
	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
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
	
}
