package characters;


/**
 * While this looks (and is) useless, the game crashes without it
 * The ability to see something is passed into a players AI, so the player needs an AI to see
 */
public class PlayerAI extends AI {

	public PlayerAI(Player owner) {
		super(owner);
	}

}