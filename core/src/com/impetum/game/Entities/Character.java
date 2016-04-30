package com.impetum.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.impetum.game.Entities.Character.Direction;

import Screens.GameScreen;

public abstract class Character implements Entity {
	public int healthPoint = 100;
	public Weapon weapon;
	private Direction currentDirection = Direction.North;
	public Circle playerBounds;
	private Texture[] survivorTextures;
	private float walkSpeed = 200;
	private float playerBoundRadius = 64;
	
	private int id;
	
	public Character(float x, float y, int id) {
		this.id = id;
		//survivorTextures = new Texture[8];
		//loadTextures();**
		//currentTexture = survivorTextures[0];
		
		playerBounds = new Circle();
		playerBounds.x = x;
		playerBounds.y = y;
		playerBounds.radius = playerBoundRadius;
		
	}
	
	public abstract void setCurrentTexture(Direction direction);
	abstract void loadTextures();
	
	public  void attack() {
		weapon.fire();
	}
	
	public void move(Direction direction) {
		float x = playerBounds.x;
		float y = playerBounds.y;
		switch(direction){
		case West:
			x += - walkSpeed * Gdx.graphics.getDeltaTime();
			break;
		case East:
			x += walkSpeed * Gdx.graphics.getDeltaTime();
			break;
		case North:
			y += walkSpeed * Gdx.graphics.getDeltaTime();
			break;
		case South:
			y += - walkSpeed * Gdx.graphics.getDeltaTime();
			break;
		case NorthWest:
			x += - walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			y += walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			break;
		case NorthEast:
			x += walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			y += walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			break;
		case SouthWest:
			x += - walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			y += - walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			break;
		case SouthEast:
			x += walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			y += - walkSpeed / 2 * Gdx.graphics.getDeltaTime();
			break;
		}
		setCurrentDirection(x, y);
		playerBounds.x = x;
		playerBounds.y = y;
		checkMapLimits();	
	}
	public  Direction getCurrentDirection(){
		return currentDirection;
	}
	public void setCurrentDirection(float x , float y) {
		/*
		if(currentDirection!=direction)  setCurrentTexture(direction);
		currentDirection = direction;
		*/
		//currentDirection = Direction.North;
		if ((x-playerBounds.x > 0) && (y-playerBounds.y > 0)) 
			currentDirection = Direction.NorthEast;
		else if ((x-playerBounds.x < 0) && (y-playerBounds.y < 0)) 
			currentDirection = Direction.SouthWest;
		else if ((x-playerBounds.x > 0) && (y-playerBounds.y < 0)) 
			currentDirection = Direction.SouthEast;
		else if ((x-playerBounds.x < 0) && (y-playerBounds.y > 0)) 
			currentDirection = Direction.NorthWest;
		else if (x-playerBounds.x > 0) 
			currentDirection = Direction.East;
		else if (x-playerBounds.x < 0) 
			currentDirection = Direction.West;
		else if (y-playerBounds.y > 0) 
			currentDirection = Direction.North;
		else if (y-playerBounds.y < 0) 
			currentDirection = Direction.South;
		setCurrentTexture(currentDirection);
		
	}
	
	

	private void checkMapLimits() {
		if(playerBounds.x + playerBounds.radius/2 > GameScreen.backgroundLimitWidth){
			playerBounds.x = GameScreen.backgroundLimitWidth - playerBounds.radius/2;
		}
		if(playerBounds.x - playerBounds.radius/2 < -GameScreen.backgroundLimitWidth){
			playerBounds.x = -GameScreen.backgroundLimitWidth + playerBounds.radius/2;
		}
		if(playerBounds.y + playerBounds.radius/2 > GameScreen.backgroundLimitHeight){
			playerBounds.y = GameScreen.backgroundLimitHeight - playerBounds.radius/2;
		}
		if(playerBounds.y - playerBounds.radius/2< -GameScreen.backgroundLimitHeight){
			playerBounds.y = -GameScreen.backgroundLimitHeight + playerBounds.radius/2;
		}
	}
	
	public int getID(){
		return id;
	}
	
	public float getX() {
		return playerBounds.x;
	}

	public float getY() {
		return playerBounds.y;
	}
	
	public void setCoordinates(float x, float y){
		setCurrentDirection(x, y);
		playerBounds.x = x;
		playerBounds.y = y;
	}
	
	public void setX(float x){
		playerBounds.x = x;
	}
	
	public void setY(float y){
		playerBounds.y = y;
	}

	public int getHealth() {
		return healthPoint;
	}

	public void setHealth(int health) {
		healthPoint = health;
	}
	
	
	
	public enum Direction {
		West,
		East,
		North,
		South,
		NorthWest,
		NorthEast,
		SouthWest,
		SouthEast
	}
	
	
	

}

