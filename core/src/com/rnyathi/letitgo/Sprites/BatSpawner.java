package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class BatSpawner extends Enemy{
    private Array<Bat> bats;
    private float previousTime = 0;
    private float stateTime;
    private int level;
    private LevelOneScreen levelOne;
    private LevelTwoScreen levelTwo;
    private LevelThreeScreen levelThree;
    public BatSpawner(LevelOneScreen screen, float x, float y) {

        super(screen,x,y);

        bats = new Array<Bat>();
        level = 1;
        levelOne = screen;

    }
    public BatSpawner(LevelTwoScreen screen, float x, float y) {

        super(screen,x,y);

        bats = new Array<Bat>();
        level = 2;
        stateTime = 0;
        levelTwo = screen;

    }
    public BatSpawner(LevelThreeScreen screen, float x, float y) {

        super(screen,x,y);

        bats = new Array<Bat>();
        level = 3;
        levelThree = screen;


    }

    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);

        fdef.filter.categoryBits = Main.BAT_SPAWNER_BIT;
        fdef.filter.maskBits = 0 ;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    @Override
    public void hitOnHead() {

    }


    public void draw(Batch batch){
        for(Bat  bat : bats) {
            bat.draw(batch);
        }

    }


    @Override
    public void update(float dt) {
        stateTime += dt;

        if(stateTime - previousTime > 5 && bats.size < 1 * Main.difficulty){
            spawnBat();
        }

        for(Bat  bat : bats) {
            bat.update(dt);
            if(bat.destroyed){
                bats.removeValue(bat, true);
            }
        }
    }

    public void spawnBat(){
        bats.add(new Bat(levelTwo, b2body.getPosition().x, b2body.getPosition().y));
        previousTime = stateTime;

    }

}
