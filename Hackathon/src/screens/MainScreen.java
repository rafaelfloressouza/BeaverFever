package screens;

import characters.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.Load;
import world.World;

public class MainScreen extends Screen {
	private World world;
	private Player player;

	public MainScreen(int width, int height) {
		super(width, height);
		world = new World(30,30);
		world.generate();
		
		//Creates the player and adds it to the world
		player = new Player(world, "Player", Load.newImage("players/beaver.png"), Player.PlayerType.HUMAN);
		world.addPlayer(player);
	}

	@Override
	public void displayOutput() {
		// How many tiles can be displayed on the screen at once
		int tilewidth = width / 48;
		int tileheight = height / 48;
		
		// Gets the leftmost and topmost tile to be displayed to the player
		int leftmost = getLeftmostTile(tilewidth);
		int topmost = getTopmostTile(tileheight);
		
		// Using that info, displays visible tiles as a grid
		displayTiles(tilewidth, tileheight, leftmost, topmost);
		
		displayCreatures(tilewidth, tileheight, leftmost, topmost);
	}
	
	@Override
	public Screen userInput(KeyEvent key) {
		KeyCode code = key.getCode();
		if (code.equals(KeyCode.LEFT))
			player.move(-1, 0);
		if (code.equals(KeyCode.RIGHT))
			player.move(1, 0);
		if (code.equals(KeyCode.UP))
			player.move(0, -1);
		if (code.equals(KeyCode.DOWN))
			player.move(0, 1);
		return this;
	}
	
	private void displayTiles(int tilewidth, int tileheight, int left, int top) {
		for (int x = 0; x < tilewidth; x++){
			for (int y = 0; y < tileheight; y++){
				int wx = x + left;
				int wy = y + top;
				draw(root, world.tile(wx, wy).image(), x*48,y*48);
			}
		}
	}
	private void displayCreatures(int width, int height, int left, int top) {
		for (Player c : world.players()) {
			if (c.x >= left && c.x < left + width &&
					c.y >= top && c.y < top + height) {
				draw(root, c.image(), (c.x-left)*48, (c.y-top)*48);
			}
		}
	}
	
	/**
	 * Gets the leftmost tile to be displayed on the screen if the player is centered
	 * @param width: 	How many tiles that can be displayed on the screen at once
	 * @return:			The leftmost coordinate tile
	 */
	private int getLeftmostTile(int width) {
		return Math.max(0, Math.min(player.x - width / 2, world.width() - width));
	}
	
	/**
	 * Gets the topmost tile to be displayed on the screen if the player is centered
	 * @param height: 	How many tiles that can be displayed on the screen at once
	 * @return:			The topmost coordinate tile
	 */
	private int getTopmostTile(int height) {
		return Math.max(0, Math.min(player.y - height / 2, world.height() - height));
	}

}
