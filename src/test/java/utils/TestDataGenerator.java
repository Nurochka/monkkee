package utils;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataGenerator {

    public static String generateRandomString(int length) {
         return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateSubString(String text, int first, int last) {
        return text.substring(first, last);
    }

}
