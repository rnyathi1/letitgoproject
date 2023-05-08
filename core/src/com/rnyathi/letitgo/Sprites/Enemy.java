package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;


public abstract class Enemy extends Sprite {
    protected World world;
    protected LevelOneScreen screen;
    protected LevelTwoScreen screenTwo;
    protected LevelThreeScreen screenThree;
    public Body b2body;
    public Vector2 velocity;
    public Enemy(LevelOneScreen screen, float x, float y){

        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(0.5f,0);
        b2body.setActive(false);
    }
    public Enemy(LevelTwoScreen screen, float x, float y){

        this.world = screen.getWorld();
        this.screenTwo = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1,0);
        b2body.setActive(false);
    }
    public Enemy(LevelThreeScreen screen, float x, float y){

        this.world = screen.getWorld();
        this.screenThree = screen;
        setPosition(x,y);
        defineEnemy();
        velocity = new Vector2(1,0);
        b2body.setActive(false);
    }



    protected abstract void defineEnemy();
    public abstract void hitOnHead();

    public void reverseVelocity(boolean x,boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
    public abstract void update(float dt);
}
