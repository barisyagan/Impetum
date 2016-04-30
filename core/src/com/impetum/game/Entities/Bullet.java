package com.impetum.game.Entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.impetum.game.AssetLoader;
import com.impetum.game.Entities.Character.Direction;

public class Bullet extends GameObject{
	private Circle bulletBounds;
	private float bulletBoundsRadius=2;
	private float posX;
	private float posY;
	
	float speed = 1000;
	float speedX = 0;
	float speedY = 0;
	
	Weapon weapon;
	
	static Sprite bulletSprite = new Sprite(AssetLoader.bulletTexture);
	Sprite fireSprite;
	Direction direction;
	
	
	Random random;
	ShapeRenderer renderer;
	
	public Bullet(float posX, float posY, Weapon weapon, float rotation){
		currentTexture = AssetLoader.bulletTexture;
		bulletSprite.setOrigin(bulletSprite.getWidth()/2, 0);
		bulletSprite.setRotation(rotation);
		
		this.posX = posX;
		this.posY = posY;
		bulletBounds = new Circle(posX,posY,bulletBoundsRadius);
		this.weapon = weapon;
		
		random = new Random();
		initializeMovement();
	}
	
	public Bullet(float x, float y, Weapon weapon, Survivor survivor){
		
		this.weapon = weapon;
		this.posX = survivor.getX();
		this.posY = survivor.getY();
		speedX = x;
		speedY = y;
		bulletSprite.setOrigin(bulletSprite.getWidth()/2, 0);
		bulletSprite.setRotation(weapon.getRotation());
	}
	
	private void initializeMovement() {
		direction = weapon.getCurrentDirection();
		switch (direction) {
		case North:
			speedX = 0 + getRandomAccuracy();
			speedY = speed;
			break;
		case NorthEast:
			speedX = speed + getRandomAccuracy();
			speedY = speed + getRandomAccuracy();
			break;
		case East:
			speedX = speed;
			speedY = 0 + getRandomAccuracy();
			break;
		case SouthEast:
			speedX = speed + getRandomAccuracy();
			speedY = -speed + getRandomAccuracy();
			break;
		case South:
			speedX = 0 + getRandomAccuracy();
			speedY = -speed + getRandomAccuracy();
			break;
		case SouthWest:
			speedX = -speed + getRandomAccuracy();
			speedY = -speed + getRandomAccuracy();
			break;
		case West:
			speedX = -speed;
			speedY = 0 + getRandomAccuracy();
			break;
		case NorthWest:
			speedX = -speed + getRandomAccuracy();
			speedY = speed + getRandomAccuracy();
			break;
		}
	}

	public void draw(SpriteBatch batch){
		posX += speedX * Gdx.graphics.getDeltaTime();
		posY += speedY * Gdx.graphics.getDeltaTime();
		bulletSprite.setX(posX);
		bulletSprite.setY(posY);
		if (bulletBounds != null){
		bulletBounds.setX(posX);
		bulletBounds.setY(posY);
		}
		bulletSprite.draw(batch);
		
	}
	
	/*
	public void drawLine(ShapeRenderer renderer){
		renderer.setProjectionMatrix(GameScreen.camera.combined);
		renderer.setColor(0, 0, 0, 1);
		renderer.begin(ShapeType.Line);
		renderer.line(posX, posY, weapon.getX(), weapon.getY());
		renderer.end();
	}
	*/
	
	private float getRandomAccuracy(){
		int accuracyDirection = random.nextBoolean() ? 1: -1;
		return accuracyDirection * ((weapon.accuracy-weapon.accuracy/3) * random.nextFloat() + weapon.accuracy/3);
	}
	
	@Override
	public float getX() {
		return posX;
	}

	@Override
	public float getY() {
		return posY;
	}
	
	
	public float getSpeedX(){
		return speedX;
	}

	@Override
	public Texture getTexture() {
		return currentTexture;
	}

	@Override
	public void setTexture(Texture texture) {
		currentTexture = texture;
	}

	public float getSpeedY() {
		return speedY;
	}

}
