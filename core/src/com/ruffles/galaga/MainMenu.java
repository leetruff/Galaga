package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenu implements Screen{

	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Texture background;
	int backgroundYpos = 0;
	
	
	Stage stage;
	
	ImageButtonStyle playButtonStyle;
	
	ImageButton playButton;
	ImageButton highscoreButton;
	ImageButton optionsButton;
	
	Skin skin;
	private TextureAtlas atlas;
	
	FreeTypeFontGenerator generator;
	private ImageButtonStyle optionsButtonStyle;
	private ImageButtonStyle creditButtonStyle;
	private ImageButton creditsButton;
	private ImageButtonStyle exitButtonStyle;
	private ImageButton exitButton;
	
	
	public MainMenu(MyGdxGame game, int backgroundYpos) {
		this.game = game;
		this.backgroundYpos = backgroundYpos;
	}
	

	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		
		batch = new SpriteBatch();
		
		background = new Texture(Gdx.files.internal("backgrounds/starfield.png"));
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		
		Drawable playUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/play_buttons.png"))));
		Drawable playDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/play_buttons_pressed_blue.png"))));
		playButtonStyle = new ImageButtonStyle();
		playButtonStyle.over = playDown;
		playButtonStyle.up = playUp;
		
		playButton = new ImageButton(playButtonStyle);
		playButton.setSize(320, 140);
		playButton.setPosition(MyGdxGame.V_WIDTH / 2 - playButton.getWidth() / 2, 260);
		stage.addActor(playButton);
		
		
		
		
		
		Drawable optionsUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/optionst_buttons.png"))));
		Drawable optionsDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/optionst_buttons_pressed.png"))));
		optionsButtonStyle = new ImageButtonStyle();
		optionsButtonStyle.over = optionsDown;
		optionsButtonStyle.up = optionsUp;
		
		optionsButton = new ImageButton(optionsButtonStyle);
		optionsButton.setSize(320, 140);
		optionsButton.setPosition(MyGdxGame.V_WIDTH / 2 - optionsButton.getWidth() / 2, 180);
		stage.addActor(optionsButton);
		
		
		
		
		
		Drawable creditsUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/creditst_buttons.png"))));
		Drawable creditsDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/creditst_buttons_pressed.png"))));
		creditButtonStyle = new ImageButtonStyle();
		creditButtonStyle.over = creditsDown;
		creditButtonStyle.up = creditsUp;
		
		creditsButton = new ImageButton(creditButtonStyle);
		creditsButton.setSize(320, 140);
		creditsButton.setPosition(MyGdxGame.V_WIDTH / 2 - creditsButton.getWidth() / 2, 100);
		stage.addActor(creditsButton);
		
		
		
		
		
		Drawable exitUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/exit_buttons.png"))));
		Drawable exitDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/exit_buttons_pressed.png"))));
		exitButtonStyle = new ImageButtonStyle();
		exitButtonStyle.over = exitDown;
		exitButtonStyle.up = exitUp;
		
		exitButton = new ImageButton(exitButtonStyle);
		exitButton.setSize(320, 140);
		exitButton.setPosition(MyGdxGame.V_WIDTH / 2 - exitButton.getWidth() / 2, 20);
		stage.addActor(exitButton);
		
		
		playButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	Gdx.input.setInputProcessor(null);
	            game.setScreen(new GameScreen(game, backgroundYpos));
	        }
	    });
		
		optionsButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	//TODO
	        }
	    });
		
		creditsButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	//TODO
	        }
	    });
		
		exitButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	Gdx.app.exit();
	        }
	    });
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 800, 601, 0, backgroundYpos, 800, 601, false, false);
		batch.end();
		
		stage.act();
		stage.draw();
		
		Gdx.graphics.setTitle("Galaga | " + Gdx.graphics.getFramesPerSecond() + " FPS");
	}
	
	private void update(float delta) {
		cam.update();
		
		/*
		 * Background loop
		 */
		if(backgroundYpos > Integer.MIN_VALUE){
			backgroundYpos -= 1;
		}
		else{
			backgroundYpos = 0;
		}
		
		
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
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
