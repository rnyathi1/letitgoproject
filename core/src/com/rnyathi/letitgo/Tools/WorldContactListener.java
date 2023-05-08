package com.rnyathi.letitgo.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Sprites.Boss;
import com.rnyathi.letitgo.Sprites.Enemy;
import com.rnyathi.letitgo.Sprites.Fireball;
import com.rnyathi.letitgo.Sprites.Player;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case Main.BOSS_BIT | Main.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Main.BOSS_BIT)
                    ((Boss) fixA.getUserData()).destroy();
                else
                    ((Boss) fixB.getUserData()).destroy();
                break;
            case Main.ENEMY_HEAD_BIT | Main.PLAYER_BIT:
                if (fixA.getFilterData().categoryBits == Main.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case Main.ENEMY_BIT | Main.ENEMY_BOUNDARY_BIT:
                if (fixA.getFilterData().categoryBits == Main.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Main.PLAYER_BIT | Main.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == Main.PLAYER_BIT) {

                     ((Player) fixA.getUserData()).hit();
                } else if(fixB.getFilterData().categoryBits == Main.PLAYER_BIT){
                     ((Player) fixB.getUserData()).hit();
                }
                break;

            case Main.PLAYER_BIT | Main.FIREBALL_BIT:
                if (fixA.getFilterData().categoryBits == Main.FIREBALL_BIT) {

                    ((Fireball) fixA.getUserData()).hitOnHead();
                } else if(fixB.getFilterData().categoryBits == Main.FIREBALL_BIT){
                    ((Fireball) fixB.getUserData()).hitOnHead();
                }

                break;
            case Main.FIREBALL_BIT | Main.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == Main.FIREBALL_BIT) {

                    ((Fireball) fixA.getUserData()).destroy();
                } else if(fixB.getFilterData().categoryBits == Main.FIREBALL_BIT){
                    ((Fireball) fixB.getUserData()).destroy();
                }
            case Main.ENEMY_BIT | Main.ENEMY_BIT:
                if(fixA.getUserData() != null && fixB.getUserData() != null){
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                }

                break;
            case Main.PLAYER_BIT | Main.COMPLETION_BIT:
                if (fixA.getFilterData().categoryBits == Main.PLAYER_BIT)
                    ((Player) fixA.getUserData()).complete();
                else
                ((Player) fixB.getUserData()).complete();
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
