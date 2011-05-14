package commandline;

import java.util.List;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

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
	
	@Test
	public void testMultipleArgsConfiguration() throws Exception {
		String[] args = new String[]{"--files","hello.txt","world.txt","bye.txt","--logfile","hello.log"};
		MultipleArgsConfiguration config = CommandLineParser.parse(MultipleArgsConfiguration.class, args);
		List<String> files = config.getFiles();
		String logfile = config.getLogfile();
		assertThat(files,hasItems("hello.txt","world.txt","bye.txt"));
		assertThat(logfile,is("hello.log"));
	}
	
	@Test
	public void testDelimerConfiguran() throws Exception {
		String[] args = new String[]{"--exec","ls","-l","*.txt",";","--logfile","hello.log"};
		DelimiterConfiguration config = CommandLineParser.parse(DelimiterConfiguration.class, args);
		List<String> command = config.getCommand();
		String logfile = config.getLogfile();
		assertThat(command,hasItems("ls","-l","*.txt"));
		assertThat(logfile,is("hello.log"));
	}
}
