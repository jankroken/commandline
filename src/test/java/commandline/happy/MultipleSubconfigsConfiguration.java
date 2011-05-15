package commandline.happy;

import java.util.List;

import commandline.annotations.LongSwitch;
import commandline.annotations.Multiple;
import commandline.annotations.Option;
import commandline.annotations.ShortSwitch;
import commandline.annotations.SingleArgument;
import commandline.annotations.SubConfiguration;
import commandline.annotations.Toggle;

public class MultipleSubconfigsConfiguration {
	
	private String logfile;
	private boolean verbose;
	private List<AlbumConfiguration> albums;

	
	@Option
	@LongSwitch("--logfile")
	@ShortSwitch("-l")
	@SingleArgument
	public void setFilename(String logfile) {
		this.logfile = logfile;
	}
	
	@Option
	@LongSwitch("--verbose")
	@ShortSwitch("-v")
	@Toggle(true)
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	@Option
	@LongSwitch("--album")
	@ShortSwitch("-a")
	@Multiple
	@SubConfiguration(AlbumConfiguration.class)
	public void setAlbum(List<AlbumConfiguration> albums) {
		this.albums = albums;
	}

	public boolean getVerbose() {
		return verbose;
	}

	public String getLogfile() {
		return logfile;
	}
	
	public List<AlbumConfiguration> getAlbums() {
		return albums;
	}
}
