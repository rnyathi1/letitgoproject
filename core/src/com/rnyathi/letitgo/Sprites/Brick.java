package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Scenes.Hud;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class Brick extends InteractiveTileObject{
    public Brick(LevelOneScreen screen, Rectangle bounds){

        super( screen,bounds);
        fixture.setUserData(this);
        setCategoryFilter(Main.BRICK_BIT);
    }


    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","collision");
        setCategoryFilter(Main.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        Main.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
