package com.impetum.server;

public class Player {
	private float x;
	private float y;
	private int health = 100;
	private boolean alive = true;
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void reduceHealth(int health){
		this.health -= health;
		if (this.health <= 0)
			alive = false;
	}
	public byte getHealth() {
		return (byte) health;
	}
	
	
	
	
	

}
