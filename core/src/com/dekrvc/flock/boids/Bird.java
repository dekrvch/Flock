package com.dekrvc.flock.boids;

import com.dekrvc.flock.simulation.Grid;

public class Bird extends Vehicle {
    public static float MAX_SPEED = 100; //px/s
    public Bird(float x, float y, Grid g) {
        super(x, y, g);
    }
}
