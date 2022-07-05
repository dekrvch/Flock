package com.dekrvc.flock.boids;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dekrvc.flock.simulation.Grid;

import java.beans.Visibility;
import java.util.Random;

public abstract class Vehicle {
    public static float SEPARATION_RADIUS = 10; //px
    public static float ALIGN_RADIUS = 30; //px
    public static float COHESION_RADIUS = 20; //px
    public static float MAX_FORCE = 100; // px/s2
    public static float MAX_SPEED = 100; //px/s
    protected Grid grid;
    private Vector2 pos;
    private Vector2 vel;
    private Vector2 force;
    private Vector2 desired;
    private Vector2 quickVec2;
    protected Array<Vehicle> neighbors;
    protected Random rand;

    public Vehicle(float x, float y, Grid g) {
        grid = g;
        pos = new Vector2(x, y);
        vel = new Vector2(MAX_SPEED, 0);
        vel.setToRandomDirection();
        force = new Vector2(0, 0);
        desired = vel.cpy();
        quickVec2 = new Vector2(0, 0);
        neighbors = new Array<>();

        rand = new Random();
   }

    public void move(float delta){
        desired.nor().scl(MAX_SPEED);
        force.add(desired.sub(vel));
        force.limit(MAX_FORCE);

        vel.mulAdd(force, delta);
        vel.limit(MAX_SPEED);
        pos.mulAdd(vel, delta);
        desired.set(0, 0);
    }

    public void seek(Vector2 target){
        quickVec2.set(target);
        grid.subVectors(quickVec2, pos);
        quickVec2.nor();
        desired.add(quickVec2);
    }

    public void separate(){
        for (Vehicle neighbor: neighbors) {
            if (grid.dist2BetweenVehicles(this, neighbor) < SEPARATION_RADIUS* SEPARATION_RADIUS) {
                quickVec2.set(this.pos);
                grid.subVectors(quickVec2, neighbor.pos);
                quickVec2.nor();
                desired.mulAdd(quickVec2, 2000f / neighbors.size/grid.dist2BetweenVehicles(this, neighbor));
            }
        }
    }

    public void align(){
        for (Vehicle neighbor: neighbors) {
            if (grid.dist2BetweenVehicles(this, neighbor) < ALIGN_RADIUS*ALIGN_RADIUS) {
                desired.mulAdd(neighbor.vel, 1f / neighbors.size);
            }
        }
    }

    public void cohesion(){
        int count = 0;
        quickVec2.set(0, 0);
        for (Vehicle neighbor: neighbors) {
            if (grid.dist2BetweenVehicles(this, neighbor) < COHESION_RADIUS*COHESION_RADIUS) {
                count++;
                quickVec2.add(neighbor.pos);
            }
        }
        if (count > 0 ){seek(quickVec2.scl(1f/count));}
    }

    public void imposePBC(Grid grid){
        float worldWidth = grid.getWorldWidth();
        float worldHeight = grid.getWorldHeight();
        if (pos.x < 0)           {pos.add(worldWidth,  0);}
        if (pos.x >= worldWidth)  {pos.add(-worldWidth, 0);}
        if (pos.y < 0)           {pos.add(0,  worldHeight);}
        if (pos.y >= worldHeight) {pos.add(0, -worldHeight);}
    }


    public float getX(){return pos.x;}
    public float getY(){return pos.y;}
    public float getAngle(){return  vel.angle();}
    public void setNeighbors(Array<Vehicle> n){neighbors = n;}

}
