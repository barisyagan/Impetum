package com.impetum.server;

import java.util.Iterator;
import java.util.LinkedList;

public class Map {
	public static final float WIDTH = Screens.GameScreen.backgroundLimitWidth;
	public static final float HEIGHT = Screens.GameScreen.backgroundLimitHeight;
	private LinkedList<Zombie> zombies = new LinkedList<Zombie>();
	
	public static final float EMPTY = 0;
	public static final float PLAYER = 1;
	public static final float ZOMBIE = 2;
	public static final float OBSTACLE = 3;
	
	private int[][] terrain = new int[(int) WIDTH][(int) HEIGHT];
	private boolean[][] visited = new boolean[(int) WIDTH][(int) HEIGHT];
	
	
	
	public Map(LinkedList<Zombie> zombies) {
		this.zombies = zombies;
		
	}
	
	
	

	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}
	
	public int getWidthInTiles(){
		return (int) WIDTH;
	}
	
	public int getHeightInTiles(){
		return (int) HEIGHT;
		
	}

	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}
	
	public int getTerrain(int x, int y) {
		return terrain[x][y];
	}
	
	public boolean blocked(int tx, int ty) {
		int temp=terrain[tx][ty];
		if(temp==0){
			return false;
		}
		return true;
	}

	public float getCost(int sx, int sy, int tx, int ty) {
		//TODO
		return 1;
	}

	
}
