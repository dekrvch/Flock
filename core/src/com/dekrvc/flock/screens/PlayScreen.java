package com.dekrvc.flock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dekrvc.flock.boids.Firefly;
import com.dekrvc.flock.boids.Vehicle;
import com.dekrvc.flock.renderer.BoidDrawer;
import com.dekrvc.flock.simulation.Simulation;

public class PlayScreen implements Screen {
    private Manager manager;
    private Viewport viewport;
    private Vector3 cursor;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private Simulation simulation;
    private BoidDrawer boidDrawer;

    public PlayScreen(Manager mng) {
        manager = mng;
        batch = manager.batch;
        cam = new OrthographicCamera(960, 540);
        viewport = new ExtendViewport(cam.viewportWidth, cam.viewportHeight, cam);
        viewport.setWorldSize(cam.viewportWidth, cam.viewportHeight);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight()/2, 0);
    }

    @Override
    public void show() {
        simulation = new Simulation(viewport.getWorldWidth(), viewport.getWorldHeight());
        simulation.populate();
        boidDrawer = new BoidDrawer(batch);

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            manager.setScreen(new PlayScreen(manager));
        }
        simulation.setCursor(getCursorPosInWorld());
        simulation.update(delta);
        draw(simulation.getVehicles());
    }

    public void draw(Array<Vehicle> vehicles) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        for (Vehicle v : vehicles) {
            boidDrawer.draw(v);
        }
        batch.end();
    }

    public Vector3 getCursorPosInWorld(){
        return cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }


    @Override
    public void resize(int width, int height) {
        System.out.println("Resizing");

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
