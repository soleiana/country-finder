package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.regex.Pattern;

import static com.demo.core.Properties.EMPTY_NUMBER_MESSAGE_PROPERTY;
import static com.demo.core.Properties.EXTENSION_DELIMITER_PROPERTY;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Log
@Component
class BasicValidationRule extends PhoneNumberValidationRule {

    private static final String INVALID_ITU_T_FORMAT = "invalid ITU-T format";
    private static final Pattern PHONE_NUMBER_SPECIAL_CHARACTERS = Pattern.compile("[\\.\\-\\(\\)\\s]");

    private static final Pattern ITU_T_PHONE_NUMBER_WITHOUT_SPACE_CHARACTERS = Pattern.compile("^\\+[0-9]{7,15}$");

    private static String emptyNumberMessage = "";
    private static Character extensionDelimiter = '-';

    @Autowired
    BasicValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        checkArgument(isNotEmpty(phoneNumber), emptyNumberMessage);

        String numberWithoutSpecialChars = removeSpecialCharacters(phoneNumber);
        String numberWithoutExtension = removeExtension(numberWithoutSpecialChars);
        return phoneNumberRegexFactory.of(numberWithoutExtension,
                ITU_T_PHONE_NUMBER_WITHOUT_SPACE_CHARACTERS, INVALID_ITU_T_FORMAT)
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
        emptyNumberMessage = environment.getRequiredProperty(EMPTY_NUMBER_MESSAGE_PROPERTY.asAlias());
        extensionDelimiter = environment.getRequiredProperty(EXTENSION_DELIMITER_PROPERTY.asAlias(), Character.class);

        log.log(Level.INFO, format("Initializing bean %s", this.getClass().getSimpleName()));
        log.log(Level.INFO, format("%s=%s", EMPTY_NUMBER_MESSAGE_PROPERTY, emptyNumberMessage));
        log.log(Level.INFO, format("%s=%c", EMPTY_NUMBER_MESSAGE_PROPERTY, extensionDelimiter));
    }

}
