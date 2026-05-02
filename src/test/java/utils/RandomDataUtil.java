package utils;

import java.util.Random;

public class RandomDataUtil {

    private static final Random RANDOM = new Random();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String randomEmail() {
        return "test_" + randomString(8) + "@mailinator.com";
    }

    public static String randomPassword() {
        return "Pass_" + randomString(6) + RANDOM.nextInt(999);
    }

    private static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private RandomDataUtil() {}
}