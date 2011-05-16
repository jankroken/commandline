package commandline.domain;

public class UnrecognizedSwitchException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String _switch;

	public UnrecognizedSwitchException(String message, String _switch) {
		super(message);
	}
	
	public String getSwitch() {
		return _switch;
	}
}
