package exceptions;

/**
 * Created by Mikhail on 19.11.2015.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
