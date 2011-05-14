package commandline.domain;

public class InvalidCommandLineException extends RuntimeException {
	public InvalidCommandLineException(String message) {
		super(message);
	}
}
