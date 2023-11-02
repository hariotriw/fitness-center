package dev.wowovan.fitness.center.global;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

public class GlobalFunction {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMBER = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    
    public static Timestamp defaultTimestamp(){
        return Timestamp.valueOf("1900-01-01 00:00:00");
    }

    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(NUMBER.length());
            char randomChar = NUMBER.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static String maskString(String strText, int start, int end, char maskChar) {

		if (strText == null || strText.equals(""))
			return "";

		if (start < 0)
			start = 0;

		if (end > strText.length())
			end = strText.length();

		int maskLength = end - start;

		if (maskLength == 0)
			return strText;

		StringBuilder sbMaskString = new StringBuilder(maskLength);

		for (int i = 0; i < maskLength; i++) {
			sbMaskString.append(maskChar);
		}

		return strText.substring(0, start) + sbMaskString.toString() + strText.substring(start + maskLength);
	}

    public static String encryptData(String data){
        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
        return new String(encodedBytes);
    }

    public static String decryptData(String data){
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes);
    }

    public static String hashPassword(String data){
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(data.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    } 
}
