package com.demo.core;

import com.demo.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Order(1)
@Component
@PropertySource({"classpath:country-finder.properties"})
class PhoneNumberBasicValidationRule implements ValidationRule {

    private static final String MIN_DIGITS_IN_USE = "phone.number.min_digits_in_use";
    private static final String MAX_DIGITS_ITU_T = "phone.number.max_digits_itu_t";
    private static final String EMPTY_NUMBER_MESSAGE = "phone number is empty";
    private static final String INVALID_BASIC_FORMAT = "invalid basic format";
    private static final Pattern PHONE_NUMBER_SPECIAL_CHARACTERS = Pattern.compile("[\\+\\.\\-\\(\\)\\s]");

    private static Pattern basicPhoneNumber;

    private final Environment environment;

    @Autowired
    public PhoneNumberBasicValidationRule(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    void initialize() {
        final int MIN_DIGITS = environment.getProperty(MIN_DIGITS_IN_USE, Integer.class);
        final int MAX_DIGITS = environment.getProperty(MAX_DIGITS_ITU_T, Integer.class);
        String regex = "^[0-9]{" + MIN_DIGITS + "," + MAX_DIGITS + "}$";
        basicPhoneNumber = Pattern.compile(regex);
    }

    @Override
    public boolean apply(String phoneNumber) {
        checkArgument(isNotEmpty(phoneNumber), EMPTY_NUMBER_MESSAGE);

        String numberWithoutSpecialChars = removeSpecialCharacters(phoneNumber);
        String numberWithoutExtension = removeExtension(numberWithoutSpecialChars);
        return applyRegex(numberWithoutExtension, basicPhoneNumber);
    }

    private String removeSpecialCharacters(String phoneNumber) {
        return PHONE_NUMBER_SPECIAL_CHARACTERS.matcher(phoneNumber)
                .replaceAll("");
    }

    private String removeExtension(String phoneNumber) {
        final char EXTENSION_DELIMITER = 'x';
        int indexOfExtension = phoneNumber.lastIndexOf(EXTENSION_DELIMITER);
        return indexOfExtension > -1 ? phoneNumber.substring(0, indexOfExtension - 1) : phoneNumber;
    }

    private boolean applyRegex(String phoneNumber, Pattern regexPattern) {
        Matcher numberMatcher = regexPattern.matcher(phoneNumber);
        if (!numberMatcher.matches()) {
            throw new ValidationException(INVALID_BASIC_FORMAT);
        }
        return true;
    }
}
