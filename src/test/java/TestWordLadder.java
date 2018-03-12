import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Set;

public class TestWordLadder {
	WordLadder wordLadder;
	
	@Before
	public void setUp() throws Exception {
		wordLadder = new WordLadder();
	}

	@Test
	public void testSetUpDic() {
		Set<String> dic = wordLadder.setUpDic("res/dictionary.txt");
		assertTrue(dic.contains("abacuses"));
		assertTrue(dic.contains("egotise"));
		assertTrue(dic.contains("lithospheric"));
		assertTrue(dic.contains("rightsizes"));
	}

}
