/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.User;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

;

/**
 *
 * @author sunkwa-arthur
 */
public class PasswordGenerator {

    private static final String PUNCTUATION = "?=.*@#$%?&+-";

    private static final Logger log = Logger.getLogger(PasswordGenerator.class.getName());

    private static SecureRandom rndm_method;

    static {
        try {
            rndm_method = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException ex) {
//            java.util.logging.Logger.getLogger(PasswordGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        log.info(PasswordGenerator.generatePassword());
//        System.out.println("PASS: " + PasswordGenerator.generateUuid());

    }

//    public static String generatePassword() {
//        String password = RandomStringUtils.random(4, true, true)
//                + PUNCTUATION.charAt((int) (Math.random() * PUNCTUATION.length()))
//                + RandomStringUtils.random(3, true, true);
//        return password;
//    }
    public Stream<Character> getRandomSpecialChars(int count) {

        IntStream specialChars = rndm_method.ints(count, 33, 45);
        return specialChars.mapToObj(data -> (char) data);
    }

    public static String generatePassword() {
//        Random rndm_method = new Random();
        String upperCaseLetters = RandomStringUtils.random(4, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(5, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = "" + PUNCTUATION.charAt(rndm_method.nextInt(PUNCTUATION.length()));
//        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
//        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters
                .concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public static String generateUuid() {
        String upperCaseLetters = RandomStringUtils.random(5, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(4);
//        String specialChar = "" + PUNCTUATION.charAt(rndm_method.nextInt(PUNCTUATION.length()));
//        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
//        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters
                .concat(lowerCaseLetters)
                .concat(numbers);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return password.toLowerCase().replaceAll("\\b(\\w{5})(\\w{3})(\\w{4})\\b", "$1-$2-$3");
    }

    public static boolean validatePassword(String password) {
        //Password Policy
        String patt = "(?=.{12,})"
                + // "" followed by 12+ symbols(length)
                "(?=.*[a-z])"
                + // --- ' ' --- at least 1 lower
                "(?=.*[A-Z])"
                + // --- ' ' --- at least 1 upper
                "(?=.*[0-9])"
                + // --- ' ' --- at least 1 digit
                "(?=.*\\p{Punct})"
                + // --- ' ' --- at least 1 symbol
                ".*";
        Pattern p = Pattern.compile(patt);
        Matcher m = p.matcher(password);
//        log.info("VALID ::: " + m.matches());
        return m.matches();
    }

    public static boolean containDetails(String password, User user) {
        boolean invalid = false;

        try {
            List<String> username = Arrays.asList(user.getUsername().trim().toLowerCase().split("[^\\w]+"));
            List<String> fname = Arrays.asList(user.getFirstname().trim().toLowerCase().split("[^\\w]+"));
            List<String> lname = Arrays.asList(user.getLastname().trim().toLowerCase().split("[^\\w]+"));
            password = password.toLowerCase();

            boolean containsUsername = username.stream().anyMatch(password::contains);
            boolean containsFname = fname.stream().anyMatch(password::contains);
            boolean containsLname = lname.stream().anyMatch(password::contains);

            invalid = containsUsername || containsFname || containsLname;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return invalid;
    }

    public static String generateOtp(int length) {
        String numbers = "0123456789"; // Numbers

//        Random rndm_method = new Random();
        String otp = "";

        for (int i = 0; i < length; i++) {
            otp = otp + numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        return otp;
    }

}
