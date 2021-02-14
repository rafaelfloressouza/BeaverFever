package items;
import javafx.scene.image.Image;
import tools.Load;

public enum Money {
	FIVE(5, Load.newImage("objects/money5.png")), 
	TWENTY(20, Load.newImage("objects/money20.png")), 
	HUNDRED(100, Load.newImage("objects/money100.png"));

	//value of the money
	private int value;
	public int value() {return value;}
	
	//image of the money
	private Image image;
	public Image image() {return image;}
	
	//constructor for money
	private Money(int value, Image image) {
		this.value = value;
		this.image = image;
	}
}
