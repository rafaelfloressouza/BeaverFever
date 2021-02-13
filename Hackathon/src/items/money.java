package items;
import javafx.scene.image.Image;

public class money {
	
	//value of the money
	private int value;
	public int value() {return value;}
	
	//image of the money
	private Image image;
	public Image image() {return image;}
	
	
	public money (int value, Image image) {
		this.value = value;
		this.image = image;
	}
	
}
