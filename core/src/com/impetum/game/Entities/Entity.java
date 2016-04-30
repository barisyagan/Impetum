package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;

public interface Entity {
	
	public float getX();
	public float getY();
	
	public Texture getTexture();
	public void setTexture(Texture texture);
}
