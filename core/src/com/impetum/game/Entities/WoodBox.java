package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;

public class WoodBox extends Disposable {

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Texture getTexture() {
		return currentTexture;
	}

	@Override
	public void setTexture(Texture texture) {
		currentTexture = texture;
	}
	
}
