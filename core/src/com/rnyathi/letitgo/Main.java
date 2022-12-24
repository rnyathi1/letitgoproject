package com.rnyathi.letitgo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rnyathi.letitgo.Views.*;

public class Main extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	public static AssetManager manager;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_HEAD_BIT = 64;
	public SpriteBatch batch;
	private AppPreferences preferences;
	private PreferencesScreen preferencesScreen;
	private LevelOneScreen levelOneScreen;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private LevelTwoScreen levelTwoScreen;
	private LevelThreeScreen levelThreeScreen;
	private GameOverScreen gameOverScreen;
	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int LEVELONE = 2;
	public final static int LEVELTWO = 3;
	public final static int LEVELTHREE = 4;
	public final static int ENDGAME = 5;

	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(menuScreen == null){
					menuScreen = new MenuScreen(this);
				}
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null){
					preferencesScreen = new PreferencesScreen(this);

				}
				this.setScreen(preferencesScreen);
				break;
			case LEVELONE:
				if(levelOneScreen == null){
					levelOneScreen = new LevelOneScreen(this);

				}
				this.setScreen(levelOneScreen);
				break;
			case LEVELTWO:
				if(levelTwoScreen == null){
					levelTwoScreen = new LevelTwoScreen(this);

				}
				this.setScreen(levelTwoScreen);
				break;
			case LEVELTHREE:
				if(levelThreeScreen == null){
					levelThreeScreen = new LevelThreeScreen(this);

				}
				this.setScreen(levelThreeScreen);
				break;
			case ENDGAME:
				if(gameOverScreen == null){
					gameOverScreen = new GameOverScreen(this);

				}
				this.setScreen(gameOverScreen);
				break;
		}
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.finishLoading();

		loadingScreen = new LoadingScreen(this);
		preferences = new AppPreferences();
		setScreen(loadingScreen);

	}

	@Override
	public void render () {

		super.render();
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
