package world;

import java.util.*;

import characters.Player;
import items.Money;
import tools.Point;

public class World {

	private int width;
	private int height;
	public int width() { return width; }
	public int height() { return height; }
	private Tile[][] tiles;
	private HashMap<Point, Money> bills;
	public void setBill(Point p, Money m) { bills.put(p, m); }

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
	}
	
	/**
	 * Generates the world
	 */
	public void generate() {
		WorldBuilder b = new WorldBuilder(width, height);
		this.tiles = b.generate(200);
		generateBills();
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
	 * Checkers whether a tile contains a bill
	 */
	public Boolean containsBill(Point p){
		return bills.containsKey(p);
	}
	
	public Money getBill(Point p) {
		return bills.get(p);
	}
	public void removeBill(Point p) {
		bills.remove(p);
	}

	/**
	 * Populates random empty tiles with bills making sure tiles are separated by n number of units
	 */
	public void generateBills(){
		int numBills = (int) ( (this.width * this.height) * 0.1);
		Point curPoint ;

		for(int i = 0; i < numBills; i++) {
			curPoint = getEmptySpace();
			bills.put(curPoint, getRandomBillType());
		}
	}

	/**
	* Returns a random bill type
	*/
	public static Money getRandomBillType(){
		int randNum = (int)(Math.random() * 3);

		if (randNum == 0){
			return Money.FIVE;
		} else if (randNum == 1){
			return Money.TWENTY;
		} else {
			return Money.HUNDRED;
		}
	}
}
