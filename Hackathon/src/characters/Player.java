package characters;

import javafx.scene.image.Image;
import world.World;

public class Player {

	// Others
	public enum PlayerType{AI, HUMAN};

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

	// This Player Type
	private PlayerType type;
	public PlayerType type(){ return type;}
	
	// Constructor
	public Player(World world, String name, Image image, PlayerType type) {
		this.world = world;
		this.name = name;
		this.image = image;
		this.type = type;
	}
	
	/**
	 * Moves in the specified direction
	 * @param sx:	relative x direction
	 * @param sy:	relative y direction
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

	public Player getNewPlayer(World world, String name, Image image, PlayerType type){
		return new Player(world, name, image, type);
	}
}
