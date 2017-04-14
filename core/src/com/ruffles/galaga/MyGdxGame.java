package com.ruffles.galaga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	
	static int V_WIDTH = 430;
	static int V_HEIGHT = 600;
	
	@Override
	public void create () {
		Assets.load();
		//setScreen(new MainMenu(this, 0));
		//setScreen(new AddHighscoreNameScreen(this, 0, 1520));
		//setScreen(new HighscoreScreen(this, 0));
		setScreen(new GameoverScreen(this, 0, 15202));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
