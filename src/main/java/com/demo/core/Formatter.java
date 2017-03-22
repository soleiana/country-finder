package com.demo.core;

import com.demo.exceptions.FormatException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;

@Log
@Component
class Formatter {

    private static final String EMPTY_NUMBER_MESSAGE_PROPERTY = "phone.number.empty_number_message";
    private static final String INTERNATIONAL_CALL_PREFIX_PROPERTY = "phone.number.international_call_prefix";
    private static String emptyNumberMessage = "";
    private static String internationalCallPrefix = "";

    private final Environment environment;

    @Autowired
    public Formatter(Environment environment) {
        this.environment = environment;
    }

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
        internationalCallPrefix = environment.getProperty(INTERNATIONAL_CALL_PREFIX_PROPERTY);
        emptyNumberMessage = environment.getProperty(EMPTY_NUMBER_MESSAGE_PROPERTY);

        log.log(Level.INFO, format("initializing bean %s", this.getClass().getSimpleName()));
        log.log(Level.INFO, format("INTERNATIONAL_CALL_PREFIX=%s", internationalCallPrefix));
        log.log(Level.INFO, format("EMPTY_NUMBER_MSG=%s", emptyNumberMessage));
    }
}
