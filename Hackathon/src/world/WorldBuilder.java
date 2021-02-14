package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tools.Point;

/**
 * USES THIS ALGORITHM TO GENERATE THE WORLD
 * https://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
 *
 */

public class WorldBuilder {
	private Tile[][] tiles;
	private int width;
	private int height;
	
	private ArrayList<Point> set;


	/**
	 * Constructor for the world builder
	 * @param width: 	Width of the world
	 * @param height: 	Height of the world
	 * */
	public WorldBuilder(int width, int height) {
		tiles = new Tile[width][height];
		this.width = width;
		this.height = height;
		this.set = new ArrayList<>();
	}
	
	/**
	 * Generate rooms and hallways
	 * @param trials:	The number of attempts at placing a room
	 */
	public Tile[][] generate(int trials) {
		// Initially set all tiles to be walls, then carve it out
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tiles[x][y] = Tile.WALL0;
		
		//Place rooms everywhere
		makeRooms(trials);
		
		//Construct hallways
		buildHallways();
		
		//Change the art of all the tiles to make it look more interesting
		finalizeWorld();
		
		return tiles;
	}

	/**
	 * A method that tries to place a bunch of rooms
	 * @param trials: The number of attempts at placing a room the algorithm makes
	 */
	public void makeRooms(int trials) {
		for (int i = 0; i < trials; i++) {
			int sx = (int)(Math.random() * width - 5);
			int sy = (int)(Math.random() * height - 5);
			tryRoom(sx, sy);
		}
	}
	
	/**
	 * A method that tries to place a square room in the specified location
	 * Checks every tile of the room, if they are all walls then make every tile a floor
	 */
	private void tryRoom(int sx, int sy) {
		int roomMin = 6;
		int roomMod = 4;
		int cx = (int)(Math.random() * roomMod) + roomMin;
		int cy = (int)(Math.random() * roomMod) + roomMin;
		int successes = 0;
		for (int x = 0; x < cx; x++)
			for (int y = 0; y < cy; y++)
				if (x+sx > 0 && x+sx < width && y+sy > 0 && y+sy < height)
					if (tiles[x+sx][y+sy].isWall())
						successes++;
		if (successes != cx * cy)
			return;
		for (int x = 1; x < cx-1; x++)
			for (int y = 1; y < cy-1; y++) {
				tiles[x+sx][y+sy] = Tile.getRandomFloor();
			}
	}
	
	/**
	 * A method that generates a maze among the entire world after rooms are placed
	 * Then it marks each unconnected area as a region
	 * Finds the connector of each region (the space that separates two regions)
	 * Resolves those connections (to create entrances)
	 * And then repeatedly removes all dead ends
	 */
	public void buildHallways() {
		int sx;
		int sy;
		do {
			sx = (int)(Math.random()*width);
			sy = (int)(Math.random()*height);
		} while (adjacentToFloor(sx,sy) > 0);
		generateMaze(sx,sy);
		
		
		for (int x = 1; x < width-1; x++) {
			for (int y = 1; y < height-1; y++) {
				if (adjacentToFloor(x,y) == 0) {
					generateMaze(x,y);
				}
			}
		}
		createRegions();
		
		findConnectors();
		
		resolveConnections();
		
		for (int x = 1; x < width-1; x++) {
			for (int y = 1; y < height-1; y++) {
				if (isDeadEnd(x, y)) {
					tiles[x][y] = Tile.WALL0;
					regions[x][y] = 0;
					x = 1;
					y = 1;
				}
			}
		}
	}
	/**
	 * A basic algorithm that generates a maze
	 * @param sx:	Start x coordinate
	 * @param sy:	Start y coordinate
	 */
	private void generateMaze(int sx, int sy) {
		set.add(new Point(sx,sy));
		
		while (set.size() > 0) {
			Point c = set.get(0);
			set.remove(c);
			if (adjacentToFloor(c.x,c.y) > 1 || tiles[c.x][c.y] == Tile.getRandomFloor())
				continue;
			tiles[c.x][c.y] = Tile.getRandomFloor(); 
			addNeighbors(c);
		}
	}
	
	/**
	 * Checks if the input coordinates are adjacent to multiple floor tiles
	 */
	private int adjacentToFloor(int x, int y) {
		Point s = new Point(x,y);
		Point parent = null;
		List<Point> n = s.neighbors4();
		for (Point p : n) {
			if (p.x > 0 && p.x < width & p.y > 0 && p.y < height) {
				if (!tiles[p.x][p.y].isWall())
					parent = p;
			}
		}
		n = s.neighbors8();
		int adjacency = 0;
		for (Point p : n) {
			if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) 
				adjacency++;
			//else if (map[p.x][p.y] != 1 && !isNeighbors(parent,p))
			else if (!tiles[p.x][p.y].isWall() && !isNeighbors(parent,p))
				adjacency++;
		}
		return adjacency;
	}
	
	/**
	 * Add all neighbors of the point to the working set
	 */
	private void addNeighbors(Point p) {
		List<Point> n = p.neighbors4();
		for (Point c : n) {
			if (adjacentToFloor(c.x,c.y) <= 1)
				set.add(c);
		}
	}
	
	/**
	 * Check if the two input points are adjacent
	 */
	private boolean isNeighbors(Point a, Point b) {
		if (a == null || b == null)
			return false;
		List<Point> points = a.neighbors4();
		for (Point p : points)
			if (p.equals(b))
				return true;
		return false;
	}
	
	/**
	 * Create regions using a recursive flood fill algorithm
	 */
	private int[][] regions;
	private int nextRegion;	//Keep track of region numbers
	private void createRegions() {
		regions = new int[width][height];
		nextRegion = 1;
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				if (!tiles[x][y].isWall() && regions[x][y] == 0){
					fillRegion(nextRegion++, x, y);
				}
			}
        }
	}
	
	/**
	 * The recursive flood fill algorithm
	 * @param region:	Region number to fill the area with
	 * @param x:		x coordinate
	 * @param y:		y coordinate
	 */
	private void fillRegion(int region, int x, int y) {
        ArrayList<Point> open = new ArrayList<Point>();
        open.add(new Point(x,y));
        regions[x][y] = region;
    
        while (!open.isEmpty()){
            Point p = open.remove(0);
            
            for (Point neighbor : p.neighbors4()){
                if (regions[neighbor.x][neighbor.y] != 0
                  || tiles[neighbor.x][neighbor.y].isWall())
                    continue;

                regions[neighbor.x][neighbor.y] = region;
                open.add(neighbor);
            }
        }
    }
	
	/**
	 * Moves across every space on the map, if that space is between two different regions, mark it as a connector
	 */
	int[][] connectors;
	ArrayList<Point> connections;
	private void findConnectors() {
		connectors = new int[width][height];
		connections = new ArrayList<Point>();
		for (int x = 1; x < width-1; x++) {
			for (int y = 1; y < height-1; y++) {
				if (tiles[x][y].isWall() && regions[x][y] == 0) {
					if (regions[x-1][y] != regions[x+1][y] && regions[x-1][y] != 0 && regions[x+1][y] != 0) {
						connectors[x][y] = 1;
						connections.add(new Point(x,y));
					} if (regions[x][y-1] != regions[x][y+1] && regions[x][y-1] != 0 && regions[x][y+1] != 0) {
						connectors[x][y] = 1;
						connections.add(new Point(x,y));
					}
				}
			}
		}
	}
	
	/**
	 * After all connectors are marked, resolve them
	 * First shuffle all the connections
	 * Then check each connector in the list
	 * If it is still a valid connector, turn it into a floor and set both region numbers to be the same
	 * Otherwise ignore it
	 */
	private void resolveConnections() {
		Collections.shuffle(connections);
		for (int i = 0; i < connections.size(); i++) {
			Point p = connections.get(i);
			if (regions[p.x-1][p.y] != regions[p.x+1][p.y] && regions[p.x-1][p.y]!= 0 && regions[p.x+1][p.y]!=0) {
				int n = regions[p.x-1][p.y];
				changeRegion(regions[p.x+1][p.y], n);
				regions[p.x][p.y] = n;
				tiles[p.x][p.y] = Tile.getRandomFloor();  
			} else if (regions[p.x][p.y-1] != regions[p.x][p.y+1] && regions[p.x][p.y-1]!= 0 && regions[p.x][p.y+1]!=0) {
				int n = regions[p.x][p.y-1];
				changeRegion(regions[p.x][p.y+1], n);
				regions[p.x][p.y] = n;
				tiles[p.x][p.y] = Tile.getRandomFloor(); 
			}
		}
	}
	
	/**
	 * Moves across the entire region and changes the region number into a new region number
	 */
	private void changeRegion(int region, int change) {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (regions[x][y] == region)
					regions[x][y] = change;
	}
	
	/**
	 * A helper method to check if a given tile is a dead end (more than 2 adjacent floors
	 */
	private boolean isDeadEnd(int x, int y) {
		int n = 0;
		if (tiles[x][y].isWall())
			return false;
		if (!tiles[x-1][y].isWall())
			n++;
		if (!tiles[x+1][y].isWall())
			n++;
		if (!tiles[x][y-1].isWall())
			n++;
		if (!tiles[x][y+1].isWall())
			n++;
		return n <= 1;
	}
	
	/**
	 * Iterate over every tile and randomize its type to add variability
	 */
	private void finalizeWorld() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (y == 0) {
					tiles[x][y] = Tile.getTopWall();
					continue;
				}
				
				if (tiles[x][y].isWall()) {
					if (tiles[x][y-1].isWall())
						tiles[x][y] = Tile.getBottomWall();
					else
						tiles[x][y] = Tile.getTopWall();
				}
			}
		}
	}
	
	/**
	 * An extra method for debugging, prints the current map to the terminal
	 */
	@SuppressWarnings("unused")
	private void printMap() {
		for (int y=0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char c = ' ';
				if (tiles[x][y] != null) {
					if (!tiles[x][y].isWall())
						c = '0';
					if (tiles[x][y].isWall())
						c = '1';
				} else {
					c = 'E';
				}
				System.out.print(c);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
