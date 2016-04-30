package com.impetum.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.impetum.game.Entities.Character.Direction;

public class MachineGun extends Weapon {
	public float damage=0;
	public float range=0;
	public float fireRate=0;
	public float pierce=0;
	public float recoil=0;
	
	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(SpriteBatch batch){
		switch (survivor.getCurrentDirection()) {
		case North:
			
			break;
		case NorthEast:
			
			break;
		case East:
			
			break;
		case SouthEast:
			
			break;
		case South:
			
			break;
		case SouthWest:
			
			break;
		case West:
			
			break;
		case NorthWest:
			
			break;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public float getRotation() {
		return 0;
	}


}
