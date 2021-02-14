package characters;

import javafx.scene.paint.Color;

public class Message {

	//Instance variables for Messages
	private String text;
	private Color color;
	
	/**
	 * Constructor for the Message
	 * @param text:    Message to be displayed
	 * @param color:   Color of the text in the message
	 **/
	public Message (String text, Color color) {
		this.text = text;
		this.color = color;
	}
	
	//return the string associated with the message
	public String text () {return text;}
	
	//return the color associated with the message
	public Color color() {return color;}
}
