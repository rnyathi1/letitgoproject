package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Scenes.Hud;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

import java.util.Random;

public class Bat extends Enemy{
        Vector2 vector;
        LevelTwoScreen screenTwo;
        TextureRegion batImage;
     boolean setToDestroy;
     boolean destroyed;
    Main parent = new Main();

    Hud hud;
        public Bat(LevelTwoScreen screenTwo, float x, float y) {
        super(screenTwo, x, y);
        this.screenTwo = screenTwo;
            batImage = new TextureRegion(screenTwo.getAtlas().findRegion("bat") , 55,1,42,19);
            setRegion(batImage);
            setBounds(getX(),getY(),6 /Main.PPM,6/Main.PPM);
            hud= new Hud(parent.batch);
            setToDestroy = false;
            destroyed = false;
            defineEnemy();

    }





    @Override
    protected void defineEnemy() {


        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + 12 / Main.PPM, getY() +  12 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.PLAYER_BIT | Main.GROUND_BIT;

        fdef.shape = shape;
        fdef.restitution = 0;
        fdef.friction = 0;
        fdef.density = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setGravityScale(1/100);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-6, 4).scl(1 / Main.PPM);
        vertice[1] = new Vector2(6, 4).scl(1 / Main.PPM);
        vertice[2] = new Vector2(-5, 2).scl(1 / Main.PPM);
        vertice[3] = new Vector2(5, 2).scl(1 / Main.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);


    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        hud.addScore(20 * Main.difficulty);
    }

    @Override
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        } else if (!destroyed) {
            vector = new Vector2();

            if (b2body.getPosition().x > screenTwo.getX()) {
                vector.x = -0.1f;
            } else if (b2body.getPosition().x < screenTwo.getX()) {
                vector.x = 0.1f;

            }
            if (b2body.getPosition().y < screenTwo.getY()) {
                vector.y = 0.1f;
            } else if (b2body.getPosition().y > screenTwo.getY()) {
                vector.y = -0.1f;
            }
            b2body.setLinearVelocity(vector);
        }
    }
}
