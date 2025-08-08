package exceptions;

/**
 * Custom exception for application-specific errors
 */
public class CustomException extends RuntimeException {

    /**
     *
     * @param message
     */
    public CustomException(String message){
        super(message);
    }
}
