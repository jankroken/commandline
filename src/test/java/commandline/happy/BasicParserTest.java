package commandline.happy;

import java.util.List;

import org.junit.Test;

import commandline.CommandLineParser;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class BasicParserTest {

	@Test
	public void testSimpleConfiguration() throws Exception {
		String[] args = new String[]{"-f","hello.txt","-v"};
		SimpleConfiguration config = CommandLineParser.parse(SimpleConfiguration.class, args);
		assertThat(config.getFilename(),is("hello.txt"));
		assertThat(config.getVerbose(),is(true));
	}
	
	@Test
	public void testMultipleArgsConfiguration() throws Exception {
		String[] args = new String[]{"--files","hello.txt","world.txt","bye.txt","--logfile","hello.log"};
		MultipleArgsConfiguration config = CommandLineParser.parse(MultipleArgsConfiguration.class, args);
		assertThat(config.getFiles(),hasItems("hello.txt","world.txt","bye.txt"));
		assertThat(config.getLogfile(),is("hello.log"));
	}
	
	@Test
	public void testDelimiterConfiguran() throws Exception {
		String[] args = new String[]{"--exec","ls","-l","*.txt",";","--logfile","hello.log"};
		DelimiterConfiguration config = CommandLineParser.parse(DelimiterConfiguration.class, args);
		assertThat(config.getCommand(),hasItems("ls","-l","*.txt"));
		assertThat(config.getLogfile(),is("hello.log"));
	}

	@Test
	public void testMultipleConfiguration() throws Exception {
		String[] args = new String[]{"--verbose","--file","hello.txt","--file","world.txt"};
		MultipleConfiguration config = CommandLineParser.parse(MultipleConfiguration.class, args);
		assertThat(config.getFiles(),hasItems("hello.txt","world.txt"));
		assertThat(config.getVerbose(),is(true));
	}
	
	@Test
	public void testSubConfiguration() throws Exception {
		String[] args = new String[]{"--verbose","--album","--name","Caustic Grip","--artist","Front Line Assembly","--year","1990","--available","--logfile","hello.log"};
		SimpleSuperConfiguration config = CommandLineParser.parse(SimpleSuperConfiguration.class, args);
		AlbumConfiguration album = config.getAlbum();
		assertThat(config.getLogfile(),is("hello.log"));
		assertThat(config.getVerbose(),is(true));
		assertThat(album.getName(),is("Caustic Grip"));
		assertThat(album.getArtist(),is("Front Line Assembly"));
		assertThat(album.getYear(),is("1990"));
		assertThat(album.isAvailable(),is(true));
	}

	@Test
	public void testMultipleSubConfiguran() throws Exception {
		String[] args = new String[]{"--verbose",
									 "--album","--name","Caustic Grip","--artist","Front Line Assembly","--year","1990","--available",
									 "--album","--name","Scintilla","--artist","Stendeck","--year","2011","--available",
									 "--logfile","hello.log"};
		MultipleSubconfigsConfiguration config = CommandLineParser.parse(MultipleSubconfigsConfiguration.class, args);
		boolean verbose = config.getVerbose();
		List<AlbumConfiguration> albums = config.getAlbums();
		assertThat(albums.size(),is(2));
		AlbumConfiguration causticGrip = albums.get(0);
		AlbumConfiguration scintilla = albums.get(1);
		assertThat(causticGrip.getName(),is("Caustic Grip"));
		assertThat(causticGrip.getArtist(),is("Front Line Assembly"));
		assertThat(causticGrip.getYear(),is("1990"));
		assertThat(causticGrip.isAvailable(),is(true));
		assertThat(scintilla.getName(),is("Scintilla"));
		assertThat(scintilla.getArtist(),is("Stendeck"));
		assertThat(scintilla.getYear(),is("2011"));
		assertThat(scintilla.isAvailable(),is(true));
		
		assertThat(config.getLogfile(),is("hello.log"));
		assertThat(verbose,is(true));
	}
	
	@Test
	public void testLooseArguments() throws Exception {
		String[] args = new String[]{"--verbose","zombies","ate","my","--logfile","hello.log","raptors"};
		LooseArgsConfiguration config = CommandLineParser.parse(LooseArgsConfiguration.class, args);
		assertThat(config.getVerbose(),is(true));
		assertThat(config.getLogfile(),is("hello.log"));
		assertThat(config.getArgs(),hasItems("zombies","ate","my","raptors"));
	}	
	
}
