import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class QuestIDParserMain {

	public static void main(String[] args) throws IOException {
		
		// Getting user input
		Scanner keyboard = new Scanner(System.in);
		System.out.println("An Example Entry: D:\\My Programs\\test.txt");
		System.out.print("Please enter the file name: ");
		String file = keyboard.nextLine();
		keyboard.close();
		
		// Initiating parsing
		try {
			QuestParser QP = new QuestParser(new File(file));
			QP.toFile();
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to find File. Are you sure the directory and filename is correct?");
		}
	}
}
