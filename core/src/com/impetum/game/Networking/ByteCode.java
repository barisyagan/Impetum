package com.impetum.game.Networking;

import com.impetum.game.Utilities.ByteUtils;

public class ByteCode {
	
	public static final byte[] SIGN       = {0x49, 0x4d, 0x50}; 					    // IMP
	public static final int DATA_OFFSET   = SIGN.length;
	public static final byte[] NICK       = {0x4e, 0x49, 0x43, 0x4b, 0x3d}; 	  	    // NICK=
    public static final byte[] SEPARATOR  = {0x7f};
    public static final byte[] ROOM		  = {0x52, 0x4f, 0x4f, 0x4d, 0x3d};		        // ROOM=
    public static final byte[] CONNECT    = {0x43, 0x4f, 0x4e, 0x4e, 0x45, 0x43, 0x54}; // CONNECT 
    public static final byte[] CONNECTION = ByteUtils.connectBytes(SIGN, CONNECT);
	public static final byte[] AUTH       = {0x41, 0x55, 0x54, 0x48};					// AUTH
	public static final byte[] END_OF_PACKET = {0x45, 0x4f, 0x50};						// EOP
	public static final byte[] EOP 		  = ByteUtils.connectBytes(SEPARATOR, END_OF_PACKET);
	public static final byte[] AUTHENTICATION = ByteUtils.connectBytes(ByteUtils.connectBytes(SIGN, AUTH), EOP);
	public static final byte[] DISCONNECTED = {0x44, 0x43, 0x3d};						// DC=
	public static final byte[] MOV		  =  {0x4d, 0x4f, 0x56, 0x3d};					// MOV=
	public static final byte[] ZMOV		  =  {0x5a, 0x4f, 0x56, 0x3d};			// ZMOV=
	public static final byte[] GAME_START = {0x47, 0x41, 0x4d, 0x45, 0x53, 0x54, 0x41, 0x52, 0x54}; // GAMESTART
	public static final byte[] NEW_USER   = {0x4e, 0x55, 0x3d};							// NU=
	public static final byte[] ID         = {0x49, 0x44, 0x3d};							// ID=
	public static final byte[] COMMA      = {0x2c};
	public static final byte[] BULLET     = {0x42, 0x4c, 0x3d};							// BL=
	public static final byte[] INCREMENT_WAVE = {0x49, 0x57, 0x3d};  					// IW=
	public static final byte[] ZOMBIE_SPAWN = {0x5a, 0x53, 0x3d};		
	public static final byte[] DECREASE_HEALTH = {0x44, 0x48, 0x3d};					// DH=
	public static final byte[] ZOMBIE_DEATH = {0x5a, 0x44};						// ZD=
}
