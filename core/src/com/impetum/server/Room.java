package com.impetum.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.impetum.server.Networking.Packet;

public class Room {
	ArrayList<User> userList=new ArrayList<User>();
	Game game;
	boolean availablity = true;
	int roomId;
	public Room(int roomId){
		this.roomId = roomId;
	}
	public void addUser(User newUser) {
		newUser.setRoom(this);
		userList.add(newUser);
		newUser.setID((byte) userList.size());
		informOldUsers(newUser);
		informNewUser(newUser);
		this.checkRoomSize();
	}
	
	public boolean isRoomFull(){
		return !availablity;
	}
	
	public void setGame(Game game){
		this.game = game;
	}

	private void checkRoomSize() {
		// 2 Players for Debugging
		if(userList.size() == 2){
			this.availablity = false;
		}
		
	}
	
	public int getSize(){
		return userList.size();
	}
	
	private void informOldUsers(User newUser) {
		//if (userList.size() == 1)
		//	return;
		System.out.println("Informing size" + userList.size());
		Packet packet = new Packet();
		packet.addUserData(newUser);
		packet.signPacket();
		packet.compressPacket();
		packet.finalizePacket();
		System.out.println("Prepared packet");
		for (int i=0; i < userList.size(); i++){
			userList.get(i).sendData(packet.getData());
			System.out.println(new String(packet.getData()));
		}
		
	}
	
	private void informNewUser(User newUser) {
		for (int i=0; i < userList.size() - 1; i++){
			Packet packet = new Packet();
			packet.addUserData(userList.get(i));
			packet.signPacket();
			packet.compressPacket();
			packet.finalizePacket();
			newUser.sendData(packet.getData());
		}
		
	}
	public void disconnected(User disconnectedUser) {
		Packet packet = new Packet();
		packet.createDisconnection(disconnectedUser.getUsername());
		packet.signPacket();
		packet.compressPacket();
		packet.finalizePacket();
		Iterator<User> it = userList.iterator();
		while(it.hasNext()){
			User user = it.next();
			if (user.equals(disconnectedUser)){
				it.remove();
				continue;
			}
			user.sendData(packet.getData());
			System.out.println("Informing other user data = " + new String(packet.getData()));
		}
		
	}
	public boolean gameEnded() {
		if (game == null)
			return false;
		return game.isOver();
	}
	public void endRoom() {
		// TODO Auto-generated method stub
		
	}
	public void start() {
		System.out.println("Game should be started.");
		Packet packet = new Packet();
		packet.createStartPacket();
		packet.signPacket();
		packet.compressPacket();
		packet.finalizePacket();
		Iterator<User> it = userList.iterator();
		while(it.hasNext()){
			User user = it.next();
			user.sendData(packet.getData());
		}
	}
	public Collection<? extends User> getUsers() {
		return userList;
	}
}
