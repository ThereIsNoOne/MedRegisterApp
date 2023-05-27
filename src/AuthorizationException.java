/**
 * Authorization exception, thrown when authorization fails.
 */
public class AuthorizationException extends Exception {

    /**
     * Create a new AuthorizationException.
     * @param e cause exception
     */
    public AuthorizationException(Exception e) {
        super(e);
    }
}
