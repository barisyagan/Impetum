package com.impetum.server;

import java.util.ArrayList;
import java.util.Collections;



public class Astar {
	private ArrayList closed = new ArrayList();
	private SortedList open = new SortedList();
	private Map map;
	private int maxSearchDistance;
	private Node[][] nodes;
	private Heuristic heuristic;
	private boolean allowDiagMovement;
	
	public Astar(Map map, int maxSearchDistance, boolean allowDiagMovement) {
		this(map, maxSearchDistance, allowDiagMovement, new Heuristic());
	}
	public Astar(Map map, int maxSearchDistance, boolean allowDiagMovement, Heuristic heuristic) {
		
		this.heuristic = heuristic;
		this.map = map;
		this.maxSearchDistance = 99;
		this.allowDiagMovement = allowDiagMovement;

		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		
		for (int x=0;x<map.getWidthInTiles();x++) {
			for (int y=0;y<map.getHeightInTiles();y++) {
				nodes[x][y] = new Node(x,y);
			}
		}
	}
	
	public Path findPath(int sx, int sy, int tx, int ty) {
		if (map.blocked(tx, ty)) {
			return null;
		}

		nodes[sx][sy].cost = 0;
		nodes[sx][sy].depth = 0;
		closed.clear();
		open.clear();
		open.add(nodes[sx][sy]);
		
		nodes[tx][ty].parent = null;
		
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {

			Node current = getFirstInOpen();
			if (current == nodes[tx][ty]) {
				break;
			}
			
			removeFromOpen(current);
			addToClosed(current);
			

			for (int x=-1;x<2;x++) {
				for (int y=-1;y<2;y++) {

					if ((x == 0) && (y == 0)) {
						continue;
					}
					int xp = x + current.x;
					int yp = y + current.y;
					
					if (isValidLocation(sx,sy,xp,yp)) {
					

						float nextStepCost = current.cost + getMovementCost(current.x, current.y, xp, yp);
						Node neighbour = nodes[xp][yp];
						map.pathFinderVisited(xp, yp);
						
						if (nextStepCost < neighbour.cost) {
							if (inOpenList(neighbour)) {
								removeFromOpen(neighbour);
							}
							if (inClosedList(neighbour)) {
								removeFromClosed(neighbour);
							}
						}
						
						if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
							neighbour.cost = nextStepCost;
							neighbour.heuristic = getHeuristicCost(xp, yp, tx, ty);
							maxDepth = Math.max(maxDepth, neighbour.setParent(current));
							addToOpen(neighbour);
						}
					}
				}
			}
		}

		if (nodes[tx][ty].parent == null) {
			return null;
		}
		
		
		Path path = new Path();
		Node target = nodes[tx][ty];
		while (target != nodes[sx][sy]) {
			path.prependStep(target.x, target.y);
			target = target.parent;
		}
		path.prependStep(sx,sy);
		

		return path;
	}
	
	
	
	protected Node getFirstInOpen() {
		return (Node) open.first();
	}
	protected void addToOpen(Node node) {
		open.add(node);
	}
	protected boolean inOpenList(Node node) {
		return open.contains(node);
	}
	protected void removeFromOpen(Node node) {
		open.remove(node);
	}
	protected void addToClosed(Node node) {
		closed.add(node);
	}
	protected boolean inClosedList(Node node) {
		return closed.contains(node);
	}
	protected void removeFromClosed(Node node) {
		closed.remove(node);
	}
	
	protected boolean isValidLocation(int sx, int sy, int x, int y) {
		boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());
		
		if ((!invalid) && ((sx != x) || (sy != y))) {
			invalid = map.blocked( x, y);
		}
		
		return !invalid;
	}
	
	
	public float getMovementCost(int sx, int sy, int tx, int ty) {
		return map.getCost(sx, sy, tx, ty);
	}
	
	public float getHeuristicCost(int x, int y, int tx, int ty) {
		return heuristic.getCost(map,x, y, tx, ty);
	}

	
	

	private class SortedList {
		private ArrayList list = new ArrayList();

		public Object first() {
			return list.get(0);
		}

		public void clear() {
			list.clear();
		}
		
		public void add(Object o) {
			list.add(o);
			Collections.sort(list);
		}
		

		public void remove(Object o) {
			list.remove(o);
		}
	
		public int size() {
			return list.size();
		}
		
		public boolean contains(Object o) {
			return list.contains(o);
		}
	}
	private class Node implements Comparable {
		private int x;
		private int y;
		private float cost;
		private Node parent;
		private float heuristic;
		private int depth;
	
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			
			return depth;
		}
		public int compareTo(Object other) {
			Node o = (Node) other;
			
			float f = heuristic + cost;
			float of = o.heuristic + o.cost;
			
			if (f < of) {
				return -1;
			} else if (f > of) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
}
