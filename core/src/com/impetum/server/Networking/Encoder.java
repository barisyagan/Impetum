package com.impetum.server.Networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.impetum.server.User;
import com.impetum.server.Zombie;
import com.impetum.server.Utilities.ByteUtils;

public class Encoder {
	
	public byte[] encode(Packet packet){
		return null;
	}

	public void addUser(ByteArrayOutputStream bos, User user) {		
		try {
			bos.write(ByteCode.NICK);
			bos.write(user.getUsername().getBytes());
			bos.write(ByteCode.SEPARATOR);
			bos.write(ByteCode.ID);
			bos.write(user.getID());			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void signPacket(Packet packet){
		packet.setData(ByteUtils.signData(packet.getData()));
	}
	
	public void createConnectionPacket(Packet packet){
		packet.setData(ByteCode.CONNECTION);
	}

	public void createAuthenticationPacket(Packet packet) {
		packet.setData(ByteCode.AUTHENTICATION);		
	}

	public void finalizePacket(Packet packet) {
		packet.setData(ByteUtils.endPacket(packet.getData()));		
	}

	public void addDisconnected(Packet packet, String username) {
		byte[] user = username.getBytes();
		packet.setData(ByteUtils.connectBytes(ByteCode.DISCONNECTED, user));		
	}

	public boolean hasNick(Packet packet) {
		byte[] body = packet.getBody();
		return ByteUtils.compareBytes(ByteUtils.subByte(body, 0, ByteCode.NICK.length), ByteCode.NICK);
	}

	public String extractNick(Packet packet) {
		byte[] nick = ByteUtils.subByte(packet.getBody(), ByteCode.NICK.length, packet.getBody().length);		
		return new String(nick);
	}

	public void createStart(Packet packet) {
		packet.setData(ByteCode.GAME_START);
		
	}

	public void encodeWave(Packet packet, int wave) {
		byte[] number = new byte[1];
		number[0] = (byte) wave;
		byte[] data = ByteUtils.connectBytes(ByteCode.INCREMENT_WAVE, number);
		packet.setData(data);		
	}

	public void encodeZombies(Packet packet, Zombie zombie) {
		byte[] no = new byte[2];
		no[0] = (byte) zombie.getId();
		no[1] = ByteCode.SEPARATOR[0];
		byte[] data = ByteUtils.connectBytes(ByteCode.ID, no);
		data = ByteUtils.connectBytes(data, ByteCode.ZOMBIE_SPAWN);
		data = ByteUtils.connectBytes(data, (new String(""+zombie.getX()).getBytes()));
		data = ByteUtils.connectBytes(data, ByteCode.COMMA);
		data = ByteUtils.connectBytes(data, (new String(""+zombie.getY()).getBytes()));
		packet.setData(data);
	}

	public void encodeZombieMovement(Packet packet, Zombie zombie) {
		byte[] no = new byte[2];
		no[0] = (byte) zombie.getId();
		no[1] = ByteCode.SEPARATOR[0];
		byte[] data = ByteUtils.connectBytes(ByteCode.ID, no);
		data = ByteUtils.connectBytes(data, ByteCode.ZMOV);
		data = ByteUtils.connectBytes(data, (new String(""+zombie.getX()).getBytes()));
		data = ByteUtils.connectBytes(data, ByteCode.COMMA);
		data = ByteUtils.connectBytes(data, (new String(""+zombie.getY()).getBytes()));
		packet.setData(data);
		
	}

	public void encoderDecreasePlayerHealth(Packet packet, User user) {
		byte[] no = new byte[2];
		no[0] = (byte) user.getID();
		no[1] = ByteCode.SEPARATOR[0];
		byte[] data = ByteUtils.connectBytes(ByteCode.ID, no);
		data = ByteUtils.connectBytes(data, ByteCode.DECREASE_HEALTH);
		data = ByteUtils.connectBytes(data, user.getHealth());
		packet.setData(data);		
	}

	public void encodeZombieDeath(Packet packet, byte id) {
		byte[] no = new byte[2];
		no[0] = id;
		no[1] = ByteCode.SEPARATOR[0];
		byte[] data = ByteUtils.connectBytes(ByteCode.ID, no);
		data = ByteUtils.connectBytes(data, ByteCode.ZOMBIE_DEATH);
		packet.setData(data);		
	}

}
