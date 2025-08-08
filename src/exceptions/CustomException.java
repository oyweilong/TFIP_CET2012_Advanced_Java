package exceptions;

/**
 * Custom Exception for application-specific errors
 */
public class CustomException extends RuntimeException {

    /**
     * Method for displaying Custom Exception message
     * @param message specific messages to each Custom Exception
     */
    public CustomException(String message){
        super(message);
    }
}
