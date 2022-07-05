package com.dekrvc.flock.simulation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.dekrvc.flock.boids.Bird;
import com.dekrvc.flock.boids.Vehicle;
import com.dekrvc.flock.boids.Firefly;

import java.util.Random;

public class Simulation {
    private final int N = 75;
    private Grid grid;
    private float clock;
    private Vector2 cursor;
    private Array<Vehicle> vehicles;

    private Random rand;

    public Simulation(float width, float height) {
        grid = new Grid(width, height, 50);
        cursor = new Vector2();
        rand = new Random();
    }

    public void populate(){
        vehicles = new Array<>();
        //Fireflies
        for (int i = 0; i < N; i++){
            Firefly ff = new Firefly(grid.worldWidth*rand.nextFloat(),
                                     grid.worldHeight*rand.nextFloat(),
                                     grid, Firefly.CYCLE_DURATION*rand.nextFloat());
            vehicles.add(ff);
        }
        //Birds
        for (int i = 0; i < 0; i++){
            Bird bd = new Bird(grid.worldWidth*rand.nextFloat(),
                    grid.worldHeight*rand.nextFloat(),  grid);
            vehicles.add(bd);
        }
    }

    public void update(float delta){
        clock = clock + delta;

        for (Vehicle vehicle : vehicles){
            //vehicle.seek(cursor);
            vehicle.separate();
            vehicle.align();
            vehicle.cohesion();
            vehicle.move(delta);
            vehicle.imposePBC(grid);
            grid.place(vehicle);
        }

        //Ticking the clock and nudging the neighbors
        for (Vehicle vehicle : vehicles){
            vehicle.setNeighbors(grid.findNeighbors(vehicle, 50));
            if (vehicle instanceof Firefly){
                ((Firefly) vehicle).tick(delta);
            }
        }
        grid.reset();
    }

    public Array<Vehicle> getVehicles(){
        return vehicles;
    }

    public void setCursor(Vector3 cur){cursor.set(cur.x, cur.y);}






}
