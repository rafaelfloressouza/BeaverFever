package items;

import javafx.scene.image.Image;
import tools.Load;

public enum Item {

    // All different types of items (Foods and Money)
    FIVE("$5", 5, 0, Load.newImage("objects/money5.png")),
    TEN("$10", 10, 0, Load.newImage("objects/money10.png")),
    TWENTY("$20", 20, 0, Load.newImage("objects/money20.png")),
    FIFTY("$50", 50, 0, Load.newImage("objects/money50.png")),
    HUNDRED("$100", 100, 0, Load.newImage("objects/money100.png")),
    BACON("Bacon", 0, 3, Load.newImage("objects/bacon.png")),
    POUTINE("Poutine", 0, 5,Load.newImage("objects/poutine.png")),
    SYRUP("Maple Syrup", 0, 10, Load.newImage("objects/maple.png"));

	//name of the item, for player interaction
	private String name;
	public String getName() { return name; }
	
    //value of the money
    private int value;
    public int value() {return value;}

    //value of the food
    private int food;
    public int food() {return food; }

    //image of the money
    private Image image;
    public Image image() {return image;}

    //constructor for money
    private Item(String name, int value, int food, Image image) {
    	this.name = name;
        this.value = value;
        this.food = food;
        this.image = image;
    }

    // Static methods
    /**
     * Returns a random bill type
     */
    public static Item getRandomBillType(){
        double i = Math.random();
        
        if (i < 0.1)		//10% chance
            return FIVE;
        else if (i < 0.3)	//20% chance
            return TEN;
        else if (i < 0.65)	//35% chance
        	return TWENTY;
        else if (i < 0.9)	//25% chance
        	return FIFTY;
        else				//10% chance
            return HUNDRED;
        // Average value for a dropped bill is $32
    }

    /**
     * Returns a random food
     * */
    public static Item getRandomFood(){
        int randNum = (int) (Math.random() * 3);
        if(randNum == 0){
            return BACON;
        }else if(randNum == 1){
            return POUTINE;
        }else{
            return SYRUP;
        }
    }
}
