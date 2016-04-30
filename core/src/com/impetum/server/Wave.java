package com.impetum.server;

import com.impetum.server.ZombieFactory;
public class Wave {
	public static ZombieFactory factory;
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
		factory = new ZombieFactory();
		factory.spawnZombies(zombieNumber);
	}	
	
}
