package com.impetum.server.Networking;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.impetum.server.Networking.ByteCode;
import com.impetum.server.User;
import com.impetum.server.Zombie;
import com.impetum.server.Utilities.ByteUtils;

public class Packet {
	private byte[] data;
	private byte[] body;
	private boolean isValid = true;
	private static Compressor compressor = new Compressor();
	private static Decompressor decompressor = new Decompressor();
	private static Encoder encoder = new Encoder();
	private static Decoder decoder = new Decoder();
	
	public Packet(){
		
	}
	
	public Packet(InputStream inputStream) {
		data = ByteUtils.readStream(inputStream);
		if (!decoder.isSigned(this)){
			System.out.println("Packet not signed! Input " + new String(data));
			return;
		}
		byte[] content = ByteUtils.removeSignAndEOP(data);
		data = ByteUtils.connectBytes(ByteCode.SIGN, decompressor.decompress(content));
	}

	
	public Packet(byte[] data) {
		this.data = data;
		if (!decoder.isSigned(this)){
			System.out.println("Packet not signed! Input " + new String(data));
			return;
		}
		byte[] content = ByteUtils.removeSignAndEOP(data);
		this.body = decompressor.decompress(content);
		this.data = ByteUtils.connectBytes(ByteCode.SIGN, this.body);
		this.data = ByteUtils.connectBytes(this.data, ByteCode.EOP);
	}

	public byte[] getData(){
		return data;
	}
	
	public byte[] getBody(){
		return body;
	}
	
	public void setData(byte[] data){
		this.data = data;
	}
	
	public void addUserData(User... users){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		System.out.println("ADding user data");
		for (int i=0; i < users.length; i++){
			encoder.addUser(bos, users[i]);
		}
		System.out.println("added");
		data = bos.toByteArray();
	}
	
	public void compressPacket(){
		byte[] content = ByteUtils.removeSign(data);
		data = ByteUtils.connectBytes(ByteCode.SIGN, compressor.compress(content));
	}
	
	public void signPacket(){
		encoder.signPacket(this);
	}

	public boolean isConnectionPacket() {
		return decoder.isConnectionPacket(this);
	}
	
	public void createConnectionPacket(){
		encoder.createConnectionPacket(this);
	}

	public void setAuthentication() {
		encoder.createAuthenticationPacket(this);		
	}
	
	public void finalizePacket(){
		encoder.finalizePacket(this);
	}

	public void createDisconnection(String username) {
		encoder.addDisconnected(this, username);		
	}

	public String getNick() {
		if (encoder.hasNick(this))
			return encoder.extractNick(this);
		return null;
	}
	
	public void createStartPacket(){
		encoder.createStart(this);
	}

	public boolean playerMovementPacket() {
		return decoder.isMovementPacket(this);
	}

	public void createMovementPacket(byte[] oldData, User user) {
		byte[] no = new byte[2];
		no[0] = user.getID();
		no[1] = ByteCode.SEPARATOR[0];
		this.data = ByteUtils.connectBytes(ByteCode.ID, no);
		this.data = ByteUtils.connectBytes(data, oldData);
	}

	public String getCoords() {
		return decoder.extractCoords(this);
	}
	
	public boolean playerAttackingPacket() {
		return decoder.isPlayerAttackingPacket(this);
	}

	public void createBulletPacket(byte[] oldData, User user) {
		byte[] no = new byte[2];
		no[0] = user.getID();
		no[1] = ByteCode.SEPARATOR[0];
		this.data = ByteUtils.connectBytes(ByteCode.ID, no);
		this.data = ByteUtils.connectBytes(data, oldData);
	}

	public String getBLCoords() {
		return decoder.extractCoords(this, ByteCode.BULLET);
	}

	public void incrementWave(int wave) {
		encoder.encodeWave(this, wave);		
	}
	
	public void preparePacket(){
		signPacket();
		compressPacket();
		finalizePacket();
	}

	public void createZombie(Zombie zombie) {
		encoder.encodeZombies(this, zombie);		
	}
	
	public void zombieMovement(Zombie zombie){
		encoder.encodeZombieMovement(this, zombie);
	}
	
	public void decreasePlayerHealth(User user){
		encoder.encoderDecreasePlayerHealth(this, user);
	}

	public void setZombieDeath(Zombie zombie) {
		encoder.encodeZombieDeath(this, zombie.getId());		
	}

}
