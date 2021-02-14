package world;

import java.sql.Time;
import java.util.*;

import characters.Player;
import items.Item;
import tools.Point;

public class World {

	private int width;
	private int height;
	public int width() { return width; }
	public int height() { return height; }
	private Tile[][] tiles;
	private HashMap<Point, Item> bills;
	public void setBill(Point p, Item m) { bills.put(p, m); }
	private HashMap<Point, Item> foods;
	public void setFood(Point p, Item m) {foods.put(p, m); }

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
		this.bills = new HashMap<>();
		this.foods = new HashMap<>();
	}
	
	/**
	 * Generates the world
	 */
	public void generate() {
		WorldBuilder b = new WorldBuilder(width, height);
		this.tiles = b.generate(200);
		generateBills();
		generateFood();
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
	 * Checks whether a tile contains a bill
	 */
	public Boolean containsBill(Point p){
		return bills.containsKey(p);
	}

	/**
	 * Get a bill based on a point
	 */
	public Item getBill(Point p) {
		return bills.get(p);
	}

	/**
	 * Remove a bill based on a point
	 */
	public void removeBill(Point p) {
		bills.remove(p);
	}

	/**
	 * Checks whether a tile contains a foood
	 */
	public Boolean containsFood(Point p){
		return foods.containsKey(p);
	}

	/**
	 * Get a food based on a point
	 **/
	public Item getFood(Point p){
		return foods.get(p);
	}

	/**
	 *  Removes a food based on a point
	 * */
	public void removeFood(Point p){
		foods.remove(p);
	}

	/**
	 * Populates random empty tiles with bills
	 */
	public void generateBills(){
		int numBills = 10;
		Point curPoint;
		for(int i = 0; i < numBills;i++) {
			curPoint = getEmptySpace();
			bills.put(curPoint, Item.getRandomBillType());
			i++;
			this.bills.put(curPoint, Item.getRandomBillType());
		}
	}

	/**
	 * Populates random empty tiles with syrups
	 */
	public void generateFood(){
		int numSyrups = 10;
		Point curPoint;
		for(int i = 0; i < numSyrups;){
			curPoint = getEmptySpace();
			if(!this.bills.containsKey(curPoint)){
				this.foods.put(curPoint, Item.getRandomFood());
				i++;
			}
		}
	}
}
