package com.impetum.game.Entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.impetum.game.Entities.Character.Direction;

public abstract class Weapon extends GameObject {
	public float damage;
	public float range;
	public float fireRate;
	public float pierce;
	public float recoil;
	public float accuracy;

	Survivor survivor;
	
	Sound sound;
	
	public abstract void fire();
	public abstract void draw(SpriteBatch batch);
	public abstract Direction getCurrentDirection();
	public abstract float getRotation();
	
}
