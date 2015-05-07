import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuestParser {

	// Instance Variables
	ArrayList<Integer> questArray = new ArrayList<Integer>();
	String quests = "";
	Scanner mainFile;
	
	// Constructor
	public QuestParser(File file) throws FileNotFoundException {
		mainFile = new Scanner(file);
	}

	// Method: "FindNextID"
	// Purpose: To continue parsing through the mainFile until the next
	//			5 number questID is found, returning the ID.
	public String findNextID() {
		
		String result = "";
		while(mainFile.hasNextLine()){
			// Looks at the first word match on a line
			String quest = mainFile.nextLine();
			if (quest.indexOf("QuestIds") > -1) {
				quest = mainFile.nextLine().replaceAll("\\s+", "");
				// Checking for stop of list of IDs..
				while (quest.indexOf("]") == -1) {
					if (quest.length() == 5 && isFiveNum(quest)) {
						result += quest + ", ";
					}
					quest = mainFile.nextLine().replaceAll("\\s+", "");
				}
			}
		}
		return result;
	}
	
	public boolean isFiveNum(String str) {
		boolean result = false;
		int count = 0;
		// Iterating through next 5 characters.
		for (int i = 0; i < 5; i++) {
			// Verifying it is a number 0-9
			if (str.charAt(i) >= 48 && str.charAt(i) <=57) {
				count++;
			}
			if (count == 5) {
				result = true;
			}
		}
		return result;
	}
}
