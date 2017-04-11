package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PlayerShip extends Sprite{

	Rectangle bounds;

	int posX = 0;
	int posY = 0;
	
	ShapeRenderer shaperenderer;
	
	
	@SuppressWarnings("rawtypes")
	Animation shipDefault;
	TextureAtlas atlas;
	private float stateTimer = 0;
	
	boolean hit;
	
	public ShapeRenderer getShaperenderer() {
		return shaperenderer;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PlayerShip(int xSpawn, int ySpawn){
		
		super(new Texture(Gdx.files.internal("blueship/1.png")));
		setBounds(50, 50, (float) (75), (float) (75));
		
		posX = xSpawn;
		posY = ySpawn;
		
		atlas = new TextureAtlas(Gdx.files.internal("blueship/blueship.pack"));
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
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
		
		
		bounds = new Rectangle(0, 0, 65, 65);
	}
	
	public void update(float delta){
		setRegion((TextureRegion) shipDefault.getKeyFrame(stateTimer, true));
		setPosition(posX, posY + 20);
		
		bounds.setPosition(posX + 5, posY + 20);
		
		stateTimer += delta;
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
}
