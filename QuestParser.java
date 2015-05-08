import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuestParser {

	// Instance Variables
	String quests = "";
	Scanner mainFile;
	int[] questArray;
	
	// To Play around with until I combine my blocks of code.
	Scanner copy;
	Scanner copy2;
	
	// Constructor
	public QuestParser(File file) throws FileNotFoundException {
		mainFile = new Scanner(file);
		copy = new Scanner(file);
		copy2 = new Scanner(file);
		quests = findIDs();
		questArray = toArray(quests);
	}

	// Method: "IsFiveNum"
	// Purpose: To verify that the give 5 number "string" is actually 0-9
	//			This is Pre-Integer conversion
	public boolean isFiveNum(String str) {
		boolean result = false;
		int count = 0;
		
		if (str.length() == 5) {
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
		}
		return result;
	}

	// Method: "FindIDs"
	// Purpose: To continue parsing through the mainFile until the next
	//			5 number questID is found, returning the ID.
	public String findIDs() {
		
		String result = "";
		String quest = "";
		
		while(copy.hasNextLine()) {
			quest = copy.nextLine();
			while (quest.indexOf("IsQuestCompleted(") != -1){
				if (isFiveNum(quest.substring(quest.indexOf("IsQuestCompleted(") + 17, quest.indexOf("IsQuestCompleted(") + 22))) {
					result += quest.substring(quest.indexOf("IsQuestCompleted(") + 17, quest.indexOf("IsQuestCompleted(") + 22) + ", ";
					quest = quest.substring(quest.indexOf("IsQuestCompleted(") + 22);
				}
				else {
					quest = quest.substring(quest.indexOf("IsQuestCompleted(") + 22);
				}
			}
		}
		// Checking Next Conditionals
		while(copy2.hasNextLine()){
			// Looks at the first word match on a line
			quest = copy2.nextLine();
			if (quest.indexOf("QuestIds") != -1) {
				quest = copy2.nextLine().replaceAll("\\s+", "");
				// Checking for stop of list of IDs..
				while (quest.indexOf("]") == -1) {
					if (isFiveNum(quest)) {
						result += quest + ", ";
					}
					quest = copy2.nextLine().replaceAll("\\s+", "");
				}
			}
		}
		// Removing the last comma from the list.
		result = result.substring(0, result.length() -2 );
		return result;
	}
	
	public int[] toArray(String IDs) {
		int[] result;
		String copy = IDs;
		Scanner idCount = new Scanner(IDs);
		Scanner idToArray = new Scanner(copy);
		int count = 0;
		while (idCount.hasNext()) {
			idCount.next();
			count++;
		}
		if (count > 0) {
			result = new int[count];
			for (int i = 0; i < count; i++) {
				// Checks for last variable because (-1) is not necessary on last
				if (i < count - 1) {
					String temp = idToArray.next();
					temp = temp.substring(0, temp.length()-1);
					result[i] = Integer.parseInt(temp);
				}
				else {
					String temp = idToArray.next();
					temp = temp.substring(0, temp.length());
					result[i] = Integer.parseInt(temp);
				}
			}
		}
		else {
			result = new int[] {};
		}
		// Avoid resource leak, closing Scanners
		idCount.close();
		idToArray.close();		
		return result;
	}
	
	// Main method to return String of questIDs
	public String getStringOfQuestIDs() {
		return quests;
	}
	// Main method to return Array of questIDs
	public int[] getArrayOfQuestIDs() {
		return questArray;
	}
}
