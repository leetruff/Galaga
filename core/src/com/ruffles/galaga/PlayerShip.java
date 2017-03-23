package com.ruffles.galaga;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class PlayerShip {

	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	
	ShapeRenderer shaperenderer;
	
	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
	}

	public PlayerShip(){
		bounds = new Rectangle(0, 0, 30, 30);
		shaperenderer = new ShapeRenderer();
		shaperenderer.setAutoShapeType(true);
		shaperenderer.begin();
		shaperenderer.setColor(Color.YELLOW);
		shaperenderer.end();
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
