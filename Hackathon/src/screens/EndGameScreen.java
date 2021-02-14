package screens;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.Load;

public class EndGameScreen extends Screen{

    private String gameOverMsg, winMessage, loseMessage;
    private Font font;
    private Boolean type;

    public EndGameScreen(int width, int height, Boolean type){
        super(width, height);
        this.gameOverMsg =  "Game Over ";
        this.winMessage = "Congrats, you won! ";
        this.loseMessage = "Oops, you lose!";
        this.type = type;
        font = Load.newFont("SDS_8x8.ttf", 16);
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

        String actualMsg = "";
        if(type){
            actualMsg = gameOverMsg;
        }else if(type){
            actualMsg = winMessage;
        }else if(!type){
            actualMsg = loseMessage;
        }

        writeCentered(root, actualMsg, width, height, font, Color.WHITE);
    }
}
