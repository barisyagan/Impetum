package com.impetum.game.Entities;


public abstract class Enemy extends Character {
	public Enemy(float x, float y, int id) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
	}
	public float healthPoint=0;
	public Weapon weapon=null;
	
	public boolean isNull() {
		if (this.equals(null)) return true;
		return false;
	}
	public void recoil() {
		// TODO Auto-generated method stub
		
	}
	public void loseHealth(float damage) {
		// TODO Auto-generated method stub
		
	}

}
