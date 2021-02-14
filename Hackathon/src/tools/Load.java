package tools;

import application.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * A class to easily load images and fonts from the resources folder in the application package
 */
public final class Load {
	
	/**
	 * Really basic image loader, loads the image straight from file without cropping
	 * @param name:	Path to the image
	 * @return:		Image
	 */
	public static Image newImage(String name) {
		return new Image(Main.class.getResourceAsStream("resources/" + name));
	}
	
	/**
	 * Loads an image by specified path, includes cropping
	 * @param name:		path to the image, starting at application/resources/
	 * @param startx:	start x coordinate by pixel 
	 * @param starty:	start y coordinate by pixel
	 * @param width:	width of the resulting image
	 * @param height:	height of the resulting image
	 * @return:			Image
	 */
	public static Image newImage(String name, int startx, int starty, int width, int height) {
		Image i = new Image(Main.class.getResourceAsStream("resources/" + name)); 
		return cropImage(i, startx, starty, width, height);
	}
	
	/**
	 * Loads the requested font object
	 * @param path:		Path starting at application/resources/fonts/
	 * @param size:		Size of the font
	 * @return:			Font Object
	 */
	public static Font newFont(String path, int size) {
		return Font.loadFont(Main.class.getResourceAsStream("resources/fonts/" + path), size);
	}
	
	/**
	 * Crops an input image by the specified rectangle
	 * @param image:	input image to crop
	 * @param startX:	start x coordinate
	 * @param startY:	start y coordinate
	 * @param width:	width of the resulting image
	 * @param height:	height of the resulting image
	 * @return:			The new cropped image
	 */
	public static Image cropImage(Image image, int startX, int startY, int width, int height) {
		PixelReader pr = image.getPixelReader();
		if (pr != null) {
			WritableImage write = new WritableImage(pr, startX, startY, width, height);
			return write;
		} else {
			ImageView view = new ImageView(image);
			view.setClip(new Rectangle(startX, startY, width, height));
			return view.getImage();
		}
	}
}
