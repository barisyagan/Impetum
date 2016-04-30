package com.impetum.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.impetum.game.Networking.Networking;

import Screens.GameScreen;
import Screens.IntroScreen;
import Screens.MainMenuScreen;


public class ImpetumGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer renderer;
	
	public static String username;
	
	AssetLoader assetLoader;

	@Override
	public void create () {
		setScreenSize();
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		renderer = new ShapeRenderer();
		
		loadAssets();
		
		this.setScreen(new MainMenuScreen(this)); // to be implemented
		//this.setScreen(new IntroScreen(this));
		
		
		//connectToServer();
		
		//this.setScreen(new GameScreen(this));
	}

	private void loadAssets() {
		assetLoader = new AssetLoader();
	}

	private void setScreenSize() {
		/*Gdx.graphics.setDisplayMode(
				Gdx.graphics.getDesktopDisplayMode().width,
	            Gdx.graphics.getDesktopDisplayMode().height, 
	            true);*/
	}

	private void connectToServer() {
		Networking.connectServer();
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
    }
}
