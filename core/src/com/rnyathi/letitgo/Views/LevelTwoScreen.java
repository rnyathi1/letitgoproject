package com.rnyathi.letitgo.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rnyathi.letitgo.AppPreferences;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Scenes.Hud;
import com.rnyathi.letitgo.Sprites.Enemy;
import com.rnyathi.letitgo.Sprites.Player;
import com.rnyathi.letitgo.Tools.B2WorldCreator;
import com.rnyathi.letitgo.Tools.WorldContactListener;

public class LevelTwoScreen implements Screen {

    public LevelTwoScreen(Main main){

        atlas = new TextureAtlas("player_enemies.txt");
        parent = main;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport((Main.V_WIDTH + 200) / Main.PPM , (Main.V_HEIGHT + 207) / Main.PPM, gamecam);
        hud = new Hud(parent.batch);
        hud.setLevelLabel("2");
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Main.PPM);
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2,0);
        world = new World(new Vector2(0,-10) , true);
        b2dr = new Box2DDebugRenderer();
        pref = new AppPreferences();
        creator = new B2WorldCreator(this);

        player = new Player(this);


        world.setContactListener(new WorldContactListener());

        music = Main.manager.get("audio/music/mario_music.ogg",Music.class);
        music.setLooping(true);
        if(pref.isMusicEnabled()){
            music.setVolume(pref.getMusicVolume());
            music.play();
        }
        this.done = false;
        parent.level = 2;



    }
    private B2WorldCreator creator;

    private Music music;
    private TextureAtlas atlas;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private Main parent;
    boolean done;
    private Viewport gameport;
    private Hud hud;
    private OrthographicCamera gamecam;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private AppPreferences pref;

    public TextureAtlas getAtlas(){
        return atlas;
    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
    }

    public void handleInput(float dt){
        if(player.currentState != Player.State.DEAD) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);

            }
            if (Gdx.input.isKeyPressed((Input.Keys.RIGHT)) && player.b2body.getLinearVelocity().x <= 1) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed((Input.Keys.LEFT)) && player.b2body.getLinearVelocity().x >= -1) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }
    public void update(float dt){
        handleInput(dt);

        player.update(dt);

            for(Enemy enemy : creator.getShooter())
                enemy.update(dt);

            for(Enemy enemy : creator.getBatSpawner())
                enemy.update(dt);


        hud.update(dt);

        world.step(1/60f,6,2);
        if(player.currentState != Player.State.DEAD) {
            if(player.b2body.getPosition().x < 3){
                gamecam.position.x = 3;
            } else if(player.b2body.getPosition().x > 35.5f){
                gamecam.position.x = 35.5f;

            } else {
                gamecam.position.x = player.b2body.getPosition().x;

            }


        }
        gamecam.update();
        renderer.setView(gamecam);

        if(player.isComplete()){
            music.stop();
            parent.changeScreen(Main.COMPLETEDSCREEN, hud.getTimeCount(), hud.getScore(),2);
            parent.levelTwoComplete = true;
        }
        if(player.isDead()){
            parent.changeScreen(Main.GAMEOVERSCREEN, hud.getTimeCount(), hud.getScore(),2);
            music.stop();
        }
        if(player.b2body.getPosition().y < 0){
            parent.changeScreen(Main.GAMEOVERSCREEN, hud.getTimeCount(), hud.getScore(),2);
            music.stop();
        }
        if(done){
            parent.changeScreen(Main.GAMEOVERSCREEN, hud.getTimeCount(), hud.getScore(),2);
            music.stop();
        }


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

       // b2dr.render(world, gamecam.combined);

        parent.batch.setProjectionMatrix(gamecam.combined);
        parent.batch.begin();
        player.draw(parent.batch);

        for(Enemy enemy : creator.getShooter()) {
            enemy.draw(parent.batch);
            if(enemy.getX() < player.getX() + 200 / Main.PPM){
                enemy.b2body.setActive(true);
            }
        }

        for(Enemy enemy : creator.getBatSpawner()) {
            enemy.draw(parent.batch);
            if(enemy.getX() < player.getX() + 200 / Main.PPM){
                enemy.b2body.setActive(true);
            }

        }

        parent.batch.end();

        parent.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }
    public void setDone(){
        done = true;
    }
    public double getX(){
        return player.b2body.getPosition().x;
    }
    public double getY(){
        return player.b2body.getPosition().y;
    }
    @Override
    public void resize(int width, int height) {
        gameport.update(width,height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
