package com.impetum.game;

import java.util.ArrayList;
import java.util.LinkedList;

import com.impetum.game.Entities.Survivor;
import com.impetum.game.Entities.Zombie;

import Screens.GameScreen;


public class CurrentSituation {
	
	/*
	public int zombieNumber = 0;
	ArrayList<Survivor> aliveSurvivors = new ArrayList<Survivor>();
	
	public boolean areSurvivorsAlive() {
		return (aliveSurvivors.size() != 0);
	}
	*/
	
	public static int wave = 1;
	public static int zombieNumber = 0;
	
	static LinkedList<Zombie> zombies = GameScreen.zombies;
	
	public static LinkedList<Zombie> getZombies(){
		return zombies;
	}
	
	public static int getZombieNumber(){
		zombieNumber = zombies.size();
		return zombieNumber;
	}
	
}
