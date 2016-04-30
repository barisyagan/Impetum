package com.impetum.game.Entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.impetum.game.Entities.Character.Direction;
import com.impetum.game.Networking.Networking;
import com.impetum.game.Networking.Packet;

import Screens.GameScreen;

public class Pistol extends Weapon {
	
	float posX;
	float posY;
	
	Direction currentDirection;
	
	Sprite gunSprite;
	private float gunHeight;
	float rotation = 0;
	
	Texture fireTexture;
	Sprite fireSprite;
	
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	long fireTime=0;
	
	private boolean isFire = false;
	
	public Pistol(Survivor survivor) {
		currentTexture = new Texture("Weapons/Pistol.png");
		
		gunSprite = new Sprite(currentTexture);
		gunSprite.setOrigin(gunSprite.getWidth()/2, 0);
		gunHeight = gunSprite.getHeight();
		
		fireTexture = new Texture("Weapons/Fire.png");
		fireSprite = new Sprite(fireTexture);
		fireSprite.setOrigin(fireSprite.getWidth()/2, 0);
		
		sound = Gdx.audio.newSound(Gdx.files.internal("sounds/PistolShot.wav"));
		
		initializeInstances();
		
		this.survivor = survivor;
		currentDirection = survivor.getCurrentDirection();
	}
	
	private void initializeInstances() {
		super.damage = 5;
		super.range = 0;
		super.fireRate=0.5f; //0.8f
		super.pierce=0;
		super.recoil=1;
		super.accuracy=100f; // 0 is the best
	}

	@Override
	public void draw(SpriteBatch batch){
		currentDirection = survivor.getCurrentDirection();
		
		switch (currentDirection) {
		case North:
			rotation = 0;
			posX = survivor.getX() - 5;
			posY = survivor.getY() + 15;
			fireSprite.setPosition(posX, posY+gunHeight);
			break;
		case NorthEast:
			rotation = 315;
			posX = survivor.getX() + 10;
		    posY = survivor.getY() + 12;
		    fireSprite.setPosition(posX+gunHeight*2/3, posY+gunHeight*2/3);
			break;
		case East:
			rotation = 270;
			posX = survivor.getX() + 10;
		    posY = survivor.getY();
		    fireSprite.setPosition(posX+gunHeight, posY);
			break;
		case SouthEast:
			rotation = 225;
			posX = survivor.getX() + 6;
		    posY = survivor.getY() - 14;
		    fireSprite.setPosition(posX+gunHeight*2/3, posY-gunHeight*2/3);
			break;
		case South:
			rotation = 180;
			posX = survivor.getX() - 5;
		    posY = survivor.getY() - 15;
		    fireSprite.setPosition(posX, posY-gunHeight);
			break;
		case SouthWest:
			rotation = 135;
			posX = survivor.getX() - 22;
		    posY = survivor.getY() - 15;
		    fireSprite.setPosition(posX-gunHeight*2/3, posY-gunHeight*2/3);
			break;
		case West:
			rotation = 90;
			posX = survivor.getX() - 20;
		    posY = survivor.getY();
		    fireSprite.setPosition(posX-gunHeight, posY);
			break;
		case NorthWest:
			rotation = 45;
			posX = survivor.getX() - 18;
		    posY = survivor.getY() + 15;
		    fireSprite.setPosition(posX-gunHeight*2/3, posY+gunHeight*2/3);
			break;
		}
		
		drawBullets(batch);

		gunSprite.setRotation(rotation);
		gunSprite.setX(posX);
		gunSprite.setY(posY);
		gunSprite.draw(batch);
		
		if(isFire){
			fireSprite.setRotation(rotation);
			fireSprite.draw(batch);
			isFire = false;
		}
		
	}
	
	private void drawBullets(SpriteBatch batch) {
		for(int i=0; i<bullets.size(); i++){
			// remove if bullet is out of map
			if(bullets.get(i).getX() > GameScreen.backgroundLimitWidth
			|| bullets.get(i).getX() < -GameScreen.backgroundLimitWidth
			|| bullets.get(i).getY() > GameScreen.backgroundLimitHeight
			|| bullets.get(i).getY() < -GameScreen.backgroundLimitHeight)
			{
				bullets.remove(i);
			}else{
				bullets.get(i).draw(batch);
			}
			
		}
	}
	
	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public float getX() {
		return posX;
	}

	@Override
	public float getY() {
		return posY;
	}

	@Override
	public void fire() {
		if(TimeUtils.millis()-fireTime > fireRate * 1000){
			Bullet bullet = new Bullet(getX(), getY(), this, rotation);
			bullets.add(bullet);
			sound.play();
			isFire = true;
			fireTime = TimeUtils.millis();
			Packet packet = new Packet();
			packet.prepareBullet(bullet);
			packet.signPacket();
			packet.compressPacket();
			packet.finalizePacket();
			Networking.addPacketToQueue(packet);
		}
		
	}

	@Override
	public Texture getTexture() {
		return currentTexture;
	}

	@Override
	public void setTexture(Texture texture) {
		currentTexture = texture;
	}

	@Override
	public Direction getCurrentDirection() {
		return currentDirection;
	}

}
