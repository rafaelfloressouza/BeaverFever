package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import screens.Screen;
import screens.StartScreen;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
	private Screen screen;
	private Stage primaryStage;
	
	//Default width and height will be 1280x800 for now
	private int width = 1280;
	private int height = 800;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			
			//Sets up some window specific things
			primaryStage.setTitle("TITLE");			//Title of window
			primaryStage.setResizable(false); 		//If the window can be resized (no)
			//primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/window-icon.png"))); //Window icon
			
			//Initializes the first game screen, StartScreen
			screen = new StartScreen(width, height);
			
			//Displays the screens output and adds a key handler
			repaint();
			addKeyHandler(screen);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Adds a key handler to the input screen, to take in player input
	 * @param screen: Screen to add the input handler to
	 */
	private void addKeyHandler(Screen screen) {
		screen.scene.setOnKeyPressed(key -> {
			keyPressed(key);
        });
	}
	
	/**
	 * How to handle the event of a keypress
	 * On key press, each screen returns the next screen to display
	 * @param key: the key that has been pressed, as a KeyEvent
	 */
	private void keyPressed(KeyEvent key) {
		Screen nextScreen = screen.userInput(key);
		if (nextScreen == null)
			return;
		screen = nextScreen;
		repaint();
		addKeyHandler(screen);
	}
	
	/**
	 * Repaints the primary stage by initializing, displaying and finalizing the output of the current screen
	 */
	public void repaint() {
		screen.initializeOutput();
		screen.displayOutput();
		screen.finalizeOutput(primaryStage);
	}
}
