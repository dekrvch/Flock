package com.dekrvc.flock.boids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.dekrvc.flock.simulation.Grid;

import java.util.Random;

public class Firefly extends Vehicle {
    public static float NUDGE_RADIUS = 40;
    public static float CYCLE_DURATION = 1f; //s
    public static float PULL = 0.01f;        //s
    public static float FLASH_TAU = 0.15f;    //s
    private float clock;
    private float phase;
    private float sensitivity;

    final Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound.wav"));


    public Firefly(float x, float y, Grid grid) {
        super(x, y, grid);
        clock = 0;
        sensitivity = 1;
    }

    public Firefly(float x, float y, Grid grid, float clock) {
        this(x, y, grid);
        this.clock = clock;
    }

    public void move(float delta){
        super.move(delta);
        sensitivity = Math.min(1, sensitivity+0.1f);
    }


    public void tick(float delta){
        clock = clock + (float) (1 + 0.05*rand.nextGaussian())*delta;
        phase =  2*(float) Math.PI * (CYCLE_DURATION - clock)/CYCLE_DURATION;
        //Fire
        if (clock > CYCLE_DURATION) {
            clock = clock - CYCLE_DURATION;
            sound.play(0.05f);
            for (Vehicle neighbor: neighbors){
                if (neighbor instanceof Firefly &&
                        grid.dist2BetweenVehicles(this, neighbor) < NUDGE_RADIUS*NUDGE_RADIUS){
                    ((Firefly) neighbor).pullClock();
                }
            }
        }
    }


    public void pullClock(){
        clock = clock + PULL * (float) Math.sin(phase) * sensitivity;
        sensitivity = sensitivity * 0.9f;
    }

    //Used for visualization
    public float getIntensity(){
        return (float) (Math.exp(-20*(CYCLE_DURATION-clock)/ FLASH_TAU) + //ignition
                        Math.exp(-clock/FLASH_TAU));                      //glowing
    }
}
