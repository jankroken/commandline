package commandline;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandLineParserTest {

	@Test
	public void testSimpleConfiguration() throws Exception {
		String[] args = new String[]{"-f","hello.txt","-v"};
		SimpleConfiguration simpleConfiguration = CommandLineParser.parse(SimpleConfiguration.class, args);
		String filename = simpleConfiguration.getFilename();
		boolean verbose = simpleConfiguration.getVerbose();
		assertEquals("hello.txt",filename);
		assertTrue(verbose);
	}
}
