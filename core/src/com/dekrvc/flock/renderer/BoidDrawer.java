package com.dekrvc.flock.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dekrvc.flock.boids.Bird;
import com.dekrvc.flock.boids.Vehicle;
import com.dekrvc.flock.boids.Firefly;

public class BoidDrawer {
    private SpriteBatch batch;
    private int width, height;
    private TextureRegion[] regions;

    public BoidDrawer(SpriteBatch spriteBatch){
        batch = spriteBatch;
    }

    public void draw(Vehicle vehicle){
        //Need to set the alpha back to one after drawing
        if (vehicle instanceof Firefly){
            FireflyDrawer.draw(batch, (Firefly) vehicle);
            batch.setColor(1, 1, 1, 1);
        }
        else if (vehicle instanceof Bird){
            BirdDrawer.draw(batch, (Bird) vehicle);
            batch.setColor(1, 1, 1, 1);
        }
    }

    static void drawSprite(SpriteBatch batch, TextureRegion txrRegion,
                            float x, float y,
                            float width, float height,
                            float angle, float alpha){
        batch.setColor(1, 1, 1, alpha);
        batch.draw(txrRegion, x - width/2, y- height/2,
                width/2, height/2,
                width, height, 1, 1, angle);
    }

    static class FireflyDrawer {
        final static int WIDTH = 5;
        final static int HEIGHT = 5;
        final static int FLASH_SIZE = 35;
        final static Texture TEXTURE = new Texture("firefly.png");;
        final static TextureRegion FF_OFF = new TextureRegion(TEXTURE, 0,0, 100, 100);
        final static TextureRegion FF_ON = new TextureRegion(TEXTURE, 100,0, 100, 100);
        final static TextureRegion FF_FLASH = new TextureRegion(TEXTURE, 0,100, 700, 700);
        static public void draw(SpriteBatch batch, Firefly ff){
            drawSprite(batch, FF_OFF, ff.getX(), ff.getY(),
                       WIDTH, HEIGHT, ff.getAngle(), 1);
            drawSprite(batch, FF_FLASH, ff.getX(), ff.getY(),
                    FLASH_SIZE, FLASH_SIZE, 0, ff.getIntensity());
            drawSprite(batch, FF_ON, ff.getX(), ff.getY(),
                    WIDTH, HEIGHT, ff.getAngle(), ff.getIntensity());
        }
    }

    static class BirdDrawer{
        final static int WIDTH = 10;
        final static int HEIGHT = 20;
        final static Texture TEXTURE = new Texture("bird.png");;
        final static TextureRegion BIRD = new TextureRegion(TEXTURE, 0,0, 125, 250);
        static public void draw(SpriteBatch batch, Bird bd){
            drawSprite(batch, BIRD, bd.getX(), bd.getY(),
                    WIDTH, HEIGHT, bd.getAngle(), 1);
        }
    }


}

