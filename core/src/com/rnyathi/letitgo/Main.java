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
	public  static int difficulty = 1;
	public static final float PPM = 100;
	public static AssetManager manager;
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BOUNDARY_BIT = 4;
	public static final short COMPLETION_BIT = 8;
	public static final short ENEMY_BIT = 16;
	public static final short ENEMY_HEAD_BIT = 32;
	public static final short BAT_SPAWNER_BIT = 64;
	public static final short FIREBALL_BIT = 128;
	public static final short BOSS_BIT = 256;
	public boolean levelOneComplete = true;
	public boolean levelTwoComplete = true;
	public int level = 1;


	public SpriteBatch batch;
	private AppPreferences preferences;
	private OptionsScreen preferencesScreen;
	private LevelOneScreen levelOneScreen;
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private LevelTwoScreen levelTwoScreen;
	private LevelThreeScreen levelThreeScreen;
	private CompletedScreen completedScreen;
	private GameOverScreen gameOverScreen;

	public final static int MENU = 0;
	public final static int PREFERENCES = 1;
	public final static int LEVELONE = 2;
	public final static int LEVELTWO = 3;
	public final static int LEVELTHREE = 4;
	public final static int LOADINGSCREEN = 5;
	public final static int COMPLETEDSCREEN = 6;
	public final static int GAMEOVERSCREEN = 7;


	public void changeScreen(int screen){
		switch(screen){
			case MENU:

					menuScreen = new MenuScreen(this);

				this.setScreen(menuScreen);

				break;
			case PREFERENCES:

					preferencesScreen = new OptionsScreen(this);

				this.setScreen(preferencesScreen);
				break;
			case LEVELONE:

				levelOneScreen = new LevelOneScreen(this);


				this.setScreen(levelOneScreen);
				break;
			case LEVELTWO:

				levelTwoScreen = new LevelTwoScreen(this);
				this.setScreen(levelTwoScreen);
				break;
			case LEVELTHREE:
				levelThreeScreen = new LevelThreeScreen(this);
				this.setScreen(levelThreeScreen);
				break;
			case LOADINGSCREEN:

				loadingScreen = new LoadingScreen(this);
				this.setScreen(loadingScreen);
				break;

		}
	}
	public void changeScreen(int screen, String score,String time, int level){
		switch(screen) {
			case COMPLETEDSCREEN:
				completedScreen = new CompletedScreen(this, score, time);
				this.setScreen(completedScreen);
				break;
			case GAMEOVERSCREEN:
				gameOverScreen = new GameOverScreen(this, score, time, level);
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


		preferences = new AppPreferences();
		changeScreen(Main.MENU);

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

	public void addDifficulty(){
		difficulty+=1;
	}
}


