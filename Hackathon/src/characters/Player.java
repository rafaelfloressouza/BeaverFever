package characters;

import javafx.scene.image.Image;
import world.World;

public class Player {
	
	// Map coordinates of this character
	public int x;
	public int y;
	
	// A reference to the world
	private World world;
	
	// This characters image to display
	private Image image;
	public Image image() { return image; }
	
	// This characters name
	private String name;
	public String name() { return name; }
	
	// Constructor
	public Player(World world, String name, Image image) {
		this.world = world;
		this.name = name;
		this.image = image;
	}
	
	/**
	 * Moves in the specified direction
	 * @param x:	relative x direction
	 * @param y:	relative y direction
	 */
	public void move(int sx, int sy) {
		if (x + sx >= world.width() ||
			x + sx < 0 ||
			y + sy >= world.height() ||
			y + sy < 0)
			return;
		x += sx;
		y += sy;
	}

}
