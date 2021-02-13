package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class Screen {
	public Scene scene;
	protected Group root;
	protected Stage stage;
	
	/**
	 * The width and the height of the screen
	 * This absolutely needs to be set to be the same the constructor of every screen or else things will get weird
	 */
	protected int width;
	protected int height;
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
	}
	

	/**
	 * How the system reads user key input. The user enters a key and this screen returns the next screen to display
	 * @param key: A KeyEvent of user input
	 * @return The next screen for the system to use
	 */
	public abstract Screen userInput(KeyEvent key);
	
	/**
	 * This is called in between initializing and finalizing output
	 * This is where you put all of the output you want the screen to display
	 */
	public abstract void displayOutput();
	
	
	/**
	 * Initializes this screens output by starting a new private scene
	 * This happens each time the system redraws the screen
	 */
	public void initializeOutput() {
		root = new Group();
	    scene = new Scene(root, width, height, Color.BLACK);
	}
	
	/**
	 * Finalizes this screens output on the input stage
	 * Should not be overridden, called by Main
	 * @param stage: Is feasibly only primaryStage of main for now, allows us to have more stages if needed
	 */
	public void finalizeOutput(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}
	
	//----------------------------Some static methods to display output------------------------------------//
	
	/**
	 * Standard draw, draw an Image to the screen at the designated coordinates
	 * @param root: Screen root that will parent the image
	 * @param i:	Image to draw
	 * @param x:	x coordinate
	 * @param y:	y coordinate
	 */
	public static void draw(Group root, Image i, int x, int y) {
    	draw(root, i, x, y, 0);
    }
	
	/**
	 * A method that loads an image and its position to add it to the children of root
	 * @param root:		The screen root to add the image to
	 * @param i:		The image to draw
	 * @param x:		The x coordinate of the image
	 * @param y:		The y coordinate of the image
	 * @param brightness:	Adjusting the brightness of the image, from -1.0 to 1.0
	 */
	public static void draw(Group root, Image i, int x, int y, double brightness) {
    	ImageView j = new ImageView(i);
    	j.setX(x);
    	j.setY(y);
    	j.setPreserveRatio(true);
    	if (brightness != 0) {
    		ColorAdjust value = new ColorAdjust();
    		value.setBrightness(brightness);
    		j.setEffect(value);
    	}
    	root.getChildren().add(j);
    }
	
	/**
	 * Basic writing method, defaults to white text that is not wrapped
	 * @param root:		Root of the screen
	 * @param s:		The string to write
	 * @param x:		x coordinate
	 * @param y:		y coordinate
	 * @param font:		Font object that the text is in (should be loaded from application/resources)
	 */
	public static void write(Group root, String s, int x, int y, Font font) {
    	write(root, s, x, y, font, Color.WHITE);
    }
	
	/**
	 * Basic writing method, writes text that is not wrapped (wrapping width is 0)
	 * @param root:		Root of the screen
	 * @param s:		The string to write
	 * @param x:		x coordinate
	 * @param y:		y coordinate
	 * @param font:		Font object that the text is in (should be loaded from application/resources)
	 * @param colour:	Text colour
	 */
	public static void write(Group root, String s, int x, int y, Font font, Color colour) {
    	writeWrapped(root, s, x, y, 0, font, colour);
    }
	
	/**
	 * A method to write text to the screen, wrapped by the given width
	 * Works similarly to drawing
	 * @param root:		Root of the screen
	 * @param s:		The string to write
	 * @param x:		x coordinate
	 * @param y:		y coordinate
	 * @param width:	Max width to wrap the text around
	 * @param font:		Font object that the text is in (should be loaded from application/resources)
	 * @param colour:	Text colour
	 */
	public static void writeWrapped(Group root, String s, int x, int y, int width, Font font, Color colour) {
    	Text text = new Text();
    	text.setText(s);
    	text.setX(x);
    	text.setY(y);
    	text.setFont(font);
    	text.setFill(colour);
    	if (width != 0)
    		text.setWrappingWidth(width);
    	root.getChildren().add(text);
    }
	
	
	
}
