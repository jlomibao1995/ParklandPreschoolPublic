package services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * Password utility for salting and hashing 
 * @author Aaron 
 */
public class PasswordUtil {

    /**
     * Hashes password received
     * @param password password to has
     * @return hashed password
     * @throws NoSuchAlgorithmException thrown when algorithm for hashing not found
     */
    public static String hashPassword(String password)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);

        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }

            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    /**
     * Generates random string of characters 
     * @return salt
     */
    public static String getSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * Generates the hash of the salt and the password entered
     * @param password password
     * @param salt salt
     * @return hashed password and salt
     * @throws NoSuchAlgorithmException thrown when algorithm for hashing not found
     */
    public static String hashAndSaltPassword(String password, String salt) throws NoSuchAlgorithmException {
        return hashPassword(password + salt);
    }
}
