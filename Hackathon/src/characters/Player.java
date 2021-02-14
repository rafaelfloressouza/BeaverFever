package characters;

import com.sun.prism.paint.Color;

import javafx.scene.image.Image;
import tools.FieldOfView;
import tools.Point;
import world.Tile;
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
	
	// A reference to this creatures AI
	private AI ai;
    public void setAI(AI ai) { this.ai = ai; }
	
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
		if (world.tile(x + sx, y + sy).isWall())
			return;
		x += sx;
		y += sy;
	}
	
	/**
	 * Checks if this creature can enter a designated point
	 */
	public boolean canEnter(int cx, int cy) {
		return !world.tile(cx, cy).isWall() && world.player(cx,cy) == null;
	}
	
	/**
	 * Field of View stuff
	 */
	private int visionRadius;
	public int visionRadius() { return visionRadius; }
	public void setVisionRadius(int r) { visionRadius = r; }
	private FieldOfView fov;
	public FieldOfView fov() { return fov; }
	public void setFOV(FieldOfView fov) { this.fov = fov; }
	public boolean canSee(int wx, int wy) {
		return ai.canSee(wx, wy);
	}
	public Tile tile(int wx, int wy) {
    	if (canSee(wx, wy))
            return world.tile(wx, wy);
        else
            return rememberedTile(wx, wy);
    }
    public Tile realTile(int wx, int wy) {
    	return world.tile(wx, wy);
    }
    public Tile rememberedTile(int wx, int wy) {
    	if (type == PlayerType.AI)
    		return Tile.NULL;
    	else
    		return fov.tile(wx, wy);
    }

	/**
	 * A creature factory
	 */
	public static Player getNewPlayer(World world, String name, Image image, PlayerType type){
		Player p = new Player(world, name, image, type);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		if (type == PlayerType.HUMAN)
			new PlayerAI(p);
		world.addPlayer(p);
		return p;
	}
	
	//Character's arraylist of messages
	private GetMessages getMessages;
	
	//notify the playt
	public void Notify(PlayerType type, String message, Color color) {
		if (type == PlayerType.HUMAN) {
			getMessages.add(new Message(message, color));
		}
	}
	
	public void NotifyAll(String message, Color color) {
		getMessages.add(new Message(message, color));
	}
}
