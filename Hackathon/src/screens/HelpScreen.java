package screens;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.Load;

public class HelpScreen extends Screen {
	private Font font;
	
	
	public HelpScreen (int width, int height) {
		super(width, height);
		font = Load.newFont("SDS_8x8.ttf", 20);
	}

	@Override
	public Screen userInput(KeyEvent key) {
		
		return null;
	}

	@Override
	public void displayOutput() {
		String actualMsg = "Welcome to Beaver Fever, where you play as a beaver to collect as much money as possible while fighting off real Canadian dangers. "
				+ "When we get a little hungry fighting everything off, you can replenish yourself by treating yourself to Canada's national treats, such as poutine. bacon, and maple syrup. \n"
				+ "After we get enough money, we just might be able to buy ourselves a nice double-double from Tim's and call it a day!\n"
				+ "The controls are simple just use the arrows to move around and collect the money and food. To fight off your enemies just lightspeed tackle them.\n"
				+ "To Return to the main menu, hit ?????";
		writeWrapped(root, actualMsg, width/10, height/3, 1000, font, Color.GHOSTWHITE);
	}
	
}
