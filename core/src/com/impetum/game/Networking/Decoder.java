package com.impetum.game.Networking;

import java.util.Arrays;

import com.impetum.game.Networking.Packet;
import com.impetum.game.Utilities.ByteUtils;

public class Decoder {
	
	public Packet decode(byte[] data){
		return new Packet();
	}
	
	public boolean isSigned(Packet packet){
		byte[] data = packet.getData();
		if (data.length < ByteCode.DATA_OFFSET)
			return false;
		for (int i=0; i < ByteCode.DATA_OFFSET; i++)
			if (data[i] != ByteCode.SIGN[i])
				return false;
		return true;
	}

	public boolean isConnectionPacket(Packet packet) {
		byte[] data = packet.getData();
		return ByteUtils.compareBytes(data, ByteCode.CONNECTION);
	}

	public boolean isAuthenticationPacket(Packet packet) {
		byte[] data = packet.getData();
		return ByteUtils.compareBytes(data, ByteCode.AUTHENTICATION);
	}

	public boolean isPlayerMovementPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.MOV) != -1);
	}

	public boolean isEnemyMovenmentPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.ZMOV) != -1);
	}

	public boolean isPlayerAttackingPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.BULLET) != -1);
	}

	public boolean isEnemyAttackingPacket(Packet packet) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDecreaseLifePacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.DECREASE_HEALTH) != -1);
	}

	public boolean isSpawningPacket() {
		// TODO Auto-generated method stub
		return false;
	}

	public String extractPlayerName(Packet packet) {
		byte[] data = ByteUtils.extractData(packet.getBody(), ByteCode.NICK);
		data = ByteUtils.subByte(data, ByteCode.NICK.length, data.length);
		return new String(data);
	}

	public byte extractPlayerId(Packet packet) {
		byte[] data = ByteUtils.extractData(packet.getBody(), ByteCode.ID);
		data = ByteUtils.subByte(data, ByteCode.ID.length, data.length);
		return Byte.parseByte(Arrays.toString(data).substring(1, Arrays.toString(data).length() - 1));
	}

	public boolean isStartingPacket(Packet packet) {
		byte[] data = packet.getBody();
		return ByteUtils.compareBytes(data, ByteCode.GAME_START);
	}
	
	public String extractCoords(Packet packet, byte[] extracted) {
		byte[] data = ByteUtils.extractData(packet.getBody(), extracted);
		data = ByteUtils.subByte(data, extracted.length, data.length);		
		return new String(data);
	}

	public boolean isWavePacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.INCREMENT_WAVE) != -1);
	}

	public int extractWave(Packet packet) {
		byte[] data = ByteUtils.extractData(packet.getBody(), ByteCode.INCREMENT_WAVE);
		data = ByteUtils.subByte(data, ByteCode.INCREMENT_WAVE.length, data.length);
		return data[0];
	}

	public boolean isSpawningZombiePacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.ZOMBIE_SPAWN) != -1);
	}

	public boolean isZombieDeathPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.ZOMBIE_DEATH) != -1);
	}

}
