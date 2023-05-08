package com.rnyathi.letitgo.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Scenes.Hud;

public class CompletedScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private String score;
    private String time;

    private Main parent;

    public CompletedScreen(Main main, String score, String time){

        parent = main;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.score = score;
        this.time = time;
    }

    @Override
    public void show() {


        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Skin skin = new Skin(Gdx.files.internal("Skins/glassy-ui.json"));
        Label completedLabel = new Label("You Win", font);
        Label scoreLabel = new Label(score, font);
        Label timeLabel = new Label(time, font);
        Label score = new Label("Score", font);
        Label time = new Label("Time", font);

        TextButton nextLevel = new TextButton("Next Level", skin, "small");
        nextLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                switch(parent.level){
                    case 1:
                        parent.changeScreen(Main.LEVELTWO);
                        break;
                    case 2:
                        parent.changeScreen(Main.LEVELTHREE);
                        break;
                }
            }
        });
        TextButton mainMenu = new TextButton("Main Menu", skin, "small");
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                parent.changeScreen(Main.MENU);
            }
        });
        table.add();
        table.add(completedLabel);
        table.add();
        table.row();

        table.add(time);
        table.add();
        table.add(score);
        table.row();


        table.add(scoreLabel);
        table.add();
        table.add(timeLabel);

        table.row();
        if(parent.level == 3){
            table.add();
        } else{
            table.add(nextLevel);
            table.add();
        }
        table.add(mainMenu);
        if(parent.level == 3){
            table.add();
        }
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    }
}
