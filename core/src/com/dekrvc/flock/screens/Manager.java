package com.dekrvc.flock.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Manager extends Game {
    private Screen menuScreen;
    public SpriteBatch batch;
    public Label.LabelStyle labelStyle;

    public Manager() {
        create();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2, 2);
        labelStyle = new Label.LabelStyle(font, Color.CYAN);
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
