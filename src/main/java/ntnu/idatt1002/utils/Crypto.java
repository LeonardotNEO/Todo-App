package ntnu.idatt1002.utils;

import ntnu.idatt1002.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public final class Crypto {
    /**
     * Returns a {@code byte[]} to be passed into the {@link #hashPassword(String, byte[])} method as salt.
     * This needs to be stored in a {@link User} object along with the hashed password for later
     * verification.
     * @return a {@code byte[]} with 16 random bytes.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a {@link String} with the hashed password using the given plaintext and salt.
     * Salt can be generated with the {@link #generateSalt()} method.
     * This method uses PBKDF2.
     * @param password plaintext password.
     * @param salt {@code salt} for a {@link User}.
     * @return a {@link String} containing the hashed password, or {@code null} if an error occured.
     */
    public static String hashPassword(String password, byte[] salt){
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            //Convert byte[] to String
            StringBuilder stringBuilder = new StringBuilder();
            for(byte iByte : hash){
                stringBuilder.append(Integer.toString((iByte & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
