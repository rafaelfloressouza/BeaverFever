package screens;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.Load;

public class EndGameScreen extends Screen{
    private Boolean type;

    public EndGameScreen(int width, int height, Boolean type){
        super(width, height);
        this.type = type;
    }

    @Override
    public Screen userInput(KeyEvent key) {
        KeyCode code = key.getCode();

        if (code.equals(KeyCode.ESCAPE))     	// If escape is pressed exit the program
        	System.exit(0);
        else if (code.equals(KeyCode.SPACE))   	// If space is pressed, go to start screen
        	return new StartScreen(width, height);

        return this;
    }

    @Override
    public void displayOutput() {
        if(type){
        	draw(root, Load.newImage("full-screens/win.png"), 0,0);
        } else {
        	draw(root, Load.newImage("full-screens/lose.png"), 0,0);
        }
    }
}
