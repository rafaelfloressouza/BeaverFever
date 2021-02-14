package characters;

import javafx.scene.paint.Color;

public class Message {
	//Instance variables for Messages
	private String message = "";
	private Color color;
	
	//constructor for the message
	public Message (String message, Color color2) {
		this.message = message;
		this.color = color2;
	}
	
	//return the string associated with the message
	public String getMessage () {return message;}
	
	//return the color associated with the message
	public Color getColor() {return color;}
}
