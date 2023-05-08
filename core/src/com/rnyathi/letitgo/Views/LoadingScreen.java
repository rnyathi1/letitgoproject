package com.rnyathi.letitgo.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.rnyathi.letitgo.Main;

public class LoadingScreen implements Screen {
private Main parent;
private final Stage stage;
public static Texture backgroundTexture;
public static Sprite backgroundSprite;
    private SpriteBatch spriteBatch;

    public LoadingScreen(Main main ){
        parent = main;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        spriteBatch = new SpriteBatch();
    }
    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("Skins/glassy-ui.json"));

        final TextButton levelOne = new TextButton("Level One", skin,"small");
        final TextButton levelTwo = new TextButton("Level Two", skin,"small");
        final TextButton levelThree = new TextButton("Level Three", skin,"small");
        final TextButton backButton = new TextButton("Back", skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Main.MENU);
            }
        });
        levelOne.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Main.LEVELONE);
            }
        });
        levelTwo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Main.LEVELTWO);
            }
        });
        levelThree.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Main.LEVELTHREE);
            }
        });


        if(!parent.levelOneComplete){
            table.add().fillX();
        }
        table.add(levelOne).fillX();
        if(parent.levelOneComplete){
            table.add(levelTwo).fillX();
        } else{
            table.add().fillX();
        }
        if(parent.levelTwoComplete && parent.levelOneComplete){
            table.add(levelThree).fillX();
        } else if(parent.levelOneComplete && !parent.levelTwoComplete) {
            table.add().fillX();
        }
        table.row();
        table.add().fillX();
        table.add(backButton).padTop(100).fillX();
        table.add().fillX();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,0,0);
        spriteBatch.end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        backgroundTexture.dispose();
    }
}
