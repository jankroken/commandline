package commandline.domain;

public class InvalidOptionConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidOptionConfigurationException(String message) {
        super(message);
    }
}
