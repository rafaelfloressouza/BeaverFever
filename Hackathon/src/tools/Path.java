package tools;

import java.util.List;
import characters.Player;


/**
 * Code for pathfinding modified and improved from a previous project
 */
public class Path {

	private static PathFinder pf = new PathFinder();

	private List<Point> points;
	public List<Point> points() { return points; }

	/**
	 * Creates a new pathfinder object, centered on the player to pathfind to the target coordinates
	 */
	public Path(Player player, int x, int y){
		points = pf.findPath(player, 
				new Point(player.x, player.y), 
				new Point(x, y), 
				300);
	}
}