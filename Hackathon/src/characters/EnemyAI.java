package characters;

import tools.Point;

public class EnemyAI extends AI {

	private Player player;	//The human player that this player wants to seek out
	private Point target;	//The last point the enemy saw the player at, to help pathfind around corners and stuff

	/**
	 * Constructor for the EnemyAI
	 * @param owner:	Player that owns this entity
	 * @param player:	Player to initialize this AI (hocker player, goose, etc)
	 * */
	public EnemyAI(Player owner, Player player) {
		super(owner);
		this.player = player;
	}
	
	/**
	 * On this creatures turn, if it can see the player then it will set the target point as the players location
	 * If it is then standing on the target point (followed the player but lost them lets say), set it to null
	 * Then it checks to see if this has a target point
	 * If it has a target point, it will move towards the target point
	 * Otherwise, it will wander
	 */
	@Override
	public void takeTurn() {
		if (owner.canSee(player.x, player.y))
			target = new Point(player.x, player.y);
		
		if (target != null && owner.x == target.x && owner.y == target.y)
			target = null;
		
		if (target == null)
			wander();
		else
			moveTo(target.x,target.y);
	}
}
