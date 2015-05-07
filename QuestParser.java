import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class QuestParser {

	// Instance Variables
	ArrayList<Integer> questArray = new ArrayList<Integer>();
	String quests = "";
	Scanner mainFile;
	
	// To Play around with until I combine my blocks of code.
	Scanner copy;
	Scanner copy2;
	
	// Constructor
	public QuestParser(File file) throws FileNotFoundException {
		mainFile = new Scanner(file);
		copy = new Scanner(file);
		copy2 = new Scanner(file);
	}

	// Method: "IsFiveNum"
	// Purpose: To verify that the give 5 number "string" is actuall 0-9
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
		return result;
		
	}
	/* TO DO LIST...
|  
|	- Write method that searches for 5 digit questID instead of identifiers (more prone to bugs, but easier)
|	- Add User Input or File Selection
|	- Convert String into Array
|	- Covert this concept into a C# Class for helping build that returns a finished Array 
|	- (mainly did it in Java to stay fresh)
|	- Still need to implement repeat quest filtering.
|
*/

/*PrintWriter output = new PrintWriter(new FileWriter("Quest Array.txt"));
output.println(quests);
output.close();*/

}
