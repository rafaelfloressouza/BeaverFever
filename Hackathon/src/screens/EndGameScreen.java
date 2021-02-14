package screens;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.Load;

public class EndGameScreen extends Screen{
    private Boolean type;

    /**
     * @param width:    Width of the screen
     * @param height:   Height of the screen
     * @param type:     True for win screen, false otherwise
     * */
    public EndGameScreen(int width, int height, Boolean type){
        super(width, height);
        this.type = type;
    }

    /**
     * @param key:  KeyEvent containing information about a key
     */
    @Override
    public Screen userInput(KeyEvent key) {
        KeyCode code = key.getCode();
        if (code.equals(KeyCode.ESCAPE))     	// If escape is pressed exit the program
        	System.exit(0);
        else if (code.equals(KeyCode.SPACE))   	// If space is pressed, go to start screen
        	return new StartScreen(width, height);
        return this;                            // If none of the above, return current screen
    }

    @Override
    public void displayOutput() {
        if(type){    // If win screen....
        	draw(root, Load.newImage("full-screens/win.png"), 0,0);
        } else {     // If lose screen...
        	draw(root, Load.newImage("full-screens/lose.png"), 0,0);
        }
    }
}
