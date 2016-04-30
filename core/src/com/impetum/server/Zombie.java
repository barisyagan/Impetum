package com.impetum.server;

import java.util.LinkedList;
import java.util.Random;

public class Zombie {
	private static Random random = new Random();
	
	private float x,y;
	private float speedX, speedY;
	private int health = 20;
	private long lastTime;
	private byte id;
	//For CollisionCheck
	private double zombieRadius = 20;
	
	int randomUser;

	public Zombie(byte id) {
		this.id = id;
		int zone = random.nextInt(4);
		if (zone == 0){
			x = random.nextFloat() * Game.backgroundLimitWidth;
			y = random.nextFloat() * Game.backgroundLimitHeight;
		} else if (zone == 1){
			x = (random.nextFloat() * Game.backgroundLimitWidth) * -1;
			y = random.nextFloat() * Game.backgroundLimitHeight;
		} else if (zone == 2){
			x = random.nextFloat() * Game.backgroundLimitWidth;
			y = (random.nextFloat() * Game.backgroundLimitHeight) * -1;
		} else if (zone == 3){
			x = (random.nextFloat() * Game.backgroundLimitWidth) * -1;
			y = (random.nextFloat() * Game.backgroundLimitHeight) * -1;
		}
		
		speedX = 0.14f;
		speedY = 0.14f;
		randomUser = random.nextInt(6);
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public double getZombieRadius(){
		return zombieRadius;
	}
	
	public void move(LinkedList<User> users){
		   //User user = getNearestUser(users);
		   User user = users.get(randomUser%users.size());
		   if(getDistance(getX(),user.getX(),getY(),user.getY())>0){
		     if(user.getX()>getX()){
		       x+=speedX;
		     }else if(user.getX()<getX()){
		       x-=speedX;
		     }
		     if(user.getY()>getY()){
		       y+=speedY;
		     }else if(user.getY()<getY()){
		       y-=speedY;
		     }
		   }
		   
		}

	private User getNearestUser(LinkedList<User> users) {
		double nearest = getDistance(getX(), users.get(0).getX(), getY(), users.get(0).getY());
		User user = users.get(0);
		for(int i=1;i<users.size();i++){
			double dist = getDistance(getX(), users.get(i).getX(), getY(), users.get(i).getY());
			if(dist < nearest){
				nearest = dist;
				user = users.get(i);
			}
		}
		return user;
	}
	
	double getDistance(float x1, float x2, float y1, float y2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2, 2));
	}
	
}
