package com.dekrvc.flock.simulation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dekrvc.flock.boids.Vehicle;

public class Grid {
    class Cell extends Array<Vehicle> {}
    protected float worldWidth, worldHeight;
    protected float cellWidth, cellHeight;
    protected int rows, columns;
    private Cell[][] cells;
    private Vector2 quickVec2;

    public Grid(float width, float height, float minCellSize){
        worldWidth = width;
        worldHeight = height;
        rows = (int) (worldHeight/minCellSize);
        columns = (int) (worldWidth/minCellSize);
        //System.out.println("The grid consists of " + columns + " columns and " + rows + " rows");
        cellWidth = worldWidth / columns;
        cellHeight = worldHeight / rows;
        cells = new Cell[columns][rows];
        //Initialization empty arrays
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j ++){
                cells[i][j] = new Cell();
            }
        }
        quickVec2 = new Vector2();
    }

    public void place(Vehicle vehicle){
        int i = Math.min((int) (vehicle.getX()/ cellWidth),  columns-1);
        int j = Math.min((int) (vehicle.getY()/ cellHeight), rows-1);
        //System.out.println(i + "\t" + j + "\twith coordinates:" + vehicle.getX() + ", " + vehicle.getY());
        cells[i][j].add(vehicle);
    }

    public void reset(){
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j ++){
                cells[i][j] = new Cell();
            }
        }
    }

    public Array<Vehicle> findNeighbors(Vehicle vehicle, float distance){
        Array<Vehicle> neighbors = new Array<>();
        int horWindow = 1 + (int) (distance/cellWidth);
        int verWindow = 1 + (int) (distance/cellHeight);
        int i = (int) (vehicle.getX()/ cellWidth);
        int j = (int) (vehicle.getY()/ cellHeight);
        for (int k_ = i - horWindow; k_ <= i + horWindow; k_++){
             int k = k_;
             if (k <= -1){k = columns - k;}
             if (k >= columns) {k = k - columns;}
             for (int l_ = j - verWindow; l_ <= j + verWindow; l_++){
                 int l = l_;
                 if (l <= -1)   {l = rows - l;}
                 if (l >= rows) {l = l - rows;};
                    for (Vehicle vehicle2 : cells[k][l]){
                        if      (vehicle == vehicle2){continue;}
                        else if (dist2BetweenVehicles(vehicle, vehicle2) < distance*distance){
                            neighbors.add(vehicle2);
                        }
                    }
             }
        }
        //System.out.println("Found " + neighbors.size + " neighbors");
        return neighbors;
    }

    public float dist2BetweenVehicles(Vehicle vehicle1, Vehicle vehicle2){
        quickVec2.set(vehicle2.getX() - vehicle1.getX(),
                vehicle2.getY() - vehicle1.getY());
        imposePBC(quickVec2);
        return quickVec2.len2();
    }

    public Vector2 subVectors(Vector2 vec1, Vector2 vec2){
        return imposePBC(vec1.sub(vec2));
    }

    public Vector2 imposePBC(Vector2 v){
        //System.out.println("Imposed PBC on " + v.x + ", " + v.y);
        if (v.x < -worldWidth/2){v.x = v.x + worldWidth;}
        else if (v.x > worldWidth/2){v.x = v.x - worldWidth;}
        if (v.y < -worldHeight/2){v.y = v.y + worldHeight;}
        else if (v.y > worldHeight/2){v.y = v.y - worldHeight;}
        return v;
    }


    public float getWorldWidth() {return worldWidth;}

    public float getWorldHeight() {return worldHeight;}

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cells.length; i ++){
            result.append("\n|");
            for (int j = 0; j < cells[i].length; j++){
                result.append(cells[i][j].size);
                result.append("|");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
}
}
