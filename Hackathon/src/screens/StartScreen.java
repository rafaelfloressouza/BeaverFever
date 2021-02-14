package screens;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.Load;

public class StartScreen extends Screen {
	public StartScreen(int width, int height) {
		super(width,height);
	}

	@Override
	public Screen userInput(KeyEvent key) {

		KeyCode code = key.getCode();
		Screen s = this;
		
		if (code.equals(KeyCode.ENTER)){
			s = new MainScreen(this.width, this.height);
		} else if (code.equals(KeyCode.ESCAPE)){
			System.exit(0);
		} else if (code.equals(KeyCode.I)) {
			s = new HelpScreen(this.width, this.height);
		}

		return s;
	}

	@Override
	public void displayOutput() {
		draw(root, Load.newImage("full-screens/starting_screen.png"),0, 0);
	}
}
