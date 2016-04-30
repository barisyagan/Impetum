package com.impetum.server.Networking;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.impetum.server.User;

public class PacketListener implements Runnable {
	private CopyOnWriteArrayList<User> allUsers;
	private ConcurrentHashMap<User,LinkedList<Packet>> packets;
	
	public PacketListener(CopyOnWriteArrayList<User> allUsers,ConcurrentHashMap<User,LinkedList<Packet>> packets ){
		this.allUsers = allUsers;
		this.packets = packets;
		
	}
	@Override
	public void run() {
		Iterator<User> it = allUsers.iterator();
		while(it.hasNext()){
			User user = it.next();
			Packet packet = user.readPacket();
			if (packet == null){
				//System.out.println("No packet to read");
				continue;
			}
			//System.out.println(new String(packet.getData()));			
			LinkedList<Packet> userPackets = packets.get(user);
			userPackets.add(packet);
		}
		
	}
	

	
}
