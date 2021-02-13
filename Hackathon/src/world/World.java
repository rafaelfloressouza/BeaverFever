package world;

import java.util.ArrayList;
import java.util.List;

import characters.Player;

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
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tiles[x][y] = Tile.FLOOR;
	}
}
