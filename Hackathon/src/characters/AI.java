package characters;

import java.util.List;

import tools.Line;
import tools.Path;
import tools.Point;

public abstract class AI {
	protected Player owner;
	
	public AI(Player owner) {
		this.owner = owner;
		owner.setAI(this);
	}
	
	/**
	 * What the owner should do on its turn
	 */
	public void takeTurn() {
		// Should be overridden by the enemy, the player ai does nothing
	}
	
	/**
	 * Check if the owner can see a target point by drawing a line to it
	 */
	public boolean canSee(int wx, int wy) {
        if ((owner.x-wx)*(owner.x-wx) + (owner.y-wy)*(owner.y-wy) > owner.visionRadius()*owner.visionRadius())
            return false;
        for (Point p : new Line(owner.x, owner.y, wx, wy)){
            if (!owner.realTile(p.x, p.y).isWall() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
        return true;
    }
	
	/**
	 * Move in a random direction
	 */
	public void wander(){
		int mx = (int)(Math.random() * 3) - 1;
		int my = (int)(Math.random() * 3) - 1;
		if (!owner.realTile(owner.x+mx,owner.y+my).isWall()) {
			owner.move(mx, my);
			return;
		}
    }
	
	/**
	 * Creates a list of points using the pathfinder aimed at the target points
	 * Then move to the next point on the list
	 */
	public void moveTo(int x, int y) {
    	List<Point> points = new Path(owner, x, y).points();
		if (points == null || points.size() == 0)
			return;
		int mx = points.get(0).x - owner.x;
		int my = points.get(0).y - owner.y;
		owner.move(mx, my);
	}

}
