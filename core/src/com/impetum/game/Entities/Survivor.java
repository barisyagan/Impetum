package com.impetum.game.Entities;

import java.util.ArrayList;
import java.util.Calendar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.impetum.game.CurrentSituation;
import com.impetum.game.ImpetumGame;
import com.impetum.game.Networking.Networking;
import com.impetum.game.Networking.Packet;

import Screens.GameScreen;

public class Survivor extends Character {
	
	//private float walkSpeed = 200;
	//private float playerBoundRadius = 64;
	
	//private Direction currentDirection = Direction.North;
	public ArrayList<Enemy> enemyAffectedList;
	public String userName;
	
	//private Circle playerBounds;
	public Texture[] survivorTextures;
	public Texture currentTexture;
	
	private int score = 0;
	
	//private int id;
	
	/*public Survivor(float x, float y) {
		survivorTextures = new Texture[8];
		loadTextures();
		currentTexture = survivorTextures[0];
		
		playerBounds = new Circle();
		playerBounds.x = x;
		playerBounds.y = y;
		playerBounds.radius = playerBoundRadius;
	}*/
	
	
	public Survivor(float x, float y, int id, String userName) {
		super(x, y, id);
		survivorTextures = new Texture[8];
		loadTextures();
		currentTexture = survivorTextures[0];
		super.weapon = new Pistol(this);
		this.userName = userName;
	}
	
	/*public Survivor(float x, float y, int id) {
		this.id = id;
		survivorTextures = new Texture[8];
		loadTextures();
		currentTexture = survivorTextures[0];
		
		playerBounds = new Circle();
		playerBounds.x = x;
		playerBounds.y = y;
		playerBounds.radius = playerBoundRadius;
	}
	*/
	
	/*public int getID(){
		return id;
	}*/
	/*
	@Override
	public float getX() {
		return playerBounds.x;
	}

	@Override
	public float getY() {
		return playerBounds.y;
	}
	
	public void setX(float x){
		playerBounds.x = x;
	}
	
	public void setY(float y){
		playerBounds.y = y;
	}


	@Override
	public int getHealth() {
		return healthPoint;
	}

	@Override
	public void setHealth(int health) {
		healthPoint = health;
	}
	*/
	/*
	@Override
	public void attack() {
		weapon.fire();
	}
	*/
	public Weapon getCurrentWeapon(){
		return weapon;
	}
	
	public Texture getTexture(){
		return currentTexture;
	}
	
	float x;
	public void draw(ImpetumGame impetumGame){
		impetumGame.batch.begin();
		x = impetumGame.font.draw(impetumGame.batch, "" + userName, 
        		getX() - x/2, 
        		getY() + 80).width;
		impetumGame.batch.draw(currentTexture, 
	               getX()-currentTexture.getWidth()/2, 
	               getY()-currentTexture.getHeight()/2);
		weapon.draw(impetumGame.batch);
		impetumGame.batch.end();
		
		impetumGame.renderer.setProjectionMatrix(GameScreen.camera.combined);
		impetumGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
		
		/*
		if(getHealth() < 25){
			impetumGame.renderer.setColor(Color.RED);
		} else if(getHealth() < 50){
			impetumGame.renderer.setColor(Color.ORANGE);
		} else if(getHealth() < 75){
			impetumGame.renderer.setColor(Color.YELLOW);
		} else{
			impetumGame.renderer.setColor(Color.GREEN);
		}
		*/
		/*
		if(Gdx.input.isKeyPressed(Keys.SPACE)){
	        	healthPoint--;
	    }
	    */
		float g = (255 * healthPoint) / 100;
		float r = (255 * (100 - healthPoint)) / 100; 
		impetumGame.renderer.setColor(r/255f, g/255f, 0, 1);
		
        impetumGame.renderer.rect(getX() - getHealth()/3/2-1, getY() + 50,
                getHealth()/3+2, 10);
		
		impetumGame.renderer.end();
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	@Override
	public void setCurrentTexture(Direction direction) {
		switch (direction) {
		case North:
			currentTexture = survivorTextures[0];
			break;
		case NorthEast:
			currentTexture = survivorTextures[1];
			break;
		case East:
			currentTexture = survivorTextures[2];
			break;
		case SouthEast:
			currentTexture = survivorTextures[3];
			break;
		case South:
			currentTexture = survivorTextures[4];
			break;
		case SouthWest:
			currentTexture = survivorTextures[5];
			break;
		case West:
			currentTexture = survivorTextures[6];
			break;
		case NorthWest:
			currentTexture = survivorTextures[7];
			break;
		}
	}
	
	@Override
	public void move(Direction direction) {
		super.move(direction);
		
		Packet packet = new Packet();
		packet.createMovementPacket(playerBounds.x, playerBounds.y);
		packet.signPacket();
		packet.compressPacket();
		packet.finalizePacket();
		//System.out.println(Calendar.getInstance().getTimeInMillis());
		Networking.sendPacket(packet);		
		
	}
	/*
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
	}*/
	
	@Override
	public void loadTextures(){
		survivorTextures[0] = new Texture("Survivor/SurvivorNorth.png");
		survivorTextures[1] = new Texture("Survivor/SurvivorNorthEast.png");
		survivorTextures[2] = new Texture("Survivor/SurvivorEast.png");
		survivorTextures[3] = new Texture("Survivor/SurvivorSouthEast.png");
		survivorTextures[4] = new Texture("Survivor/SurvivorSouth.png");
		survivorTextures[5] = new Texture("Survivor/SurvivorSouthWest.png");
		survivorTextures[6] = new Texture("Survivor/SurvivorWest.png");
		survivorTextures[7] = new Texture("Survivor/SurvivorNorthWest.png");
	}
	/*
	@Override
	public Direction getCurrentDirection() {
		return currentDirection;
	}

	@Override
	public void setCurrentDirection(Direction direction) {
		if(currentDirection!=direction) setCurrentTexture(direction);
		currentDirection = direction;
	}
	*/
	public boolean isSurvivorNull() {
		if (this.equals(null)) return true;
		return false;
	}

	public Enemy getEnemyInGunEffectArea() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enemy[] getEnemiesInGunEffectArea() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}



