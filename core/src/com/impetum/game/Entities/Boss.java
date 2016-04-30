package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;

public class Boss extends Enemy {
	public Boss(float x, float y, int id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}

	public int healthPoint=0;
	public Weapon weapon=null;
	
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
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHealth(int health) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentDirection(float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Direction getCurrentDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void setCurrentTexture(Direction direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void loadTextures() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Texture getTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}

}
