package com.rnyathi.letitgo.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class Player extends Sprite {
    public enum State {FALLING,JUMPING,STANDING,RUNNING,DEAD};
    public State currentState;
    public State previousState;
    private Animation playerRun;
    private Animation playerJump;
    private boolean runningRight;
    private boolean isDead;
    private float stateTimer;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    public Player(LevelOneScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4 ; i++){
            frames.add(new TextureRegion(getTexture(),i * 16, 10,16,16));

        }
        playerRun = new Animation(0.1f,frames);
        frames.clear();

        for(int i = 4; i < 6 ; i++){
            frames.add(new TextureRegion(getTexture(),i * 16,10,16,16));

        }
        playerJump = new Animation(0.1f,frames);
        frames.clear();

        definePlayer();

        playerStand = new TextureRegion(getTexture(),0,10,16,16);
        setBounds(0,0,16 / Main.PPM,16/Main.PPM);
        setRegion(playerStand);
        isDead = false;
    }


    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) playerRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        } else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    public State getState(){
        if(isDead)
            return State.DEAD;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
                return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }
    public boolean isDead(){
        return isDead;
    }
    public float getStateTimer(){
        return stateTimer;
    }

    public void hit(){
        isDead = true;
    }
    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ Main.PPM,32 / Main.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(6 / Main.PPM);
        fdef.filter.categoryBits = Main.PLAYER_BIT;

        fdef.filter.maskBits = Main.GROUND_BIT | Main.BRICK_BIT | Main.ENEMY_BIT | Main.OBJECT_BIT | Main.ENEMY_HEAD_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / Main.PPM, 6 / Main.PPM),new Vector2(2 / Main.PPM, 6 / Main.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");

    }
}
