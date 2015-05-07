import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;


public class QuestParserTest {

	@Test
	public void findNextIDTest1() throws FileNotFoundException {
		QuestParser test = new QuestParser(new File("D:\\test.txt"));
		System.out.println(test.findNextID());

	}

}
