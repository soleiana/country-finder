package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;

import static com.demo.core.Properties.EMPTY_NUMBER_MESSAGE_PROPERTY;
import static com.demo.core.Properties.INTERNATIONAL_CALL_PREFIX_PROPERTY;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;

@Log
@Component
class FormattingOperations extends PhoneNumberOperations {

    private static String emptyNumberMessage = "";
    private static String internationalCallPrefix = "";

    @Autowired
    FormattingOperations(Environment environment) {
        super(environment);
    }

    @Override
    String apply(String phoneNumber) {
        String normalizedNumberWithLeadingPlus = prependIfMissing(normalizeSpace(phoneNumber), internationalCallPrefix);
        try {
            int prefixLength = internationalCallPrefix.length();
            checkArgument(normalizedNumberWithLeadingPlus.length() > prefixLength, emptyNumberMessage);
        } catch (IllegalArgumentException exception) {
            throw new FormatException(exception.getMessage());
        }
        return normalizedNumberWithLeadingPlus;
    }

    @PostConstruct
    void initialize() {
        internationalCallPrefix = environment.getRequiredProperty(INTERNATIONAL_CALL_PREFIX_PROPERTY.asAlias());
        emptyNumberMessage = environment.getRequiredProperty(EMPTY_NUMBER_MESSAGE_PROPERTY.asAlias());

        log.log(Level.INFO, format("Initializing bean %s", this.getClass().getSimpleName()));
        log.log(Level.INFO, format("%s=%s", INTERNATIONAL_CALL_PREFIX_PROPERTY, internationalCallPrefix));
        log.log(Level.INFO, format("%s=%s", EMPTY_NUMBER_MESSAGE_PROPERTY, emptyNumberMessage));
    }
}
