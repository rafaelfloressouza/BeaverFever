package world;

import javafx.scene.image.Image;
import tools.Load;

public enum Tile {
	WALL0(Type.WALL, Load.newImage("tiles/wallbig.png", 0, 0, 42, 42)),
	WALL1(Type.WALL, Load.newImage("tiles/wallbig.png", 42, 0, 42, 42)),
	WALL2(Type.WALL, Load.newImage("tiles/wallbig.png", 84, 0, 42, 42)),
	WALL3(Type.WALL, Load.newImage("tiles/wallbig.png", 126, 0, 42, 42)),
	WALL4(Type.WALL, Load.newImage("tiles/wallbig.png", 168, 0, 42, 42)),
	WALL5(Type.WALL, Load.newImage("tiles/wallbig.png", 0, 42, 42, 42)),
	WALL6(Type.WALL, Load.newImage("tiles/wallbig.png", 42, 42, 42, 42)),
	WALL7(Type.WALL, Load.newImage("tiles/wallbig.png", 84, 42, 42, 42)),
	WALL8(Type.WALL, Load.newImage("tiles/wallbig.png", 126, 42, 42, 42)),
	WALL9(Type.WALL, Load.newImage("tiles/wallbig.png", 168, 42, 42, 42)),
	FLOOR0(Type.FLOOR, Load.newImage("tiles/floorbig.png", 0, 0, 42, 42)),
	FLOOR1(Type.FLOOR, Load.newImage("tiles/floorbig.png", 42, 0, 42, 42)),
	FLOOR2(Type.FLOOR, Load.newImage("tiles/floorbig.png", 84, 0, 42, 42)),
	NULL(null, null);

	private enum Type {
		WALL, FLOOR;
	}
	private Type type;
	
	private Image image;
	public Image image() { return image; }
	
	private Tile(Type type, Image image) {
		this.type = type;
		this.image = image;
	}
	
	public boolean isWall() { return type == Type.WALL; }
	
	/**
	 * Methods to return a random wall based on the snow on top of it
	 * @return
	 */
	public static Tile getTopWall() {
		switch((int)(Math.random()*5)) {
		case 0: return WALL0;
		case 1: return WALL1;
		case 2: return WALL2;
		case 3: return WALL3;
		default: return WALL4;
		}
	}
	public static Tile getBottomWall() {
		switch((int)(Math.random()*5)) {
		case 0: return WALL5;
		case 1: return WALL6;
		case 2: return WALL7;
		case 3: return WALL8;
		default: return WALL9;
		}
	}
	public static Tile getRandomFloor() {
		switch((int)(Math.random()*3)) {
		case 0: return FLOOR0;
		case 1: return FLOOR1;
		default: return FLOOR2;
		}
	}
}