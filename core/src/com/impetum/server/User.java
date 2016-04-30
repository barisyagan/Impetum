package com.impetum.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

import com.impetum.server.Networking.Packet;
import com.impetum.server.Utilities.ByteUtils;

public class User {
	private Socket socket;
	private String IP;
	private int port;
	private String username;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Room room;
	private byte[] buffer = new byte[2048];
	private byte id;
	private Player player = new Player();
	

	public User(Socket clientSocket) throws IOException {
		this.socket = clientSocket;
		this.IP = clientSocket.getInetAddress().toString();
		this.port = clientSocket.getPort();
		this.inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
	}

	public void setIP(String IP){
		this.IP = IP;
	}
	
	public void setID(byte id){
		this.id = id;
	}
	
	public String getIP(){
		return IP;
	}
	
	public void setRoom(Room room){
		this.room = room;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		if (username == null){
			username = "UsernameNotDefined";
		}
		return username;
	}
	
	public int getPort(){
		return port;
	}
	
	public byte[] readMessage(){
		try {
			Arrays.fill(buffer, (byte)0);
			inputStream.read(buffer);
			byte[] data = ByteUtils.extractPacket(buffer);
			Packet packet = new Packet(data);
			return packet.getBody();
		} catch (IOException e) {}
		return null;
	}
	
	public Packet readPacket(){
		try {
			Arrays.fill(buffer, (byte)0);
			inputStream.read(buffer);
			byte[] data = ByteUtils.extractPacket(buffer);
			if (data == null)
				return null;
			Packet packet = new Packet(data);
			return packet;
		} catch (IOException e) {}
		return null;
	}
	
	public void sendData(String data){
		PrintWriter outputBuffer = new PrintWriter(outputStream);
		outputBuffer.println(data);
		outputBuffer.flush();		
	}
	
	public void sendData(byte[] data){
		try {
			outputStream.write(data);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		
	}

	public void sendAuthentication() {
		Packet packet = new Packet();
		packet.setAuthentication();
		packet.compressPacket();
		packet.finalizePacket();
		sendData(packet.getData());
	}
	
	public boolean connected(){
		return socket.isConnected();
	}

	public void disconnect() {
		room.disconnected(this);		
	}

	public byte getID() {
		return id;
	}

	public void move(String coords) {
		player.setX(Float.parseFloat (coords.substring(0, coords.indexOf(",") )) );
		player.setY(Float.parseFloat (coords.substring(coords.indexOf(",")+1,coords.length()  )) );		
	}
	
	public float getX(){
		return player.getX();
	}
	
	public float getY(){
		return player.getY();
	}
	
	public boolean isAlive(){
		return player.isAlive();
	}
	
	public void reduceHealth(int health){
		player.reduceHealth(health);
	}

	public byte[] getHealth() {
		byte[] data = new byte[0];
		data[0] = player.getHealth();
		return data;
	}
	
	public boolean isConnected(){
		return socket.isConnected();
	}

}
