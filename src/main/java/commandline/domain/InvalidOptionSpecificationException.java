package commandline.domain;

public class InvalidOptionSpecificationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOptionSpecificationException(String message) {
		super(message);
	}
}
