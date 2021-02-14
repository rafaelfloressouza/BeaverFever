package characters;

import java.util.ArrayList;
import java.util.List;

import items.Item;
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

	// Combat stats
	private int hp;
	public int hp() { return hp; }
	private int maxHP;
	public int maxHP() { return maxHP; }
	private int attack;
	public int attack() { return attack; }
	private int minDamage;
	private int maxDamage;
	private int dodging;
	public int dodging() { return dodging; }

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
	
	/**
	 * Constructor for the player
	 * @param world:	world in which player will be generated
	 * @param name:		The name of the player
	 * @param image:	Image for the player
	 * @param type:		Type of player (either human or AI)
	 * */
	public Player(World world, String name, Image image, PlayerType type) {
		this.world = world;
		this.name = name;
		this.image = image;
		this.type = type;
	}
	
	/**
	 * Updates the hp of the player (makes sure if hp is 0, the player dies)
	 * @param x:	Value by which to adjust the hp (either + or -)
	 */
	public void changeHP(int x) { 
		hp = Math.min(maxHP, hp + x);
		if (hp <= 0)
			die();
	}

	/**
	 * Updates the min and max damage done by a player
	 * @param min:	Min amount of damage done by a player
	 * @param max:	Max amount of damage done by a player
	 * */
	public void setDamage(int min, int max) {
		minDamage = min;
		maxDamage = max;
	}

	/**
	 * Gets the damage that can be done by a player
	 * */
	public int getDamage() {
		return (int)(Math.random() * (maxDamage - minDamage) + minDamage);
	}

	/**
	 * Initializes all the stats of a creature too default values.
	 * @param hp:		How much life the creature will have
	 * @param attack:	Amount of damage a creature can make
	 * @param dodging:	Amount of dogning a creature has
	 * @param minDamage: Minimum amount of damage done by a creature
	 * @param maxDamage: Maximum amount of damage done by a creature
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
	 * When a player dies, remove them from the world and notify everything that can see them
	 */
	public void die() {
		world.removePlayer(this);
		world.setItem(new Point(x,y), Item.getRandomBillType());
		if (type == PlayerType.HUMAN)
			notify("You are defeated!", Color.ORANGERED);
		else
			notifyOthers("The " + name() + " is defeated!", Color.AQUAMARINE);
	}
	
	/**
	 * In charge of making AI attack a target
	 * @param target: Player to which the AI will attack
	 */
	public void attackTarget(Player target) {
		//First, make sure this isnt AI friendly fire
		if (type == PlayerType.AI && target.type() == PlayerType.AI)
			return;
		
		//First generate a random number from 0 to 10 and add that to your attack to see if the other player dodges
		int roll = (int)(Math.random() * 11);
		if (roll + attack() > target.dodging()) {
			//If the target fails to dodge, they lose HP
			int damage = getDamage();
			target.changeHP(-damage);
			notify("You hit the " + target.name() + " for " + damage + " damage!", Color.BISQUE);
			target.notify("The " + name() + " hit you for " + damage + " damage!", Color.ORANGERED);
		} else {
			notify("You missed " + target.name(), Color.BISQUE);
			target.notify("The " + name() + " missed you", Color.BISQUE);
		}
	}
	
	/**
	 * Calls this AI to take the turn
	 */
	public void takeTurn() { ai.takeTurn(); }
	
	/**
	 * Moves AI in the specified direction
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
			attackTarget(other);
		} else {
			x += sx;
			y += sy;
			if (type == PlayerType.HUMAN) {
				Point p = new Point(x,y);
				if (world.containsItem(p)) {
					Item i = world.getItem(p);
					if (i.value() > 0) {
						score += i.value();
						notify("You picked up $" + i.value() + "!", Color.YELLOW);
					} if (i.food() > 0) {
						changeHP(i.food());
						notify("You ate " + i.getName() + "!", Color.LAWNGREEN);
					}
					world.removeItem(p);
				}
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

	// Vision radius in pixels
	private int visionRadius;
	public int visionRadius() { return visionRadius; }

	// Used to set the vision radius of an player
	public void setVisionRadius(int r) { visionRadius = r; }

	// Field of view of a player
	private FieldOfView fov;
	public FieldOfView fov() { return fov; }
	public void setFOV(FieldOfView fov) { this.fov = fov; }

	// Use to determine if an ai can see a point
	public boolean canSee(int wx, int wy) {
		return ai.canSee(wx, wy);
	}

	// Used to determine if player can still see a point (or it should be shown as explored)
	public boolean hasSeen(int wx, int wy) {
		return fov.hasSeen(wx, wy);
	}

	// Returns either the tile or an explored version of the tile
	public Tile tile(int wx, int wy) {
    	if (canSee(wx, wy))
            return world.tile(wx, wy);
        else
            return rememberedTile(wx, wy);
    }

    // Function returns an actual tile at the specified coordinates
    public Tile realTile(int wx, int wy) {
    	return world.tile(wx, wy);
    }

    // Function returns either a NULL tile or an explored tile
    public Tile rememberedTile(int wx, int wy) {
    	if (type == PlayerType.AI)
    		return Tile.NULL;
    	else
    		return fov.tile(wx, wy);
    }
    
    //Character's list of messages
  	private List<Message> messages;
  	public List<Message> messages() { return messages; }
  	public void startMessages() { messages = new ArrayList<>(); }
  	
  	// Notify this player
  	public void notify(String text, Color color) {
  		if (messages == null)
  			return;
  		messages.add(new Message(text,color));
  		if (messages.size()>5)
  			messages.remove(0);
  	}
  	
  	// Notify all player that can see this one
  	public void notifyOthers(String text, Color color) {
  		for (Player p : world.players())
  			if (p.canSee(x, y))
  				p.notify(text, color);
  	}

	/**
	 * A way to generate new preset players to populate the world with
	 */
	public static Player getNewHuman(World world){
		Player p = new Player(world, "Player", Load.newImage("players/beaver-s.png"), Player.PlayerType.HUMAN);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		p.setStats(20, 5, 10, 3, 7);
		p.startMessages();
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
	
	public static Player getNewBlueHockey(World world, Player player) {
		Player p = new Player(world, "Blue Hockey Player", Load.newImage("players/hockey2.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(10, 3, 8, 1, 5);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getNewWhiteHockey(World world, Player player) {
		Player p = new Player(world, "White Hockey Player", Load.newImage("players/hockey3.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(10, 1, 1, 1, 10);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getNewOrangeHockey(World world, Player player) {
		Player p = new Player(world, "Orange Hockey Player", Load.newImage("players/hockey4.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(10, 1, 10, 1, 2);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getTealCivilian(World world, Player player) {
		Player p = new Player(world, "Teal Civilian", Load.newImage("players/person1.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(7, 2, 5, 1, 3);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getBrownCivilian(World world, Player player) {
		Player p = new Player(world, "Brown Civilian", Load.newImage("players/person2.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(7, 2, 5, 1, 3);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getYellowCivilian(World world, Player player) {
		Player p = new Player(world, "Yellow Civilian", Load.newImage("players/person3.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(7, 1, 7, 1, 2);
		new EnemyAI(p, player);
		return p;
	}
	
	public static Player getBlueCivilian(World world, Player player) {
		Player p = new Player(world, "Blue Civilian", Load.newImage("players/person4.png"), PlayerType.AI);
		p.setVisionRadius(9);
		Point spawn = world.getEmptySpace();
		p.x = spawn.x;
		p.y = spawn.y;
		world.addPlayer(p);
		p.setStats(7, 3, 6, 2, 5);
		new EnemyAI(p, player);
		return p;
	}
	
}
