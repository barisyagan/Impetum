package com.impetum.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.impetum.server.Networking.Packet;


public class GameController extends Thread {
	private CopyOnWriteArrayList<User> allUsers;
	private ConcurrentHashMap<User,LinkedList<Packet>> packets;

	private LinkedList<User> usersWithoutNick = new LinkedList<User>();
	private LinkedList<Room> gameRooms = new LinkedList<Room>();
	private LinkedList<Room> playingRooms = new LinkedList<Room>();
	Calendar calendar = Calendar.getInstance();
	
	
	public GameController(CopyOnWriteArrayList<User> allUsers, ConcurrentHashMap<User, LinkedList<Packet>> packets) {
		this.allUsers = allUsers;
		this.packets = packets;
	}

	@Override
	public void run(){
		checkUsersWithNoNick();
		checkUsers();
		checkRooms();
	}

	private void checkUsersWithNoNick() {
		Iterator<User> it = usersWithoutNick.iterator();
		while(it.hasNext()){
			User user = it.next();
			if (setNick(user, readUserPacket(user))){
				it.remove();
				System.out.println("New user has this nickname: " + user.getUsername());
				addUserToRoom(user);
				
			}
		}
		
	}

	private boolean setNick(User user, Packet packet) {
		if (packet == null)
			return false;
		String nick = packet.getNick();
		if (nick == null)
			return false;
		user.setUsername(nick);
		return true;		
	}

	private void checkUsers() {
		Iterator<User> it = allUsers.iterator();
		while (it.hasNext()) {
			User user = it.next();
			if (!user.connected()){
				System.out.println("User" + user.getIP() + "disconnected!");
				user.disconnect();
				it.remove();
			}
			//System.out.println(new String(user.readMessage()));
		}
	}
	
	private void checkRooms() {
		Iterator<Room> it = playingRooms.iterator();
		while(it.hasNext()){
			Room room = it.next();
			if (room.gameEnded()){
				room.endRoom();
				it.remove();
			} else {
				room.game.tick();
			}				
		}
	}

	public void addConnection(Socket clientSocket) throws IOException {
		clientSocket.setSoTimeout(Server.TIMEOUT);
		System.out.println("Incoming connection");
		String IP = clientSocket.getInetAddress().toString();
		int port = clientSocket.getPort();
		if (!isConnectionPacket(clientSocket.getInputStream()) /*|| userIsConnected(IP, port)*/){
			System.out.println("Invalid packet");
			return;
		}
		clientSocket.setSoTimeout(10);
	    User newUser = new User(clientSocket);
	    System.out.println("A new user connected! IP : " + IP + " port :" + port);
	    newUser.sendAuthentication();
	    allUsers.add(newUser);
	    packets.put(newUser, new LinkedList<Packet>());
	    usersWithoutNick.add(newUser);
	}

	private void addUserToRoom(User newUser) {
		if(gameRooms.isEmpty()){
	    	Room newRoom = new Room(1);
	    	newRoom.addUser(newUser);
	    	System.out.println("GameRooms arrayList was empty, new one is created.");
	    	gameRooms.add(newRoom);
	    }else{
	    	Room lastRoom = gameRooms.get(gameRooms.size()-1);   
	    	if(lastRoom.availablity == false) {
	    		Room newRoom = new Room(lastRoom.roomId+1);
		    	newRoom.addUser(newUser);
		    	gameRooms.add(newRoom);
		    	System.out.println("LastRoom is not available, new one is created.");
	    	}else{
	    		lastRoom.addUser(newUser);
	    		System.out.println("LastRoom was available, lastUser connected to lastRoom. LastRoom have " + lastRoom.getSize() + " users"  );
	    		if (lastRoom.isRoomFull()){
	    			System.out.println("Room is full and ready");
	    			gameRooms.remove(lastRoom);
	    			lastRoom.setGame(new Game(this, lastRoom));
	    			lastRoom.start();	    			
	    			playingRooms.add(lastRoom);
	    			
	    		}
	    	}
	    }
	    System.out.println("Total awaiting room number is " + gameRooms.size());
	    System.out.println("Total playing room number is " + playingRooms.size());
	}

	private boolean isConnectionPacket(InputStream inputStream) {
		Packet packet = new Packet(inputStream);
		return packet.isConnectionPacket();
	}

	private boolean userIsConnected(String IP, int port) {
		Iterator<User> it = allUsers.iterator();
		while(it.hasNext()){
			if (it.next().getIP().equals(IP) && it.next().getPort() == port)
				return true;
		}
		return false;
	}
	
	Packet readUserPacket(User user){
		LinkedList<Packet> packetsOfUser = packets.get(user);
		if(packetsOfUser.size()>0 ){
			Packet packet = packetsOfUser.get(0);
			packetsOfUser.removeFirst();
			return packet;
		}
		
		return null;
	}
	

}
