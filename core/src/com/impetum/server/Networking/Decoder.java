package com.impetum.server.Networking;

import java.util.Arrays;

import com.impetum.server.Networking.ByteCode;
import com.impetum.server.Networking.Packet;
import com.impetum.server.Utilities.ByteUtils;

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

	public boolean isMovementPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.MOV) != -1);
	}

	public String extractCoords(Packet packet) {
		byte[] data = ByteUtils.extractData(packet.getBody(), ByteCode.MOV);
		data = ByteUtils.subByte(data, ByteCode.MOV.length, data.length);		
		return new String(data);
	}

	public boolean isPlayerAttackingPacket(Packet packet) {
		return (ByteUtils.indexOfBytes(packet.getBody(), ByteCode.BULLET) != -1);
	}

	public String extractCoords(Packet packet, byte[] extracted) {
		byte[] data = ByteUtils.extractData(packet.getBody(), extracted);
		data = ByteUtils.subByte(data, extracted.length, data.length);		
		return new String(data);
	}

}
