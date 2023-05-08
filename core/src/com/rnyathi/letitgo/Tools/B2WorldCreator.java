package com.rnyathi.letitgo.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rnyathi.letitgo.Main;
import com.rnyathi.letitgo.Sprites.BatSpawner;
import com.rnyathi.letitgo.Sprites.Boss;
import com.rnyathi.letitgo.Sprites.Shooter;
import com.rnyathi.letitgo.Sprites.Walker;
import com.rnyathi.letitgo.Views.LevelOneScreen;
import com.rnyathi.letitgo.Views.LevelThreeScreen;
import com.rnyathi.letitgo.Views.LevelTwoScreen;

public class B2WorldCreator {
    private Array<Walker> walker;
    private Array<Shooter> shooter;
    private Array<BatSpawner> batSpawner;
    private Array<Boss> boss;
    private int level;
    public B2WorldCreator(LevelOneScreen screen){
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        debugRenderer.setDrawBodies(false);
        debugRenderer.setDrawInactiveBodies(false);
        System.out.println(debugRenderer.isDrawBodies());

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;
        this.level = level;
        //Ground

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.GROUND_BIT;
            body.createFixture(fdef);


        }


        //Completion
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.COMPLETION_BIT;
            body.createFixture(fdef);

        }
        //Completion
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.ENEMY_BOUNDARY_BIT;
            fdef.filter.maskBits = Main.ENEMY_BIT| Main.GROUND_BIT;
            body.createFixture(fdef);

        }

        //walker
        walker = new Array<Walker>(0);
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            walker.add(new Walker(screen,rect.getX() / Main.PPM,rect.getY() / Main.PPM));

        }
        //Shooter
        shooter = new Array<Shooter>(0);
        if(Main.difficulty > 1) {
            for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                shooter.add(new Shooter(screen, rect.getX() / Main.PPM, rect.getY() / Main.PPM));

            }
        }






    }
    public B2WorldCreator(LevelTwoScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        this.level = level;

        //Ground
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.GROUND_BIT;
            body.createFixture(fdef);

        }

        //Completion
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.ENEMY_BOUNDARY_BIT;
            fdef.filter.maskBits = Main.ENEMY_BIT| Main.GROUND_BIT;
            body.createFixture(fdef);

        }



        //Completion
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.COMPLETION_BIT;
            fdef.filter.maskBits = Main.PLAYER_BIT;
            body.createFixture(fdef);

        }
        //Completion
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.ENEMY_BIT;
            fdef.filter.maskBits = Main.PLAYER_BIT;
            body.createFixture(fdef);

        }



        batSpawner = new Array<BatSpawner>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            batSpawner.add(new BatSpawner(screen,rect.getX() / Main.PPM,rect.getY() / Main.PPM));


        }

        shooter = new Array<Shooter>(0);
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            shooter.add(new Shooter(screen,rect.getX() / Main.PPM,rect.getY() / Main.PPM));

        }






    }
    public B2WorldCreator(LevelThreeScreen screen){

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        this.level = level;
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.GROUND_BIT;
            body.createFixture(fdef);

        }
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.GROUND_BIT;
            body.createFixture(fdef);

        }

        //Completion
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.ENEMY_BOUNDARY_BIT;
            fdef.filter.maskBits = Main.ENEMY_BIT| Main.GROUND_BIT;
            body.createFixture(fdef);

        }

        //Completion
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Main.PPM, (rect.getY() + rect.getHeight() / 2)/ Main.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / Main.PPM,rect.getHeight() / 2 / Main.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Main.COMPLETION_BIT;
            fdef.filter.maskBits = Main.PLAYER_BIT;
            body.createFixture(fdef);

        }


        boss = new Array<Boss>(0);
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            boss.add(new Boss(screen,rect.getX() / Main.PPM,rect.getY() / Main.PPM));

        }

        //walker
        walker = new Array<Walker>(0);
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();

            walker.add(new Walker(screen,rect.getX() / Main.PPM,rect.getY() / Main.PPM));

        }




    }

    public Array<Walker> getWalker() {
        return walker;
    }
    public Array<Shooter> getShooter() {
        return shooter;
    }
    public Array<BatSpawner> getBatSpawner() {
        return batSpawner;
    }
    public Array<Boss> getBoss() {return boss;}
}
