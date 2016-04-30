package com.impetum.game;

/*
 * Baris Yagan S5019 Department of Computer Science
 */

public class Wave {
	public int zombieNumber = 0;
	public int bossNumber = 0;
	public float zombieDelay = 0;
	public float bossDelay = 0;
	
	public Wave(int zombieNumber, int bossNumber) {
		this.bossNumber = bossNumber;
		this.zombieNumber = zombieNumber;
	}

	public Wave(int level) {
		this.zombieNumber = level*50;
		this.bossNumber = level;
		this.zombieDelay = 1/level;
		this.bossDelay = 10/level;
	}	
	
}
