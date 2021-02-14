package characters;

import items.Money;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.FieldOfView;
import tools.Load;
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
    
    // This players score
    private int score;
    public int score() { return score; }
	
	// Constructor
	public Player(World world, String name, Image image, PlayerType type) {
		this.world = world;
		this.name = name;
		this.image = image;
		this.type = type;
	}
	
	/**
	 * Combat Stats
	 */
	private int hp;
	public int hp() { return hp; }
	private int maxHP;
	public int maxHP() { return maxHP; }
	public void changeHP(int x) { 
		hp = Math.min(maxHP, hp + x);
		if (hp <= 0)
			die();
	}
	private int attack;
	public int attack() { return attack; }
	private int minDamage;
	private int maxDamage;
	public void setDamage(int min, int max) {
		minDamage = min;
		maxDamage = max;
	}
	public int getDamage() {
		return (int)(Math.random() * (maxDamage - minDamage) + minDamage);
	}
	private int dodging;
	public int dodging() { return dodging; }
	/**
	 * Basic method to set the starting stats of a creature
	 */
	public void setStats(int hp, int attack, int dodging, int minDamage, int maxDamage) {
		this.hp = hp;
		this.maxHP = hp;
		this.attack = attack;
		this.dodging = dodging;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	/**
	 * When a player dies, remove them from the world
	 */
	public void die() {
		world.removePlayer(this);
		world.setBill(new Point(x,y), World.getRandomBillType());
	}
	
	/**
	 * The attack algorithm
	 */
	public void attack(Player target) {
		//First generate a random number from 0 to 10 and add that to your attack to see if the other player dodges
		int roll = (int)(Math.random() * 11);
		if (roll + attack() > target.dodging()) {
			//If the target fails to dodge, they lose HP
			target.changeHP(-getDamage());
		} else {
			//getMessages.add(new Message(target.name()+ " missed", Color.YELLOW));
		}
	}
	
	/**
	 * Calls this ai to take the turn
	 */
	public void takeTurn() { ai.takeTurn(); }
	
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
		
		Player other = world.player(x+sx, y+sy);
		if (other != null && other != this) {
			attack(other);
		} else {
			x += sx;
			y += sy;
			Point p = new Point(x,y);
			if (world.containsBill(p)) {
				score += world.getBill(p).value();
				world.removeBill(p);
			}
		}
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
	public boolean hasSeen(int wx, int wy) {
		return fov.hasSeen(wx, wy);
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
    
    //Character's arraylist of messages
  	private GetMessages getMessages;
  	
  	//notify the player and add messages
  	public void Notify(Player p, String message, Color color) {
  		p.getMessages.add(new Message(message, color));
  	}
  	
  	public void NotifyAll(String message, Color color) {
  		getMessages.add(new Message(message, color));
  	}

	/**
	 * A way to generate new players to populate the world with
	 */
	public static Player getNewHuman(World world){
		Player p = new Player(world, "Player", Load.newImage("players/beaver.png"), Player.PlayerType.HUMAN);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		p.setStats(20, 5, 10, 3, 7);
		world.addPlayer(p);
		new PlayerAI(p);
		return p;
	}
	public static Player getNewRedHockey(World world, Player player){
		Player p = new Player(world, "Red Hockey Player", Load.newImage("players/hockey_red1.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(10, 4, 8, 1, 4);
		new EnemyAI(p, player);
		return p;
	}
	public static Player getNewCanadaGoose(World world, Player player){
		Player p = new Player(world, "Canada Goose", Load.newImage("players/goose.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(12, 4, 8, 2, 5);
		new EnemyAI(p, player);
		return p;
	}
}
