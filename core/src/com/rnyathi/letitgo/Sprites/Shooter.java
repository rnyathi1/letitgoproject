package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Scenes.Hud;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class Shooter extends Enemy{

    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int level;
    private boolean runningRight;
    Main parent = new Main();
    Hud hud;
    private float previousTime = 0;
    private LevelOneScreen levelOne;
    private LevelTwoScreen levelTwo;

    Array<Fireball> balls;
    public Shooter(LevelOneScreen screen, float x, float y ) {

        super(screen, x, y);
        hud = new Hud(parent.batch);
        level = 1;
        levelOne = screen;

        frames = new Array<TextureRegion>();
        for(int i = 0; i <= 2; i++){

            frames.add(new TextureRegion(screen.getAtlas().findRegion("shooter") , i * 16,1,16,32));
        }
        walkAnimation = new Animation(0.1f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,32/Main.PPM);
        setToDestroy = false;
        destroyed = false;
        levelOne = screen;
        balls = new Array<Fireball>();


    }
    public Shooter(LevelTwoScreen screen, float x, float y ) {
        super(screen, x, y);
        hud = new Hud(parent.batch);
        level = 2;
        levelTwo = screen;

        frames = new Array<TextureRegion>();
        for(int i = 0; i <= 2; i++){

            frames.add(new TextureRegion(screen.getAtlas().findRegion("shooter") , i * 16,1,16,32));
        }
        walkAnimation = new Animation(0.1f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,32/Main.PPM);
        setToDestroy = false;
        destroyed = false;
        levelTwo = screen;
        balls = new Array<Fireball>();

    }


    public void update(float dt){
        stateTime += dt;

        TextureRegion region = region = (TextureRegion) walkAnimation.getKeyFrame(stateTime,true);

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        } else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;

        } else if( !destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
        }
        for(Fireball fireball : balls){
            fireball.update(dt);
            if(fireball.destroyed){
                balls.removeValue(fireball, true);
            }
        }
        if(stateTime - previousTime > 6 / Main.difficulty){
            shoot();
            previousTime = stateTime;
        }



    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.ENEMY_BOUNDARY_BIT | Main.ENEMY_BIT|Main.COMPLETION_BIT | Main.PLAYER_BIT ;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 16).scl(1 / Main.PPM);
        vertice[1] = new Vector2(5, 16).scl(1 / Main.PPM);
        vertice[2] = new Vector2(-3, 6).scl(1 / Main.PPM);
        vertice[3] = new Vector2(3, 6).scl(1 / Main.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);

    }
    public void shoot(){

        if (level == 1) {
            if(b2body.getLinearVelocity().x > 0){
                balls.add(new Fireball( levelOne,b2body.getPosition().x,b2body.getPosition().y,true));
            }else {
                balls.add(new Fireball(levelOne, b2body.getPosition().x, b2body.getPosition().y, false));
            }
        } else if(level == 2){
            if(b2body.getLinearVelocity().x > 0){
                balls.add(new Fireball( levelTwo,b2body.getPosition().x,b2body.getPosition().y,true));
            }else {
                balls.add(new Fireball(levelTwo, b2body.getPosition().x, b2body.getPosition().y, false));
            }
        }

    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1){
            super.draw(batch);
        }


        for(Fireball fireball : balls){
            fireball.draw(batch);
        }



    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        hud.addScore(200 * Main.difficulty);
    }
}