package com.rnyathi.letitgo.Views;

import com.badlogic.gdx.Screen;
import com.rnyathi.letitgo.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OptionsScreen implements Screen {
    private Main parent;
    private Stage stage;
    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label difficultyLabel;
    private Label musicOnOffLabel;


    public OptionsScreen(Main main){
        parent = main;
        stage = new Stage(new ScreenViewport());

    }
    @Override
    public void show() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);

        Skin skin = new Skin(Gdx.files.internal("Skins/glassy-ui.json"));

        // music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());

                return false;
            }
        });



        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });


        final TextButton difficultyButton = new TextButton("Difficulty", skin,"small");
        difficultyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if(Main.difficulty < 3){
                    parent.addDifficulty();
                    difficultyLabel.setText(Main.difficulty);
                } else {
                    Main.difficulty = 1;
                    difficultyLabel.setText(Main.difficulty);
                }


            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(new MenuScreen(parent));

            }
        });

        titleLabel = new Label( "Options", skin );
        volumeMusicLabel = new Label( "Music Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        difficultyLabel = new Label(Integer.toString(Main.difficulty),skin);


        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10,0,0,10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10,0,0,10);
        table.add(difficultyButton).left();
        table.add(difficultyLabel);
        table.row().pad(10,0,0,10);
        table.add(backButton).colspan(2);
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        stage.dispose();

    }

}
