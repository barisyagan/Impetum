package com.impetum.game.Networking;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

import com.impetum.server.User;
import com.impetum.game.Entities.Bullet;
import com.impetum.game.Entities.Zombie;
import com.impetum.game.Networking.ByteCode;
import com.impetum.game.Utilities.ByteUtils;

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
		byte[] content = ByteUtils.removeSign(data);
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
		for (int i=0; i < users.length; i++){
			encoder.addUser(bos, users[i]);
		}
		data = bos.toByteArray();
	}
	
	public void compressPacket(){
		byte[] content = ByteUtils.removeSign(data);
		data = ByteUtils.connectBytes(ByteCode.SIGN, compressor.compress(content));
	}
	
	public void signPacket(){
		encoder.signPacket(this);
	}

	public void createConnectionPacket(){
		encoder.createConnectionPacket(this);
	}
	
	public void finalizePacket(){
		encoder.finalizePacket(this);
	}
	
	public void createUsername(String username){
		encoder.createUsername(this, username);
	}

	public boolean playerMovementPacket() {
		return decoder.isPlayerMovementPacket(this);
	}

	public boolean enemyMovementPacket() {
		return decoder.isEnemyMovenmentPacket(this);
	}

	public boolean playerAttackingPacket() {
		return decoder.isPlayerAttackingPacket(this);
	}

	public boolean enemyAttackingPacket() {
		return decoder.isEnemyAttackingPacket(this);
	}

	public boolean decreaseLifePacket() {
		return decoder.isDecreaseLifePacket(this);
	}

	public boolean spawningPacket() {
		return decoder.isSpawningPacket();
	}

	public boolean highscorePacket() {
		return false;
	}

	public boolean newPlayerPacket() {
		return encoder.isNewPlayerPacket(this);
	}

	public String getPlayerName() {
		return decoder.extractPlayerName(this);
	}

	public byte getPlayerID() {
		return decoder.extractPlayerId(this);
	}

	public boolean gameStartingPacket() {
		return decoder.isStartingPacket(this);
	}

	public void createMovementPacket(Float x, Float y) {
		encoder.createMovement(this ,x ,y);
	}
	
	public String getMovCoords() {
		return decoder.extractCoords(this, ByteCode.MOV);
	}
	
	public String getBLCoords() {
		return decoder.extractCoords(this, ByteCode.BULLET);
	}

	public void prepareBullet(Bullet bullet) {
		encoder.encodeBullet(this, bullet);		
	}

	public boolean isWavePacket() {
		return decoder.isWavePacket(this);
	}

	public int getWave() {
		return decoder.extractWave(this);
	}

	public boolean spawningZombiePacket() {
		return decoder.isSpawningZombiePacket(this);
	}

	public Zombie extractZombie() {
		String[] coords = decoder.extractCoords(this, ByteCode.ZOMBIE_SPAWN).split(",");
		int id = decoder.extractPlayerId(this);
		return new Zombie(Float.parseFloat(coords[0]), Float.parseFloat(coords[1]), id, true);
	}

	public int getHealth() {
		String health = decoder.extractCoords(this, ByteCode.DECREASE_HEALTH);
		return Byte.parseByte(health);
	}

	public boolean zombieDeathPacket() {
		return decoder.isZombieDeathPacket(this);
	}

	public String getZMovCoords() {
		return decoder.extractCoords(this, ByteCode.ZMOV);
	}


}
