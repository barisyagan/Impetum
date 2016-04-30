package com.impetum.server;

import java.util.Iterator;
import java.util.LinkedList;

import com.impetum.server.Zombie;
import com.impetum.server.Networking.Packet;

public class Game {
	State state = State.SETUP;
	private Room room;
	private GameController gameController;
	private LinkedList<User> users = new LinkedList<User>();
	private LinkedList<Zombie> zombies = new LinkedList<Zombie>();
	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	private ZombieFactory zombieFactory = new ZombieFactory();
	private Map map = new Map(zombies);
	
	
	public static float backgroundLimitWidth = 300;
	public static float backgroundLimitHeight = 300;
	
	private long gameStartTime;
	private long gameTime;
	
	public Game(GameController gameController, Room lastRoom) {
		room = lastRoom;
		this.gameController = gameController;
		users.addAll(room.getUsers());
	}





	public void tick(){
		switch(state){
		case SETUP:
			setup();
			break;
		case WAITING:
			waiting();
			break;
		case PLAYING:
			play();
			break;
		case ENDED:
			end();
			break;
		}
	}

	
	


	private void setup() {
		gameStartTime = System.currentTimeMillis();
		state = State.PLAYING;
		map = new Map(zombies);
	}


	private void waiting() {
		// TODO Auto-generated method stub
		
	}
	
	int count = 0;
	private void play() {
		updateTime();
		Iterator<User> it = users.iterator();
		while(it.hasNext()){
			
			User user = it.next();
			if (!user.isConnected()){
				it.remove();
				continue;
			}
			Packet packet;
			while((packet = gameController.readUserPacket(user)) != null){
				executePacket(user, packet);
			}
		}
		handleBullets();
		if (zombies.size() == 0){
			zombieFactory.increaseWave();
			Packet packet = new Packet();
			packet.incrementWave(zombieFactory.getWave());
			packet.signPacket();
			packet.compressPacket();
			packet.finalizePacket();
			informAll(packet);	
			spawnZombies();
		}
		if (count == 0){
			moveZombies();
			count++;
		} else
			count--;
		checkZombieCollusion();
		
	}

	private void updateTime() {
		long time = System.currentTimeMillis();
		gameTime = time - gameStartTime;		
	}





	private void handleBullets() {
		Iterator<Bullet> it = bullets.iterator();
		while(it.hasNext()){
			Bullet bullet = it.next();
			bullet.move();
			checkCollision(bullet);
			if(bullet.outOfBounds())
				it.remove();
		}
	}


	private void checkCollision(Bullet bullet) {
		Iterator<Zombie> it = zombies.iterator();
		while(it.hasNext()){
			Zombie zombie = it.next();
			if(collisionExist(bullet,zombie)){
				System.out.println("collusion");
				int newHealth = zombie.getHealth()-bullet.getDamage();
				if(newHealth <= 0 ){
					System.out.println("Zombie died");
					Packet packet = new Packet();
					packet.setZombieDeath(zombie);
					packet.preparePacket();
					informAll(packet);
					it.remove();
				}else{
					zombie.setHealth(newHealth);
				}
				
			}
			
		}
		
	}

	//Applied Formula is sqrt((x1-x2)^2+(y1-y2)^2)< r1 + r2 
	private boolean collisionExist(Bullet bullet, Zombie zombie) {
		
		double arg1 = Math.sqrt(Math.pow(bullet.getX()-zombie.getX(),2)+Math.pow(bullet.getY()-zombie.getY(), 2));
		double arg2 = zombie.getZombieRadius() + bullet.getBulletRadius();
		return (arg1 < arg2);
		
	}



	private void executePacket(User user, Packet packet) {
		if (packet.playerMovementPacket()){
			Packet newPacket = new Packet();
			user.move(packet.getCoords());
			newPacket.createMovementPacket(packet.getBody(), user);
			newPacket.signPacket();
			newPacket.compressPacket();
			
			
			newPacket.finalizePacket();
			informAllOthers(user, newPacket);
			return;
		}
		if (packet.playerAttackingPacket()){
			Packet newPacket = new Packet();
			newPacket.createBulletPacket(packet.getData(), user);
			newPacket.signPacket();
			newPacket.compressPacket();
			
			
			newPacket.finalizePacket();
			Bullet bullet = new Bullet(user, packet.getBLCoords());
			bullets.add(bullet);
			informAllOthers(user, newPacket);
		}
		
	}
	
	private void informAllOthers(User user, Packet packet){
		for (User oneUser : users)
			if (!oneUser.equals(user)){
				oneUser.sendData(packet.getData());
			}
	}

	private void informAll(Packet packet){
		for (User oneUser : users)
			oneUser.sendData(packet.getData());
			
	}
	
	private void spawnZombies() {
		LinkedList<Zombie> newZombies = zombieFactory.spawnWave();
		Iterator<Zombie> it = newZombies.iterator();
		while(it.hasNext()){
			Zombie zombie = it.next();
			Packet packet = new Packet();
			packet.createZombie(zombie);
			packet.preparePacket();
			informAll(packet);
		}
		zombies.addAll(newZombies);
		
		
	}
	
	private void moveZombies() {
		Iterator<Zombie> it = zombies.iterator();
		while(it.hasNext()){
			Zombie zombie = it.next();
			zombie.move(users);
			Packet packet = new Packet();
			packet.zombieMovement(zombie);
			packet.preparePacket();
			informAll(packet);
		}
		
	}
	
	private void checkZombieCollusion() {
		// TODO Auto-generated method stub
		
	}
	
	

	private void end() {
		// TODO Auto-generated method stub
		
	}


	public boolean isOver() {		
		return state.equals(State.ENDED);
	}
}


enum State {
    SETUP,WAITING, PLAYING, ENDED
}
