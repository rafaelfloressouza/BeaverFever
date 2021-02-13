package screens;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.Load;

public class StartScreen extends Screen {
	private String s = "";
	private Font f;
	
	public StartScreen(int width, int height) {
		super(width,height);
		//f = Load.newFont("DejaVuSansMono.ttf", 16);
		f = Load.newFont("SDS_8x8.ttf", 16);	//Uncomment for a more blocky font
	}

	@Override
	public Screen userInput(KeyEvent key) {

		KeyCode code = key.getCode();
		Screen s = this;
		
		if(code.equals(KeyCode.S)){
			s = new MainScreen(this.width, this.height);
		}else if(code.equals(KeyCode.ESCAPE)){
			System.exit(0);
		}

		return s;
	}

	@Override
	public void displayOutput() {
		draw(root, Load.newImage("players/t.jpg"),0, 0);
	}
}
