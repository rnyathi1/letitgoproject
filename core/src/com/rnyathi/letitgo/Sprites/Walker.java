package com.rnyathi.letitgo.Sprites;

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

public class Walker extends Enemy{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private int level;
    Main parent = new Main();
    Hud hud;
    public Walker(LevelOneScreen screen, float x, float y ) {

        super(screen, x, y);
        hud = new Hud(parent.batch);
        level = 1;


        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++){

            frames.add(new TextureRegion(screen.getAtlas().findRegion("walker") , i * 16,0,16,16));
        }
        walkAnimation = new Animation(0.4f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,16/Main.PPM);
        setToDestroy = false;
        destroyed = false;

    }

    public Walker(LevelThreeScreen screen, float x, float y ) {
        super(screen, x, y);
        hud = new Hud(parent.batch);
        level = 3;


        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++){

            frames.add(new TextureRegion(screen.getAtlas().findRegion("walker") , i * 16,0,16,16));
        }
        walkAnimation = new Animation(0.4f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16 /Main.PPM,16/Main.PPM);
        setToDestroy = false;
        destroyed = false;
    }

    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            if(level == 1){
                setRegion(new TextureRegion(screen.getAtlas().findRegion("walker"),32,0,16,16));
            } else if(level == 2){
                setRegion(new TextureRegion(screenTwo.getAtlas().findRegion("walker"),32,0,16,16));
            } else{
                setRegion(new TextureRegion(screenThree.getAtlas().findRegion("walker"),32,0,16,16));
            }

            stateTime = 0;
        } else if( !destroyed) {
            if(Main.difficulty == 3 && level == 1){
                if (b2body.getPosition().x > screen.getX()) {
                    b2body.setLinearVelocity(-velocity.x * Main.difficulty, velocity.y);
                } else if (b2body.getPosition().x < screen.getX()) {
                    b2body.setLinearVelocity(velocity.x * Main.difficulty, velocity.y);

                }
            }else {
                b2body.setLinearVelocity(velocity.x * Main.difficulty, velocity.y * Main.difficulty);

            }
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion((TextureRegion) walkAnimation.getKeyFrame(stateTime, true));
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
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.ENEMY_BIT;
        fdef.filter.maskBits = Main.GROUND_BIT | Main.ENEMY_BOUNDARY_BIT | Main.ENEMY_BIT|Main.COMPLETION_BIT | Main.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 9).scl(1 / Main.PPM);
        vertice[1] = new Vector2(5, 9).scl(1 / Main.PPM);
        vertice[2] = new Vector2(-3, 4).scl(1 / Main.PPM);
        vertice[3] = new Vector2(3, 4).scl(1 / Main.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = Main.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);




    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1){
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        hud.addScore(100 * Main.difficulty);
    }

}
