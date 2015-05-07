import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;


public class QuestParserTest {


	@Test
	public void testIsFiveNum() throws FileNotFoundException {
		QuestParser test = new QuestParser(new File("D:\\test.txt"));
		assertEquals(true, test.isFiveNum("12345"));
		assertEquals(true, test.isFiveNum("00000"));
		assertEquals(false, test.isFiveNum("123456"));
		assertEquals(false, test.isFiveNum("12A3b4"));
		assertEquals(false, test.isFiveNum("125aa"));
	}

	@Test
	public void testFindNextID() throws FileNotFoundException {
		QuestParser test = new QuestParser(new File("D:\\test.txt"));
		System.out.println(test.findIDs());
	}
}
