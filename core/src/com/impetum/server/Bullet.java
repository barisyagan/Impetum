package com.impetum.server;

public class Bullet {
	private float x,y;
	private float speedX, speedY;
	private long lastTime;
	private static final int DAMAGE = 5;
	
	//For CollisionCheck
	private double bulletRadius = 1;
		
	public Bullet(User user, String blCoords) {
		x = user.getX();
		y = user.getY();
		String[] coords = blCoords.split(",");
		speedX = Float.parseFloat(coords[0]);
		speedY = Float.parseFloat(coords[1]);
	}

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


	public float getSpeedX() {
		return speedX;
	}


	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}


	public float getSpeedY() {
		return speedY;
	}


	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	
	public void move(){
		//float delta = (float) ((System.currentTimeMillis() - lastTime)/1000000000000.0);
		//lastTime = System.currentTimeMillis();
		//x += (speedX * delta);
		//y += (speedY * delta);
		x += (speedX/1200.0);
		y += (speedY/1200.0);
		//System.out.println(speedX * delta);
		//System.out.println(speedY * delta);
	}

	public boolean outOfBounds() {
		if (x > Game.backgroundLimitWidth || y > Game.backgroundLimitHeight || y < -Game.backgroundLimitHeight || x < -Game.backgroundLimitWidth)
			return true;
		return false;
	}
	
	public int getDamage(){
		return DAMAGE;
	}

	public double getBulletRadius(){
		return bulletRadius;
	}
}
