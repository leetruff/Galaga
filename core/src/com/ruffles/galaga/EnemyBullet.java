package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class EnemyBullet extends Sprite {

	
	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	int speed;
	boolean hit = false;
	GameScreen gameScreen;
	
	public EnemyBullet(int xSpawn, int ySpawn, int speed, GameScreen gameScreen){
		
		super(Assets.enemyBullet);
		setBounds(xSpawn, ySpawn, 50, 50);
		bounds = new Rectangle(xSpawn, ySpawn, 6, 12);
		flip(false, true);
		
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
		
		setPosition(posX - 23, posY - 20);
		bounds.setPosition(getPosX(), getPosY());
		
		if(posY > 600 || posY < -100 || hit){
			gameScreen.getEnemyBulletList().remove(this);
		}
	}

	public void setHit(boolean b) {
		hit = b;
	}
	
}
