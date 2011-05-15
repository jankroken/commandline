package commandline.util;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class StringUtilTest {
	@Test
	public void testPrintableList() {
		String[] input = new String[]{"hello","world"};
		assertThat(StringUtils.printableList(input),is("[hello,world]"));
	}
}
