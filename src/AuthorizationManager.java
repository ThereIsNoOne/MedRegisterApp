import java.io.IOException;
import  java.security.*;
import java.sql.SQLException;

/**
 * Authorization manager class, responsible for authorizing and register user.
 */
public class AuthorizationManager {

    private static final String salt = "salt";

    private final MessageDigest msgDigest;
    private final DatabaseConnector dbConnector;

    /**
     * Constructs a new AuthorizationManager.
     * @throws NoSuchAlgorithmException  Thrown when there is no algorithm
     * @throws IOException Thrown when it is not possible to connect to the database
     */
    AuthorizationManager() throws NoSuchAlgorithmException, IOException {
        this.msgDigest = MessageDigest.getInstance("MD5");
        this.dbConnector = new DatabaseConnector();
    }

    /**
     * Register new user with given password and login.
     * @param login login of the new user
     * @param password password of the new user
     * @throws SQLException Thrown when it is unable to connect to database, or it is not possible to register new user
     * @throws IllegalArgumentException Thrown when login is already used
     */
    public void registerUser(String login, String password) throws SQLException, IllegalArgumentException {
        if (dbConnector.validateRegistration(login)) {
            byte[] bytes = msgDigest.digest((password + salt).getBytes());
            String hash = bytesToHex(bytes);
            dbConnector.registerUser(login, hash);
        }
        else {
            throw new IllegalArgumentException("login already used!");
        }
    }

    /**
     * Authorizes user by comparing hash from the database and hashed password provided by the user.
     * @param login User's login
     * @param password Password provided by the user
     * @return True if the user is authorized, otherwise false
     * @throws AuthorizationException When user is not authorized due to database malfunction
     */
    public boolean authorizeUser (String login, String password) throws AuthorizationException {
        byte[] bytes = msgDigest.digest((password + salt).getBytes());
        String hash = bytesToHex(bytes);
        String hashToCompare;
        try {
            hashToCompare = dbConnector.getHash(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (hash.equals(hashToCompare)) {
            System.out.println("Authorized"); // DEBUG statement
        }
        else {
            System.out.println("Not authorized!");
        }
        return hash.equals(hashToCompare);
    }

    /**
     * Change bytes to hex values.
     * @param bytes Bytes to translate
     * @return String representation of hex values
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
