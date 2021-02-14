package items;

import javafx.scene.image.Image;
import tools.Load;

public enum Item {

    FIVE(5, 0, Load.newImage("objects/money5.png")),
    TWENTY(20, 0, Load.newImage("objects/money20.png")),
    HUNDRED(100, 0, Load.newImage("objects/money100.png")),
    BACON(0, 3, Load.newImage("objects/maple.png")),
    POUTINE(0, 5,Load.newImage("objects/maple.png")),
    SYRUP(0, 10, Load.newImage("objects/maple.png"));

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
    private Item(int value, int food, Image image) {
        this.value = value;
        this.food = food;
        this.image = image;
    }

    // Static methods

    /**
     * Returns a random bill type
     */
    public static Item getRandomBillType(){
        int randNum = (int)(Math.random() * 3);

        if (randNum == 0){
            return FIVE;
        } else if (randNum == 1){
            return TWENTY;
        } else {
            return HUNDRED;
        }
    }

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
