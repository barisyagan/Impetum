package com.impetum.server;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.rudp.ReliableServerSocket;
import net.rudp.ReliableSocketOutputStream;

import com.badlogic.gdx.Gdx;
import com.impetum.server.Networking.Packet;
import com.impetum.server.Networking.PacketListener;
import com.impetum.server.Utilities.Configuration;
import com.impetum.server.Utilities.ShutdownHook;

public class Server {
	//private final static File server_config = new File("../core/assets/sconfig.txt");
	private Configuration config;
	
	
	// TODO: Read these value from config file
	public static int TIMEOUT = 500;
	public static int port = 4400;
	
	
	public void start() {
		addShutDownHook();
		System.out.println("Server is starting....");
		//setupConfigurationValue();
		if (onRemoteMachine())
			startRemoteServer();
		else
			startLocalServer();		
	}
	
	

	private boolean onRemoteMachine() {
		return System.getenv("Server_IP") != null;
	}
	
	private void startRemoteServer() {
		System.out.println("Starting remote Server");
		openConnection();
	}
	
	private void startLocalServer() {
		System.out.println("Starting local Server");
		openConnection();
	}
	
	private void openConnection() {
		CopyOnWriteArrayList<User> allUsers = new CopyOnWriteArrayList<User>();
		ConcurrentHashMap<User,LinkedList<Packet>> packets = new ConcurrentHashMap<User,LinkedList<Packet>>() ;
		GameController gameController = new GameController(allUsers, packets);
		PacketListener packetListener = new PacketListener(allUsers, packets);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.scheduleAtFixedRate(gameController, 0, 1, TimeUnit.MILLISECONDS);
		executor.scheduleAtFixedRate(packetListener, 0, 1, TimeUnit.MILLISECONDS);
		ReliableServerSocket serverSocket = null;
		try {
			serverSocket = new ReliableServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true){
			acceptConnections(gameController, serverSocket);
		}
		
		
	}

	private void acceptConnections(GameController gameController,
			ReliableServerSocket serverSocket) {
		try {
			gameController.addConnection(serverSocket.accept());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new ShutdownHook(config));		
	}
	
	private void setupConfigurationValue() {
		// TODO: Configuration setup	
		//config = new Configuration(server_config);
		config.setData("TIMEOUT"," = ");
		TIMEOUT = Integer.parseInt(config.getData());
		config.setData("PORT"," = ");
		port = Integer.parseInt(config.getData());
		
		
		 
		
	}

}
