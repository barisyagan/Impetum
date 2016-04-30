package com.impetum.game;

import java.util.ArrayList;

import com.impetum.game.Entities.Enemy;
import com.impetum.game.Entities.Survivor;

/*
 
public class Game {
	
	CurrentSituation currentSituation = null;
	ZombieSpawner zombieSpawner = new ZombieSpawner();
	int level;
	
	public void runTheGame() {
		currentSituation.zombieNumber = 0;
		currentSituation.aliveSurvivors.add(null);
		currentSituation.aliveSurvivors.add(null);
		currentSituation.aliveSurvivors.add(null);
		currentSituation.aliveSurvivors.add(null);
		currentSituation.aliveSurvivors.add(null);
		currentSituation.aliveSurvivors.add(null);
		
		while (currentSituation.areSurvivorsAlive()) {
			if (currentSituation.zombieNumber == 0) {
				
				Wave wave = new Wave(level);
				zombieSpawner.spawnZombie(wave);
			}
			else {
				
				ArrayList<Survivor> survivorsFiringGun = getSurvivorsFiringGun();
				while (survivorsFiringGun.size() != 0) {
					//ArrayList<Enemy> enemyAffectedList = new ArrayList<Enemy>();
					for (int i = 0; i < survivorsFiringGun.size(); i++) {
						survivorsFiringGun.get(i).enemyAffectedList.add(survivorsFiringGun.get(i).getEnemyInGunEffectArea());
					}
					for (int i = 0; i < survivorsFiringGun.size(); i++) {
						if (survivorsFiringGun.get(i).enemyAffectedList.size() != 0) {
							for (int j = 0; j < survivorsFiringGun.get(i).enemyAffectedList.size(); i++) {
								survivorsFiringGun.get(i).enemyAffectedList.get(i).recoil();
								survivorsFiringGun.get(i).enemyAffectedList.get(i).loseHealth(survivorsFiringGun.get(i).weapon.damage);
								if (survivorsFiringGun.get(i).enemyAffectedList.get(i).healthPoint == 0) sendEnemiesToSurvivor();
							}
						}
						else sendEnemiesToSurvivor();
					}
				}
				ArrayList<Enemy> enemiesReachedToSurvivors = enemiesReachedToSurvivors();
				while (enemiesReachedToSurvivors.size() != 0)
				for (int i = 0; i < enemiesReachedToSurvivors.size(); i++) {
					enemiesReachedToSurvivors.get(i).attack();
					if (checkDeadSurvivor()) terminateDeadSurvivor();
				}
			}
			
		}
		terminateGame();
		
		
	}
	
	private ArrayList<Survivor> getSurvivorsFiringGun() {
		ArrayList<Survivor> survivorsFiringGun;
		for (int i = 0; i < currentSituation.aliveSurvivors.size(); i++) {
			//if (currentSituation.aliveSurvivors.get(i).)
		}
		return null;
	}
	
	private void sendEnemiesToSurvivor() {
		// TODO Auto-generated method stub
		
	}
	
	private ArrayList<Enemy> enemiesReachedToSurvivors() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void terminateDeadSurvivor() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean checkDeadSurvivor() {
		// TODO Auto-generated method stub
		return false;
	}

	private void terminateGame() {
		// TODO Auto-generated method stub
		
	}
	
}

*/
