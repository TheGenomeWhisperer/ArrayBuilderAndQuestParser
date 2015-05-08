/* Author: 	Sklug (aka The GenomeWhisperer)
 * 
 * Profile:	github.com/TheGenomeWhisperer/
 * Repository:  ArrayBuilderAndQuestParser
 * 
 * Purpose: 	In Conjunction with the "Rebot" Warcraft program (Rebot.to)
 * 		the questing profiles can be quite tedious to keep track of all
 * 		of the added quest IDs, so I basically created a way to take 
 * 		a quest profile exported as a .txt of any size, parse through it
 * 		and collect all the IDs. For quality of life, it exports
 * 		the data into an easily copyable format to paste straight into
 * 		the official Rebot editor in C# format for quest progress checking 			
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class QuestParser {

	// Instance Variables
	String quests = "";
	int[] questArray;
	
	// Constructor
	// In order:	Opens Scanners >> Parses data and puts all questIDs into string >>
	//      	>> removes duplicate IDs >> builds an array from the string >> Establishes
	//		global String and global Array to have parsed final product.
	public QuestParser(File file) throws FileNotFoundException {
		Scanner copy = new Scanner(file);
		Scanner copy2 = new Scanner(file);
		quests = removeDuplicates(findIDs(copy,copy2));
		questArray = toArray(quests);
	}

	// Method: 	"IsFiveNum"
	// Purpose	 To verify that the give 5 number "string" is actually 0-9
	//		which is just a basic boolean quality control check against input errors.
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

	// Method: 	"FindIDs"
	// Purpose: 	To continue parsing through the mainFile until the next
	//		5 number questID is found, returning the ID.
	public String findIDs(Scanner copy, Scanner copy2) {
		
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
	
	// Method: 	"RemoveDuplicates"
	// Purpose: 	In parsing and pulling all instances of quest IDs, many repeat quests
	// 		are called due to the nature of the Questing Profiles referencing the IDs multiple times.
	//		I COULD use a "LinkedHashSet" to auto remove duplicates in an array,
	//      	but in regards to me making this a learning exercise, I will manually do it.
	public String removeDuplicates(String raw) {
		String result = "";
		Scanner remove = new Scanner(raw);
		while (remove.hasNext()) {
			String temp = remove.next();
			if (!result.contains(temp)) {
				result += temp + " ";
			}
		}
		remove.close();
		// Removing the last comma from the list.
		result = result.substring(0, result.length() -2 );
		return result;
	}
	
	// Method: 	"ToArray"
	// Purpose: 	Strings are nice to work with, but Arrays can also be useful.
	//		This is so this Class can present two global variables
	//		but in different formats, an array and a string. This just converts
	//		the string into an array.
	public int[] toArray(String IDs) {
		int[] result;
		String copy = IDs;
		Scanner idCount = new Scanner(IDs);
		Scanner idToArray = new Scanner(copy);
		int count = 0;
		// Determining how many items there are to establish array size.
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
		
	// Method 	"GetStringOfQuestIDs"
	// Purpose	 Main method to return final result
	public String getStringOfQuestIDs() {
		return quests;
	}
	
	// Method	"GetArrayOfQuestIDs"
	// Purpose: 	Main method to return Array of questIDs
	public int[] getArrayOfQuestIDs() {
		return questArray;
	}
	
	// Method: 	"ToFile"
	// Purpose: 	To output to a .txt file the string, and the array in a
	// 		format ready to copy into the ReBot Editor, or for other uses.
	//		yes, I COULD do a long string with \n for line change, but this
	//		is just easier to read, in my opinion.
	public void toFile() throws IOException {
		PrintWriter output = new PrintWriter(new FileWriter("Quest_Array_" + questArray.length + "_quests.txt"));
		quests = quests.replaceAll("\\s+", "");
		output.println("The Following Block of Code is designed to be 100% copy and pasted.");
		output.println("After having parsed the script and compiled all the quests of the profile,");
		output.println("all you need to do is now copy and paste this block of code into the");
		output.println("Rebot editor's \"Run C#\" Script tool.  Then, when placing that block of code");
		output.println("at the start of any questing profile that you just parsed, it will provide");
		output.println("a useful, quality of life, profile progress report!");
		output.println();
		output.println("Block of Code Begins Here:");
		output.println();
		output.println("int[] questArray = {" + quests + "};");
		output.println("int count = 0;");
		output.println("for (int i = 0; i < questArray.Length; i++)");
		output.println("{");
		output.println("\tif (!IsQuestCompleted(questArray[i]))");
		output.println("\t{");
		output.println("\t\tcount++;");
		output.println("\t}");
		output.println("}");
		output.println("Print(\"You Have \" + count + \" quests left to complete in this questpack!\");");
		output.println();
		output.println("End Block of Code");
		output.println();
		output.println("QUEST ID ARRAY");
		output.println("(For Ease to copy and paste into a column, if needed)");
		output.println();

		for(int i = 0; i < questArray.length; i++)
		{
			output.println(questArray[i]);
		}
		output.close();
	}
}
