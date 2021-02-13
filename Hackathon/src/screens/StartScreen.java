package screens;

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
		
		char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	
    	if (code.equals(KeyCode.ENTER))
    		s += "\n";
    	else if (code.equals(KeyCode.BACK_SPACE)) {
    		if (s.length() > 0)
    			s = s.substring(0, s.length() - 1);
    	}
    	
    	else {
    		s += c;
    	}
    	
		return this;
	}

	@Override
	public void displayOutput() {
		writeWrapped(root, s, 16, 32, width - 32, f, Color.WHITE);
	}

}
