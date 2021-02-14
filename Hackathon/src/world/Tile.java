package world;

import javafx.scene.image.Image;
import tools.Load;

public enum Tile {

	// Types of tiles
	WALL(Load.newImage("tiles/wallsnow.png")),
	FLOOR(Load.newImage("tiles/snowfloor.png")),
	NULL(null);

	// Image related to a tile
	private Image image;
	public Image image() { return image; }

	/**
	 * Constructor for a Tile
	 * @param  image:	Accepts an image for the tile
	 * */
	private Tile(Image image) {
		this.image = image;
	}

	// Determine if a tile is a wall
	public boolean isWall() { return this == WALL; }
}
