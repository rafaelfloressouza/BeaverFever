package characters;

import java.util.ArrayList;

public class GetMessages {
	
	private ArrayList<Message> messagesLog;
	
	public GetMessages() {
		this.messagesLog = new ArrayList<Message>();
	}
	
	//add a new message to the message log - limit to 10
	public void add (Message message) {
		if (messagesLog.size() >= 10) {
			messagesLog.remove(0);
			messagesLog.add(message);
		}
		else {
			Messages.add(message);
		}
	}
	
	//display the message - the most recent 10 messages
	public void getMessageList() {
		for (Message i: messagesLog) {
			System.out.println(i.getMessage());
		}
	}
	
}
