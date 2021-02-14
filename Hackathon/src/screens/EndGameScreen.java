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
        Screen screen = this;

        if(type){ // In game screen
            if (code.equals(KeyCode.ESCAPE)) {       // If space is pressed, go to start screen
                System.exit(0);
            } else if (code.equals(KeyCode.SPACE)) { // If escape is pressed exit the program
                screen = new StartScreen(width, height);
            }
        }else{ // In victory screen
            if(code.equals(KeyCode.SPACE))          // If space is pressed, go to game over screen
                screen = new EndGameScreen(this.width, this.height, true);
        }

        return screen;
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
