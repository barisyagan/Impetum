package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.impetum.game.AssetLoader;
import com.impetum.game.ImpetumGame;
import com.impetum.game.Entities.Character.Direction;

public class Zombie extends Enemy {
	//public static int healthPoint=100;
	//public Weapon weapon= new ZombieGun();
	public static Texture[] zombieTextures;
	
	public Texture currentTexture;
	//private Direction currentDirection = Direction.North;
	
	//private Circle zombieBounds;
	
	//For CollisionCheck
	private double zombieRadius = 20;

	private int id;
	
	public Zombie(float x, float y, int id) {
		super(y, y, id);
		zombieTextures = new Texture[8];
		loadTextures();
		currentTexture = zombieTextures[0];
		super.weapon = new ZombieGun();
		/*zombieBounds = new Circle();
		zombieBounds.x = x;
		zombieBounds.y = y;*/
		
	}
	
	public Zombie(float x, float y, int id, boolean placeholder){
		super(y, y, id);
		zombieTextures = AssetLoader.zombieTextures;
		currentTexture = zombieTextures[0];
	}
	
	public Weapon getCurrentWeapon(){
		return weapon;
	}
	
	public Texture getTexture() {
		return currentTexture;
	}
	
	public double getZombieRadius(){
		return zombieRadius;
	}
	
	/*
	@Override
	public float getX() {
		return zombieBounds.x;
	}

	@Override
	public float getY() {
		return zombieBounds.y;
	}

	@Override
	public int getHealth() {
		return healthPoint;
	}

	@Override
	public void setHealth(int health) {
		healthPoint = health;
	}
	
	
	@Override
	public void attack() {
	    weapon.fire();
	}
	*/
	@Override
	public void move(Direction direction) {
		// TODO Auto-generated method stub
	}
	/*
	@Override
	public void setCurrentDirection(Direction direction) {
		currentDirection = direction;
	}

	@Override
	public Direction getCurrentDirection() {
		return currentDirection;
	}
	*/

	public void setCurrentTexture(Direction direction) {
		switch (direction) {
		case North:
			currentTexture = zombieTextures[0];
			break;
		case NorthEast:
			currentTexture = zombieTextures[1];
			break;
		case East:
			currentTexture = zombieTextures[2];
			break;
		case SouthEast:
			currentTexture = zombieTextures[3];
			break;
		case South:
			currentTexture = zombieTextures[4];
			break;
		case SouthWest:
			currentTexture = zombieTextures[5];
			break;
		case West:
			currentTexture = zombieTextures[6];
			break;
		case NorthWest:
			currentTexture = zombieTextures[7];
			break;
		}
	}

	@Override
	void loadTextures() {
		zombieTextures[0] = new Texture("zombie/ZombieNorth.png");
		zombieTextures[1] = new Texture("zombie/ZombieNorthEast.png");
		zombieTextures[2] = new Texture("zombie/ZombieEast.png");
		zombieTextures[3] = new Texture("zombie/ZombieSouthEast.png");
		zombieTextures[4] = new Texture("zombie/ZombieSouth.png");
		zombieTextures[5] = new Texture("zombie/ZombieSouthWest.png");
		zombieTextures[6] = new Texture("zombie/ZombieWest.png");
		zombieTextures[7] = new Texture("zombie/ZombieNorthWest.png");
		
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(ImpetumGame impetumGame){
		impetumGame.batch.draw(currentTexture, 
	               getX()-currentTexture.getWidth()/2, 
	               getY()-currentTexture.getHeight()/2);
		 //weapon.draw(impetumGame.batch);
	}
	

}
