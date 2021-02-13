package world;

import javafx.scene.image.Image;
import tools.Load;

public enum Tile {
	WALL(Load.newImage("tiles/wall.png")), 
	FLOOR(Load.newImage("tiles/floor.png")),
	NULL(null);

	private Image image;
	public Image image() { return image; }
	
	private Tile(Image image) {
		this.image = image;
	}
	
	public boolean isWall() { return this == WALL; }
}
