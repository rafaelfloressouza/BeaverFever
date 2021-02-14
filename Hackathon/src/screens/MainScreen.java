package screens;

import characters.Player;
import items.Money;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.FieldOfView;
import tools.Load;
import tools.Point;
import world.World;

public class MainScreen extends Screen {
	private World world;
	private Player player;
	private Font mainfont;

	public MainScreen(int width, int height) {
		super(width, height);
		world = new World(30,30);
		world.generate();
		
		mainfont = Load.newFont("SDS_8x8.ttf", 24);
		
		//Creates the player and adds it to the world
		player = Player.getNewHuman(world);
		player.setFOV(new FieldOfView(world));
		
		populate();
	}

	@Override
	public void displayOutput() {
		// How many tiles can be displayed on the screen at once
		int tilewidth = width / 48;
		int tileheight = height / 48;
		
		// Gets the leftmost and topmost tile to be displayed to the player
		int leftmost = getLeftmostTile(tilewidth);
		int topmost = getTopmostTile(tileheight);
		
		player.fov().update(player.x, player.y, player.visionRadius());
		
		// Using that info, displays visible tiles and creatures on a grid
		displayTiles(tilewidth, tileheight, leftmost, topmost);
		displayCreatures(tilewidth, tileheight, leftmost, topmost);
		displayUI();
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
		
		// After the players turn is complete, iterate through every other player and take their turn
		// This calls their ai function
		// Cannot do for (Player p : world.players()) as this can throw a ConcurrentModificationException 
		for (int i = 0; i < world.players().size(); i++)
			world.players().get(i).takeTurn();
		
		if (player.hp() <= 0)
			//If the player is dead, return the game loss screen
			return new EndGameScreen(width, height, false);
		
		return this;
	}
	
	private void displayTiles(int tilewidth, int tileheight, int left, int top) {
		for (int x = 0; x < tilewidth; x++){
			for (int y = 0; y < tileheight; y++){
				int wx = x + left;
				int wy = y + top;
				if (player.canSee(wx, wy)) {
					draw(root, player.tile(wx, wy).image(), x*48,y*48);
					displayBill(world.getBill(new Point(wx,wy)),wx,wy,x,y, 0.0);
				} else {
					draw(root, player.tile(wx, wy).image(), x*48,y*48, -0.7);
					displayBill(world.getBill(new Point(wx,wy)),wx,wy,x,y, -0.7);
				}
			}
		}
	}
	private void displayBill(Money m, int wx, int wy, int x, int y, double tint) {
		if (m == null || !player.hasSeen(wx, wy))
			return;
		draw(root, m.image(), x*48, y*48, tint);
	}
	private void displayCreatures(int width, int height, int left, int top) {
		for (Player c : world.players()) {
			if (c.x >= left && c.x < left + width &&
				c.y >= top && c.y < top + height &&
				player.canSee(c.x, c.y)) {
				draw(root, c.image(), (c.x-left)*48, (c.y-top)*48);
				displayHealth(c, (c.x-left)*48, (c.y-top)*48);
			}
		}
	}
	private void displayUI() {
		write(root, "$" + player.score(), 800, 764, mainfont, Color.WHITE);
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
	
	/**
	 * Spawns a bunch of enemies into the world for the player to confront
	 */
	private void populate() {
		for (int i = 0; i < 6; i++)
			Player.getNewRedHockey(world, player);
		for (int i = 0; i < 3; i++)
			Player.getNewCanadaGoose(world, player);
	}
	
	/**
	 * Displays a health bar at a given location
	 */
	private void displayHealth(Player p, int x, int y) {
		Image i;
		if (p.hp() > (3*p.maxHP())/4)
			i = healthGreen;
		else if (p.hp() > p.maxHP()/2)
			i = healthYellow;
		else if (p.hp() > 1*p.maxHP()/4)
			i = healthOrange;
		else
			i = healthRed;
		draw(root, i, x, y + 36);
	}
	private static Image healthGreen = Load.newImage("icons/health-bar.png", 0, 0, 48, 8);
	private static Image healthYellow = Load.newImage("icons/health-bar.png", 0, 8, 48, 8);
	private static Image healthOrange = Load.newImage("icons/health-bar.png", 0, 16, 48, 8);
	private static Image healthRed = Load.newImage("icons/health-bar.png", 0, 24, 48, 8);

}
