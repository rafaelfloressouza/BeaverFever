package world;

import java.util.ArrayList;
import java.util.List;

import characters.Player;
import tools.Point;

public class World {
	private int width;
	private int height;
	public int width() { return width; }
	public int height() { return height; }
	private Tile[][] tiles;
	
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
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Generates the world
	 */
	public void generate() {
		WorldBuilder b = new WorldBuilder(width, height);
		tiles = b.generate(200);
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
}
