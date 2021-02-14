package world;

import java.util.*;

import characters.Player;
import items.Item;
import tools.Point;

public class World {

	// Variables used in the world
	private int width;
	private int height;
	public int width() { return width; }
	public int height() { return height; }
	private Tile[][] tiles;
	private HashMap<Point, Item> items;

	/**
	 * A list of all players
	 */
	private List<Player> players;
	public List<Player> players() { return players; }
	public void addPlayer(Player c) { players.add(c); }
	public void removePlayer(Player c) { players.remove(c); }
	public Player player(int x, int y) {
		for (Player p : players)
			if (p.x == x && p.y == y)
				return p;
		return null;
	}
	
	/**
	 * Gets a designated tile by coordinates
	 */
	public Tile tile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return Tile.NULL;
		return tiles[x][y]; 
	}

	/**
	 * Constructor, width and height are by tiles
	 */
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		this.players = new ArrayList<>();
		this.items = new HashMap<>();
	}
	
	/**
	 * Generates the world
	 */
	public void generate() {
		WorldBuilder b = new WorldBuilder(width, height);
		this.tiles = b.generate(200);
		generateItems();
	}
	
	/**
	 * Get a random empty space (a floor with no walls or other players)
	 */
	public Point getEmptySpace() {
		int x = 0;
		int y = 0;
		while (tiles[x][y].isWall() || player(x,y) != null) {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		}
		return new Point(x,y);
	}

	/**
	 * Checks whether a tile contains an item
	 */
	public Boolean containsItem(Point p){ return items.containsKey(p); }

	/**
	 * Get an item based on a point
	 **/
	public Item getItem(Point p){ return items.get(p); }

	/**
	 *  Removes an item based on a point
	 **/
	public void removeItem(Point p){ items.remove(p); }
	
	/**
	 *  Sets the item based on a point
	 **/
	public void setItem(Point p, Item m) { items.put(p, m); }

	/**
	 * Populates random empty tiles with items
	 */
	public void generateItems(){
		int numSyrups = 6;
		int numBills = 6;
		for(int i = 0; i < numSyrups; i++){
			Point p = getEmptySpace();
			if(!containsItem(p))
				setItem(p, Item.getRandomFood());
		}
		for(int i = 0; i < numBills; i++){
			Point p = getEmptySpace();
			if(!containsItem(p))
				setItem(p, Item.getRandomBillType());
		}
	}
}
