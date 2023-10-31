package utils;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataGenerator {

    public static String generateRandomString(int length) {
         String generatedString = RandomStringUtils.randomAlphanumeric(length);
         return generatedString;
    }

    public static String generateSubString(String text, int first, int last) {
        String generatedSubString = text.substring(first, last);
        return generatedSubString;
    }

}
