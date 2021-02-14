package items;
import javafx.scene.image.Image;

public class Money {

	// Bill Types
	public static enum Bill_Type {FIVE, TWENTY, HUNDRED};

	//value of the money
	private int value;
	public int value() {return value;}
	
	//image of the money
	private Image image;
	public Image image() {return image;}
	
	//constructor for money
	public Money (int value, Image image) {
		this.value = value;
		this.image = image;
	}
}
