package eu.senla.myfirstapp.app.exception;

public class IncorrectValueException extends RuntimeException {
    public IncorrectValueException(String message) {
        super(message);
    }

    public IncorrectValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectValueException(Throwable cause) {
        super(cause);
    }
}
