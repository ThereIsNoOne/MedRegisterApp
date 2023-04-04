public class AuthorizationException extends Exception {
    public AuthorizationException(String s) {
        super(s);
    }

    public AuthorizationException(Exception e) {
        super(e);
    }
}
