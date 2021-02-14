package characters;

public class EnemyAI extends AI {
	private Player player;	//The human player that this player wants to seek out
	
	public EnemyAI(Player owner, Player player) {
		super(owner);
		this.player = player;
	}
	
	/**
	 * On this creatures turn, if it can see the player then it will move towards them (including attacking them if adjacent)
	 * Otherwise, wander in a random direction
	 */
	@Override
	public void takeTurn() {
		if (owner.canSee(player.x, player.y))
			moveTo(player.x,player.y);
		else
			wander();
	}

}
