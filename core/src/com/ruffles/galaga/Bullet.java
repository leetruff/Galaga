package com.ruffles.galaga;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {

	
	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	int speed;
	
	ShapeRenderer shaperenderer;
	GameScreen gameScreen;
	
	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
	}
	
	public Bullet(int xSpawn, int ySpawn, int speed, GameScreen gameScreen){
		bounds = new Rectangle(xSpawn, ySpawn, 5, 12);
		shaperenderer = new ShapeRenderer();
		shaperenderer.setAutoShapeType(true);
		shaperenderer.begin();
		shaperenderer.setColor(Color.PINK);
		shaperenderer.end();
		
		this.posX = xSpawn;
		this.posY = ySpawn;
		this.speed = speed;
		
		this.gameScreen = gameScreen;
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

	public void update(float delta) {
		posY += speed;
		if(posY > 600){
			gameScreen.getBulletList().remove(this);
		}
	}
	
}
