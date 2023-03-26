import java.io.IOException;
import  java.security.*;
import java.sql.SQLException;

public class AuthorizationManager {

    private static final String salt = "salt";

    private final MessageDigest msgDigest;
    private final DatabaseConnector dbConnector;

    AuthorizationManager() throws NoSuchAlgorithmException, IOException {
        this.msgDigest = MessageDigest.getInstance("MD5");
        this.dbConnector = new DatabaseConnector();
    }

    public void registerUser(String login, String password) throws SQLException {
        if (dbConnector.validateRegistration(login)) {
            byte[] bytes = msgDigest.digest((password + salt).getBytes());
            String hash = bytesToHex(bytes);
            dbConnector.registerUser(login, hash);
        }
        else {
            throw new IllegalArgumentException("login already used!");
        }
    }

    public boolean authorizeUser (String login, String password) {
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

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
