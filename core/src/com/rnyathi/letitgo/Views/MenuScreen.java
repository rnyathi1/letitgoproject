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

public class MenuScreen implements Screen {
    private final Stage stage;
    private Main parent;
    public static Texture backgroundTexture;
    private SpriteBatch spriteBatch;

    private TextButton newGame;
    public MenuScreen(Main main){
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
        Label name = new Label("Let It Go",skin);

        if(parent.levelOneComplete == true){
             newGame = new TextButton("Continue", skin,"small");
        } else {
            newGame = new TextButton("New Game", skin,"small");
        }

        TextButton options = new TextButton("Options", skin,"small");
        TextButton exit = new TextButton("Exit", skin,"small");

        table.add(name).center();
        table.row();
        table.add(newGame).fillX().uniformX();
        table.row();
        table.add(options).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Main.LOADINGSCREEN);
            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Main.PREFERENCES);

            }
        });

    }

    @Override
    public void render(float delta) {

        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture,0,0);
        spriteBatch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();




    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        stage.dispose();
        backgroundTexture.dispose();
    }
}
