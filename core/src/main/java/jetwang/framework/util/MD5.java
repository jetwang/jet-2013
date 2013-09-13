/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jetwang.framework.util;

import java.security.MessageDigest;

public class MD5 {


    public static String digest(String source) {
        StringBuilder dist = new StringBuilder();
        try {
            byte[] tmp = MessageDigest.getInstance("MD5").digest(source.getBytes());
            for (byte b : tmp) {
                dist.append(String.format("%02X", b));
            }
        } catch (Exception ex) {
            dist.append(String.format("%032d", System.currentTimeMillis()));
        }
        return dist.toString().toLowerCase();
    }
}
