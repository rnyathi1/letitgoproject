package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class Fireball extends Enemy{
    private float stateTime;
    private float previousTime;
    Vector2 vector = new Vector2();
    boolean right;
    int level;
    boolean setToDestroy;
    boolean destroyed;
    LevelOneScreen screenOne;
    LevelTwoScreen screenTwo;
     LevelThreeScreen screenThree;
    TextureRegion test;
    public Fireball(LevelOneScreen screen, float x, float y,boolean right) {
        super(screen, x, y);
        this.right = right;
        level = 1;
        screenOne = screen;
        stateTime = 0;
        test = new TextureRegion(screen.getAtlas().findRegion("fireball") , 0,1,8,8);
        setRegion(test);
        setBounds(getX(),getY(),6 /Main.PPM,6/Main.PPM);
        defineEnemy();



    }

    public Fireball(LevelTwoScreen screen, float x, float y, boolean right) {
        super(screen, x, y);
        this.right = right;
        level = 2;
        screenTwo = screen;
        setToDestroy = false;
        destroyed = false;
        test = new TextureRegion(screen.getAtlas().findRegion("fireball") , 0,1,8,8);
        stateTime = 0;
        setRegion(test);
        setBounds(getX(),getY(),6 /Main.PPM,6/Main.PPM);
        defineEnemy();

    }

    public Fireball(LevelThreeScreen screen, float x, float y) {
        super(screen,x,y);
        level = 3;
        screenThree = screen;
        destroyed = false;
        setToDestroy = false;
        test = new TextureRegion(screen.getAtlas().findRegion("fireball") , 0,1,8,8);
        stateTime = 0;
        setRegion(test);
        setBounds(getX(),getY(),6 /Main.PPM,6/Main.PPM);
        defineEnemy();
    }


    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / Main.PPM);
        fdef.filter.categoryBits = Main.FIREBALL_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setGravityScale(1/100);




    }

    @Override
    public void hitOnHead() {
        if(level == 1)
            screenOne.setDone();
        else if (level == 2)
            screenTwo.setDone();
        else
            screenThree.setDone();
    }

    public void destroy(){
        setToDestroy = true;
    }
    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;

            stateTime = 0;
        } else if( !destroyed) {
            if(stateTime - previousTime > 2){
                setToDestroy= true;
            }

            if(level == 1 || level == 2){
                vector = new Vector2();
                if(right){
                    vector.x = 2f;
                } else {
                    vector.x = -2f;
                }

                vector.y = 0;
                b2body.setLinearVelocity(vector);
            } else {
                vector = new Vector2();

                if(b2body.getPosition().x > screenThree.getX()){
                    vector.x= -0.7f;
                } else if(b2body.getPosition().x < screenThree.getX()){
                    vector.x= 0.7f;

                }
                if(b2body.getPosition().y < screenThree.getY()){
                    vector.y = 0.5f;
                } else if(b2body.getPosition().y > screenThree.getY()){
                    vector.y = -0.5f;
                }
                b2body.setLinearVelocity(vector);
            }


        }


    }

}
