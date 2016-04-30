package com.impetum.game.Networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.impetum.game.Utilities.ByteUtils;
import com.impetum.game.Networking.Packet;

public class PacketListener implements Runnable{
	
	private ConcurrentLinkedQueue<Packet> incomingPackets;
	private InputStream inputStream;
	
	private byte[] buffer = new byte[1024];

	public PacketListener(ConcurrentLinkedQueue<Packet> incomingPackets, InputStream inputStream) {
		this.incomingPackets = incomingPackets;
		this.inputStream = inputStream;
	}

	@Override
	public void run() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while(true){
			
			try {
				//int singleData = inputStream.read();
				//bos.write(singleData);
				inputStream.read(buffer);
				bos.write(buffer);
			} catch (IOException e) {
				if (e instanceof SocketException){
					System.out.println("Disconnected from server");
					Networking.disconnect();
					break;
				}
			}
			 
			
			
			
			if (bos.size() >= ByteCode.SIGN.length + ByteCode.EOP.length){
				byte[] data = bos.toByteArray();
				data = ByteUtils.extractPacket(data);
				if (data == null)
					continue;
				
				//if (packetIsReceived(data)){
					Packet packet = new Packet(data);
					incomingPackets.add(packet);
					//System.out.println(new String(packet.getData()));
					bos.reset();
					if (Networking.gameStarted())
						Networking.executeSinglePacket();
				//}
			}
			sleep(1);
		
		}		
	}

	private void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean packetIsReceived(byte[] data) {
		if (!ByteUtils.compareBytes(ByteUtils.subByte(data, 0, ByteCode.SIGN.length), ByteCode.SIGN))
			return false;
		if (!ByteUtils.compareBytes(ByteUtils.subByte(data, data.length - ByteCode.EOP.length, data.length), ByteCode.EOP))
			return false;
		return true;
	}

}
