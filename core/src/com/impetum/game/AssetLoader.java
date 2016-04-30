package com.impetum.game;

import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {
	
	public static Texture bulletTexture;
	public static Texture[] zombieTextures;
	
	public AssetLoader(){
		
		bulletTexture = new Texture("weapons/bullet2.png");
		zombieTextures = new Texture[8];
		zombieTextures[0] = new Texture("zombie/ZombieNorth.png");
		zombieTextures[1] = new Texture("zombie/ZombieNorthEast.png");
		zombieTextures[2] = new Texture("zombie/ZombieEast.png");
		zombieTextures[3] = new Texture("zombie/ZombieSouthEast.png");
		zombieTextures[4] = new Texture("zombie/ZombieSouth.png");
		zombieTextures[5] = new Texture("zombie/ZombieSouthWest.png");
		zombieTextures[6] = new Texture("zombie/ZombieWest.png");
		zombieTextures[7] = new Texture("zombie/ZombieNorthWest.png");
	}
}
