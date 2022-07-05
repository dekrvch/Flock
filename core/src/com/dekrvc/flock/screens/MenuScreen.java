package com.dekrvc.flock.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
    private Manager manager;
    private OrthographicCamera cam;
    private Viewport vp;
    private Stage stage;


    public MenuScreen(Manager sm){
        manager = sm;
        cam = new OrthographicCamera();
        vp = new ExtendViewport(960, 540, cam);
        vp.apply();
        stage = new Stage(vp);
        cam.position.set(cam.viewportWidth / 2, cam.viewportHeight / 2, 0);
        cam.update();
    }

    @Override
    public void show() {
        Label text = new Label("Touch to Play", manager.labelStyle);
        text.setPosition((vp.getWorldWidth() - text.getWidth())/2,
                (vp.getWorldHeight() - text.getHeight())/2);
        stage.addActor(text);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            manager.setScreen(new PlayScreen(manager));
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
