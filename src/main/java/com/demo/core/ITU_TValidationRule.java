package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.regex.Pattern;

import static com.demo.core.Properties.*;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Log
@Order(1)
@Component
class ITU_TValidationRule extends PhoneNumberValidationRule {

    private static final String INVALID_BASIC_FORMAT = "invalid basic format";
    private static final Pattern PHONE_NUMBER_SPECIAL_CHARACTERS = Pattern.compile("[\\.\\-\\(\\)\\s]");

    private static Pattern ITU_TPhoneNumber;
    private static String emptyNumberMessage = "";
    private static Character extensionDelimiter = '-';

    @Autowired
    ITU_TValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        checkArgument(isNotEmpty(phoneNumber), emptyNumberMessage);

        String numberWithoutSpecialChars = removeSpecialCharacters(phoneNumber);
        String numberWithoutExtension = removeExtension(numberWithoutSpecialChars);
        return phoneNumberRegexFactory.of(numberWithoutExtension, ITU_TPhoneNumber, INVALID_BASIC_FORMAT)
                .applyWithException();
    }

    private String removeSpecialCharacters(String phoneNumber) {
        return PHONE_NUMBER_SPECIAL_CHARACTERS.matcher(phoneNumber)
                .replaceAll("");
    }

    private String removeExtension(String phoneNumber) {
        int indexOfExtension = phoneNumber.toLowerCase().indexOf(extensionDelimiter);
        return indexOfExtension > -1 ? phoneNumber.substring(0, indexOfExtension - 1) : phoneNumber;
    }

    @PostConstruct
    void initialize() {
        final int MIN_DIGITS = environment.getRequiredProperty(MIN_DIGITS_IN_USE_PROPERTY.asAlias(), Integer.class);
        final int MAX_DIGITS = environment.getRequiredProperty(MAX_DIGITS_ITU_T_PROPERTY.asAlias(), Integer.class);
        emptyNumberMessage = environment.getRequiredProperty(EMPTY_NUMBER_MESSAGE_PROPERTY.asAlias());
        extensionDelimiter = environment.getRequiredProperty(EXTENSION_DELIMITER_PROPERTY.asAlias(), Character.class);

        String regex = "^\\+[0-9]{" + MIN_DIGITS + "," + MAX_DIGITS + "}$";
        ITU_TPhoneNumber = Pattern.compile(regex);

        log.log(Level.INFO, format("Initializing bean %s", this.getClass().getSimpleName()));
        log.log(Level.INFO, format("%s=%d", MIN_DIGITS_IN_USE_PROPERTY, MIN_DIGITS));
        log.log(Level.INFO, format("%s=%d", MIN_DIGITS_IN_USE_PROPERTY, MAX_DIGITS));
        log.log(Level.INFO, format("%s=%s", EMPTY_NUMBER_MESSAGE_PROPERTY, emptyNumberMessage));
        log.log(Level.INFO, format("%s=%c", EMPTY_NUMBER_MESSAGE_PROPERTY, extensionDelimiter));
    }

}
