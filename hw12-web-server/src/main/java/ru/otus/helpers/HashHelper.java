package ru.otus.helpers;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class HashHelper {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final byte[] salt = new byte[16];

    static {
        var random = new SecureRandom();
        random.nextBytes(salt);
    }

    private HashHelper() {
    }

    public static String hash(String password) {
        var spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            var factory = SecretKeyFactory.getInstance(ALGORITHM);
            var hash = factory.generateSecret(spec).getEncoded();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Missing algorithm: " + ALGORITHM, e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("Invalid SecretKeyFactory", e);
        }
    }
}
