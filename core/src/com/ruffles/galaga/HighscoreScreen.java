package com.ruffles.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class HighscoreScreen implements Screen {

	
	MyGdxGame game;
	OrthographicCamera cam;
	StretchViewport port;
	SpriteBatch batch;
	
	Texture background;
	int backgroundYpos = 0;
	
	
	Stage stage;
	
	ImageButtonStyle exitButtonStyle;
	
	ImageButton exitButton;
	
	Skin skin;
	
	FreeTypeFontGenerator generator;
	
	BitmapFont scoreFont;
	
	String highscoreName1;
	String highscoreName2;
	String highscoreName3;
	
	int highscoreScore1;
	int highscoreScore2;
	int highscoreScore3;
	
	Texture goldmedal;
	Texture silvermedal;
	Texture bronzemedal;
	
	public HighscoreScreen(MyGdxGame game, int backgroundYpos) {
		this.game = game;
		this.backgroundYpos = backgroundYpos;
	}
	
	@Override
	public void show() {
		cam = new OrthographicCamera();
		port = new StretchViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, cam);
		cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
		
		batch = new SpriteBatch();
		
		background = Assets.background;
		background.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		
		stage = new Stage(port, batch);
		Gdx.input.setInputProcessor(stage);
		
		
		exitButtonStyle = new ImageButtonStyle();
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/timeburnerbold.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 25;
		
		
		Drawable exitUp = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/exit_buttons.png"))));
		Drawable exitDown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttons/exit_buttons_pressed.png"))));
		exitButtonStyle = new ImageButtonStyle();
		exitButtonStyle.over = exitDown;
		exitButtonStyle.up = exitUp;
		
		exitButton = new ImageButton(exitButtonStyle);
		exitButton.setSize(250, 70);
		exitButton.setPosition(MyGdxGame.V_WIDTH / 2 - exitButton.getWidth() / 2, 20);
		stage.addActor(exitButton);
		
		exitButton.addListener(new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	game.setScreen(new MainMenu(game, backgroundYpos));
	        }
	    });
		
		/*
		 * Font fuer Schriftzuege
		 */
		
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter2.size = 25;
		scoreFont = generator.generateFont(parameter2);
		
		/*
		 * Medaillen Texturen laden
		 */
		
		goldmedal = new Texture(Gdx.files.internal("medals/goldmedalsmall.png"));
		silvermedal = new Texture(Gdx.files.internal("medals/silvermedalsmall.png"));
		bronzemedal = new Texture(Gdx.files.internal("medals/bronzemedalsmall.png"));
		
		/*
		 * Highscores aus .prefs auslesen
		 */
		
//		highscoreName1 = "Lars";
//		highscoreName2 = "Lars";
//		highscoreName3 = "Lars";
//		
//		highscoreScore1 = 16512;
//		highscoreScore2 = 12941;
//		highscoreScore3 = 5122;
		
		if(IOController.prefs.getString("highscoreName1") != null){
			highscoreName1 = IOController.prefs.getString("highscoreName1", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore1") > 0){
			highscoreScore1 = IOController.prefs.getInteger("highscoreScore1");
		}
		
		if(IOController.prefs.getString("highscoreName2") != null){
			highscoreName2 = IOController.prefs.getString("highscoreName2", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore2") > 0){
			highscoreScore2 = IOController.prefs.getInteger("highscoreScore2");
		}
		
		if(IOController.prefs.getString("highscoreName3") != null){
			highscoreName3 = IOController.prefs.getString("highscoreName3", "-1");
		}
		
		if(IOController.prefs.getInteger("highscoreScore3") > 0){
			highscoreScore3 = IOController.prefs.getInteger("highscoreScore3");
		}
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(background, 0, 0, 800, 601, 0, backgroundYpos, 800, 601, false, false);
		
		if(highscoreName1 != "-1"){
			scoreFont.draw(batch, highscoreName1, 85, 500);
			scoreFont.draw(batch, highscoreScore1 + "", 325, 500);
			
		}
		scoreFont.draw(batch, "-", 230, 500);
		batch.draw(goldmedal, 55, 477);
		
		if(highscoreName2 != "-1"){
			scoreFont.draw(batch, highscoreName2, 85, 450);
			scoreFont.draw(batch, highscoreScore2 + "", 325, 450);
			
		}
		scoreFont.draw(batch, "-", 230, 450);
		batch.draw(silvermedal, 55, 427);
		
		if(highscoreName3 != "-1"){
			scoreFont.draw(batch, highscoreName3, 85, 400);
			scoreFont.draw(batch, highscoreScore3 + "", 325, 400);
			
		}
		scoreFont.draw(batch, "-", 230, 400);
		batch.draw(bronzemedal, 55, 377);
		
		batch.end();
		
		//TODO Buttons
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