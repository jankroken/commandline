package commandline.domain;

public class UnrecognizedSwitchException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String _switch;

	public UnrecognizedSwitchException(Class<? extends Object> configurationClass, String _switch) {
		super(""+configurationClass+": "+_switch);
	}
	
	public String getSwitch() {
		return _switch;
	}
}
