package com.rnyathi.letitgo.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Sprites.Enemy;
import com.rnyathi.letitgo.Sprites.InteractiveTileObject;
import com.rnyathi.letitgo.Sprites.Player;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
        switch(cDef){
            case Main.ENEMY_HEAD_BIT | Main.PLAYER_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case Main.ENEMY_BIT | Main.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;
            case Main.PLAYER_BIT | Main.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == Main.PLAYER_BIT) {
                    ((Player) fixA.getUserData()).hit();
                }
            else{
                ((Player) fixB.getUserData()).hit();

            }
                break;
            case Main.ENEMY_BIT | Main.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
