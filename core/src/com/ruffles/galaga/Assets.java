package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Assets {

	@SuppressWarnings("rawtypes")
	static Animation shipDefault;
	static TextureAtlas atlas;
	static Texture flashTexture;
	static Texture redShip;
	
	static Texture friendlyBullet;
	static Texture enemyBullet;
	
	static Texture explosionSheet;
	@SuppressWarnings("rawtypes")
	static Animation explosionAnimation;
	
	static Texture background;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void load(){
		redShip = new Texture(Gdx.files.internal("redship/spaceship_enemy_start.png"));
		flashTexture = new Texture(Gdx.files.internal("redship/spaceship_enemy_red_flash.png"));
		
		
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
		
		friendlyBullet = new Texture(Gdx.files.internal("blueship/bullet.png"));
		enemyBullet = new Texture(Gdx.files.internal("redship/bullet_red.png"));
		
		
		explosionSheet = new Texture(Gdx.files.internal("explosion/exp.png"));
		
		TextureRegion[][] tmp = TextureRegion.split(explosionSheet, 
				explosionSheet.getWidth() / 4,
				explosionSheet.getHeight() / 4);
		
		TextureRegion[] explosionFrames = new TextureRegion[4 * 4];
		int index = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				explosionFrames[index++] = tmp[i][j];
			}
		}
		
		
		explosionAnimation = new Animation<TextureRegion>(0.025f, explosionFrames);
		
		background = new Texture(Gdx.files.internal("backgrounds/starfield.png"));
	}
}
