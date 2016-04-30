package com.impetum.game.Networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.impetum.server.User;
import com.impetum.game.Entities.Bullet;
import com.impetum.game.Networking.Packet;
import com.impetum.game.Utilities.ByteUtils;

public class Encoder {
	
	public byte[] encode(Packet packet){
		return null;
	}

	public void addUser(ByteArrayOutputStream bos, User user) {		
		try {
			bos.write(ByteCode.NICK);
			bos.write(user.getUsername().getBytes());
			bos.write(ByteCode.SEPARATOR);
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
	
	public void finalizePacket(Packet packet) {
		packet.setData(ByteUtils.endPacket(packet.getData()));		
	}

	public void createUsername(Packet packet, String username) {
		packet.setData(ByteUtils.connectBytes(ByteCode.NICK, username.getBytes()));		
	}

	public boolean isNewPlayerPacket(Packet packet) {
		if (packet.getBody().length < ByteCode.NICK.length + ByteCode.ID.length)
			return false;
		if (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.ID) > 0)
			return true;
		return false;
	}

	public void createMovement(Packet packet, float x, float y) {
		byte[] data = ByteUtils.connectBytes(ByteCode.MOV, (new String(""+x).getBytes()));
		data = ByteUtils.connectBytes(data, ByteCode.COMMA);
		data = ByteUtils.connectBytes(data, (new String(""+y).getBytes()));
		packet.setData(data);
	}

	public void encodeBullet(Packet packet, Bullet bullet) {
		float x = bullet.getSpeedX();
		float y = bullet.getSpeedY();
		byte[] data = ByteUtils.connectBytes(ByteCode.BULLET, (new String(""+x).getBytes()));
		data = ByteUtils.connectBytes(data, ByteCode.COMMA);
		data = ByteUtils.connectBytes(data, (new String(""+y).getBytes()));
		packet.setData(data);
	}

}
