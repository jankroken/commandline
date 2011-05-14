package commandline;

import commandline.annotations.LongSwitch;
import commandline.annotations.Option;
import commandline.annotations.SingleArgument;
import commandline.annotations.Toggle;

public class AlbumConfiguration {
	
	private String artist;
	private String name;
	private String year;
	private boolean available;

	@Option
	@LongSwitch("--artist")
	@SingleArgument
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Option
	@LongSwitch("--name")
	@SingleArgument
	public void setName(String name) {
		this.name = name;
	}

	@Option
	@LongSwitch("--year")
	@SingleArgument
	public void setYear(String year) {
		this.year = year;
	}
	
	@Option
	@LongSwitch("--available")
	@Toggle(true)
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getArtist() {
		return artist;
	}

	public String getName() {
		return name;
	}

	public String getYear() {
		return year;
	}

	public boolean isAvailable() {
		return available;
	}
}
