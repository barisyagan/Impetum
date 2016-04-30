package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;

public class Barrel extends Disposable {

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public boolean isDestroyed() {
		if(super.healthPoint>0){
			return false;
		}
		return true;
	}
	
	public void explode(){
		
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
