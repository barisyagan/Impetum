package com.impetum.game.Networking;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketSender implements Runnable{
	private ConcurrentLinkedQueue<Packet> outgoingPackets;

	public PacketSender(ConcurrentLinkedQueue<Packet> outgoingPackets) {
		this.outgoingPackets = outgoingPackets;
	}

	@Override
	public void run() {
		Iterator<Packet> it = outgoingPackets.iterator();
		while(it.hasNext()){
			Packet packet = it.next();
			//if (!it.hasNext()){
				Networking.sendPacket(packet);
				
			//}
		}
		// FOR DEBUGGING
		outgoingPackets.clear();
		
	}

}
