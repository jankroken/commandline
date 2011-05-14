package commandline.util;

import org.junit.Test;

public class StringUtilTest {
	@Test
	public void testPrintableList() {
		String[] input = new String[]{"hello","world"};
		String expected = "[hello,world]";
		String result = StringUtils.printableList(input);
	}
}
