package com.impetum.server;

import java.util.ArrayList;
import java.util.LinkedList;

import com.impetum.server.Zombie;

public class ZombieFactory {
	
	private static byte id = 0;
	private int wave = 0;
	
	public ZombieFactory(){
	}
	
	public void increaseWave(){
		wave++;
	}
	
	public LinkedList<Zombie> spawnWave(int wave){
		switch(wave){
		case 1:
			return spawnZombies(2);
		case 2:
			return spawnZombies(3);
		case 3:
			return spawnZombies(4);
		default:
			return spawnZombies(5);
		}
	}
	
	public LinkedList<Zombie> spawnZombies(int number){
		LinkedList<Zombie> zombies = new LinkedList<Zombie>();
		for (int i= 0 ; i< number ; i++)
			zombies.add(new Zombie(id++));
		return zombies;
	}

	public int getWave() {
		return wave;
	}

	public LinkedList<Zombie> spawnWave() {
		return spawnWave(wave);
	}
}
