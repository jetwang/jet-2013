package jetwang.framework.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RSAManager {
    private KeyPair keyPair;

    public RSAManager(String rsakeyPath) {
        keyPair = getKeyPair(rsakeyPath);
    }

    public KeyPair getKeyPair(String rsakeyPath) {
        try {
            //产生新密钥对
            //InputStream is = FileUtils.class.getResourceAsStream(rsakeyPath);
            ObjectInputStream oos = new ObjectInputStream(RSAManager.class.getResourceAsStream("/RSAKey.xml"));
            KeyPair kp = (KeyPair) oos.readObject();
            oos.close();
            return kp;
        } catch (Exception e) {
            throw new SecurityException("couldn't get the key pair", e);
        }
    }

    public void saveRSAKey() throws NoSuchAlgorithmException, IOException {
        SecureRandom sr = new SecureRandom();
        KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
        kg.initialize(1024, sr);
        FileOutputStream fos = new FileOutputStream("C:/RSAKey.xml");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(kg.generateKeyPair());
        oos.close();
    }

    public String encrypt(String unencryptedString) {
        if (unencryptedString == null || unencryptedString.trim().length() == 0)
            throw new IllegalArgumentException(
                    "unencrypted string was null or empty");

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] cleartext = unencryptedString.getBytes(CoreConstants.UTF_8);
            byte[] ciphertext = cipher.doFinal(cleartext);

            return new String(Base64.encodeBase64(ciphertext), CoreConstants.UTF_8);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    public String decrypt(String encryptedString) {
        if (encryptedString == null || encryptedString.trim().length() <= 0)
            throw new IllegalArgumentException(
                    "encrypted string was null or empty");
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] cleartext = Base64.decodeBase64(encryptedString.getBytes(CoreConstants.UTF_8));
            byte[] ciphertext = cipher.doFinal(cleartext);
            return new String(ciphertext, CoreConstants.UTF_8);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}
