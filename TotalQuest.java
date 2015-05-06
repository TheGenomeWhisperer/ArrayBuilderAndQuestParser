import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class TotalQuest {
	
	public static void main(String[] args) throws IOException 
	{
		// Method: "findAllQuests"
		// Finds the next quest and builds questArray with it.
		
		String quests = "";
		ArrayList<Integer> questArray = new ArrayList<Integer>();

		Scanner txt = new Scanner(new File("D:\\test.txt"));

		while (txt.hasNextLine())
		{
			// Looks at the first characters before white space
			String quest = txt.next();
			// Verifying at least 6 lines since all quests are 6 numbers.
			if (quest.length() > 4)
			{
				// Ensuring no out-of-bound errors.
				for (int i = 0; i < quest.length() - 5; i++)
				{
					int count = 0;
					// Iterating through next 6 characters.
					for (int j = 0; j < 5; j++)
					{
						// Verifying it is a number 0-9
						if (quest.charAt(j) >= 48 && quest.charAt(j) <=57)
						{
							count++;
						}
						if (count == 5)
						{
							questArray.add(Integer.parseInt(quest.substring(0, 5)));
							quests += (quest.substring(0, 5) + ", ");
						}
					}
				}
			}
		}
		txt.close();
		
		// Removing the last comma from the list.
		quests = quests.substring(0, quests.length() -2 );
		
		// Printing Results
		for (Integer i:questArray)
		{
			System.out.println(i);
		}
		System.out.println(quests);
		System.out.println("Test successful");
	}
}

/*PrintWriter output = new PrintWriter(new FileWriter("Quest Array.txt"));
output.println(quests);
output.close();*/

/* TO DO LIST...
|  
|	- Add User Input or File Selection
|	- Convert ArrayList into normal Array
|	- Covert this concept into a C# Class for helping build that returns a finished Array 
|	- (mainly did it in Java to stay fresh)
|	- Still need to implement repeat quest filtering.
|
*/
