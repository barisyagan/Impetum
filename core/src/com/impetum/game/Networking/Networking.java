package com.impetum.game.Networking;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import Screens.GameScreen;
import Screens.MainMenuScreen;

import com.impetum.game.Utilities.ByteUtils;
import com.impetum.game.CurrentSituation;
import com.impetum.game.ZombieSpawner;
import com.impetum.game.Entities.Bullet;
import com.impetum.game.Entities.Pistol;
import com.impetum.game.Entities.Survivor;
import com.impetum.game.Entities.Zombie;
import com.impetum.game.Networking.Packet;
import com.impetum.game.Networking.PacketListener;
import com.impetum.server.Utilities.Configuration;

import net.rudp.ReliableSocket;
import net.rudp.ReliableSocketOutputStream;

public class Networking {
	//private final static File client_config = new File("../assets/config.txt");
	private static ReliableSocket clientSocket;
	private static ReliableSocketOutputStream outputStream;
	private static InputStream inputStream;
	private final static ExecutorService executor = Executors.newSingleThreadExecutor();
	private static boolean connected = false;
	private static boolean usernameInit = false;
	private static boolean disconnected = false;
	private static boolean started = false;
	public static boolean test = false;
	private static Configuration config;
	// TODO: Read these from configure files.
	//private static String IP = "46.101.156.225";
	//private static String IP = "46.101.123.134";
	private static String IP = "127.0.0.1";
	private static int port = 4400;
	
	private static ConcurrentLinkedQueue<Packet> incomingPackets;
	private static ConcurrentLinkedQueue<Packet> outgoingPackets = new ConcurrentLinkedQueue<Packet>();
	
	private static Decoder decoder = new Decoder();
	
	private static void asyncConnectToServer(){
		if (clientSocket != null && !clientSocket.isConnected())
			throw new RuntimeException("Already Connected!");
		clientSocket = createSocket();
		try {
			clientSocket.setSoTimeout(1400);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (clientSocket == null)
			return;
		createStreams();
		Packet packet = new Packet();
		packet.createConnectionPacket();
		packet.compressPacket();
		packet.finalizePacket();
		sendPacket(packet);
		waitForAuthentication();
		return;
	}
	
	public static void connectServer(){
		//setupConfigurationValue();
		new Thread(new Runnable(){

			@Override
			public void run() {
				Future<?> future = executor.submit(Networking::asyncConnectToServer);
				try {
					future.get(10, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}
	
	

	private static void waitForAuthentication() {
		Packet packet = new Packet(ByteUtils.readStream(inputStream));
		if (decoder.isAuthenticationPacket(packet)){
			connected = true;
			try {
				clientSocket.setSoTimeout(120);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("Invalid authentication packet" + new String(packet.getData()));
	}

	private static void createStreams() {
		try {
			outputStream = (ReliableSocketOutputStream) clientSocket.getOutputStream();
			inputStream = clientSocket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static ReliableSocket createSocket(){
		try {
			return new ReliableSocket(IP, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void sendPacket(Packet packet){
		try {
			outputStream.write(packet.getData());
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isConnected(){
		return connected;
	}
	
	public static void initializePacketListener(){
		incomingPackets = new ConcurrentLinkedQueue<Packet>();
		Thread listenerThread = new Thread(new PacketListener(incomingPackets, inputStream));
		listenerThread.start();
		PacketSender packetSender = new PacketSender(outgoingPackets);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.scheduleAtFixedRate(packetSender, 0, 20, TimeUnit.MILLISECONDS);
	}
	
	public static void initializeUsername(String username){
		try {
			executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.submit(() -> initializeUsernameAsync(username));
		
	}
	
	public static void initializeUsernameAsync(String username){
		if (usernameInit)
			throw new RuntimeException("Username already initialized!");
		while (!isConnected())
			sleep(1);
		Packet packet = new Packet();
		packet.createUsername(username);
		packet.signPacket();
		packet.compressPacket();
		packet.finalizePacket();
		sendPacket(packet);
		usernameInit = true;
	}

	private static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void disconnect() {
		disconnected = true;	
		// TODO: cleanup
	}
	
	public static boolean disconnected() {
		return disconnected;
	}
	
	private static void setupConfigurationValue() {
		//config = new Configuration(client_config);
		config.setData("PORT"," = ");
		port = Integer.parseInt(config.getData());
		config.setData("IP"," = ");
		IP = config.getData();

	}
	
	public static boolean executeSinglePacket(){
		if (incomingPackets != null && incomingPackets.size() > 0){
			Packet packet = incomingPackets.poll();
			executeCommand(packet);
			return true;
		}
		return false;
	}

	private static void executeCommand(Packet packet) {
		//System.out.println(new String(packet.getBody()));
		if (packet.newPlayerPacket()){
			if (MainMenuScreen.getMe() == null){
				System.out.println("My id is " +  packet.getPlayerID() + " nick = " + packet.getPlayerName());
				if (!test){
					Survivor survivor = new Survivor(packet.getPlayerID() - 1, packet.getPlayerID() - 1, packet.getPlayerID(), packet.getPlayerName());
					MainMenuScreen.setMe(survivor);
				}
				
			} else {
				System.out.println("New player packet " + packet.getPlayerName() + " "+ packet.getPlayerID());
				if (!test){
					Survivor survivor = new Survivor(packet.getPlayerID() - 1, packet.getPlayerID() - 1, packet.getPlayerID(), packet.getPlayerName());
					MainMenuScreen.addSurvivor(survivor);
				}				
			}
			
			return;
		}
		if (packet.gameStartingPacket()){
			System.out.println("Game is starting..");
			started = true;
			return;
		}
		
		if (packet.playerMovementPacket()){
			byte id = packet.getPlayerID();
			Survivor survivor = GameScreen.getSurvivor(id);
			String data = packet.getMovCoords();
			String[] coords = data.split(",");
			float x = Float.parseFloat(coords[0]);
			float y = Float.parseFloat(coords[1]);
			if (survivor == null)
				return;
			survivor.setCoordinates(x, y);
			//System.out.println(Calendar.getInstance().getTimeInMillis());
			//System.out.println("Movement packet coming this player moved" + id);
			return;
		}
		if (packet.enemyMovementPacket()){
			Zombie zombie = GameScreen.getZombie(packet.getPlayerID());
			if (zombie == null)
				return;
			String data = packet.getZMovCoords();
			String[] coords = data.split(",");
			float x = Float.parseFloat(coords[0]);
			float y = Float.parseFloat(coords[1]);
			zombie.setCoordinates(x, y);
			return;
		}
		if (packet.playerAttackingPacket()){
			byte id = packet.getPlayerID();
			Survivor survivor = GameScreen.getSurvivor(id);
			String data = packet.getBLCoords();
			String[] coords = data.split(",");
			float x = Float.parseFloat(coords[0]);
			float y = Float.parseFloat(coords[1]);
			Bullet bullet = new Bullet(x, y, survivor.getCurrentWeapon(), survivor);
			Pistol.bullets.add(bullet);			
			return;
		}
		if (packet.isWavePacket()){
			int wave = packet.getWave();
			System.out.println("Wave " + wave);
			CurrentSituation.wave = wave;
			return;
		}
		
		if (packet.enemyAttackingPacket()){
			// show attack
			return;
		}
		if (packet.decreaseLifePacket()){
			Survivor surv = GameScreen.getSurvivor(packet.getPlayerID());
			int health = packet.getHealth();
			surv.setHealth(health);			
			return;
		}
		if (packet.spawningZombiePacket()){
			System.out.println("Spawning zombies");
			Zombie zombie = packet.extractZombie();
			GameScreen.zombies.add(zombie);
			return;
		}
		if (packet.zombieDeathPacket()){
			System.out.println("Zombie died");
			Zombie zombie = GameScreen.getZombie(packet.getPlayerID());
			GameScreen.zombies.remove(zombie);
			return;
		}
		if (packet.highscorePacket()){
			// show highscores
			return;
		}
		
		
		
	}

	public static boolean gameStarted() {
		return started;
	}
	
	public static void addPacketToQueue(Packet packet){
		outgoingPackets.add(packet);
	}

	
	
	

}
