package screens;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.Load;

public class StartScreen extends Screen {

	/**
	 * Constructor for the StartScreen
	 * @param width:	Width of the screen
	 * @param height:	Height of the screen
	 * */
	public StartScreen(int width, int height) {
		super(width,height);
	}

	/**
	 * Handles user input and perform a certain action
	 * @param key:		KeyEvent that contains information about a key
	 * */
	@Override
	public Screen userInput(KeyEvent key) {
		KeyCode code = key.getCode();
		Screen s = this;
		if (code.equals(KeyCode.ENTER)){	// If enter is pressed, go the main screen
			s = new MainScreen(this.width, this.height);
		} else if (code.equals(KeyCode.ESCAPE)){ // If escape is pressed exit the game
			System.exit(0);
		} else if (code.equals(KeyCode.I)) { // If I is pressed, go to info screen
			s = new HelpScreen(this.width, this.height, this);
		}
		return s;
	}

	/**
	 * Displays output to the screen (starting screen image)
	 * */
	@Override
	public void displayOutput() {
		draw(root, Load.newImage("full-screens/starting_screen.png"),0, 0);
	}
}
