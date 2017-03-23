package com.demo.core;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.regex.Pattern;

import static com.demo.core.Properties.EMPTY_NUMBER_MESSAGE_PROPERTY;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Log
@Order(30)
@Component
class EPPValidationRule extends PhoneNumberValidationRule {

    private static final String EPP_REGEX = "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:[x|X].+)?$";
    private static final Pattern EPP_PHONE_NUMBER = Pattern.compile(EPP_REGEX);

    private static String emptyNumberMessage = "";

    @Autowired
    EPPValidationRule(Environment environment, PhoneNumberRegexFactory phoneNumberRegexFactory) {
        super(environment, phoneNumberRegexFactory);
    }

    @Override
    boolean apply(String phoneNumber) {
        checkArgument(isNotEmpty(phoneNumber), emptyNumberMessage);
        return phoneNumberRegexFactory.of(phoneNumber, EPP_PHONE_NUMBER, "")
                .apply();
    }

    @PostConstruct
    void initialize() {
        emptyNumberMessage = environment.getRequiredProperty(EMPTY_NUMBER_MESSAGE_PROPERTY.asAlias());

        log.log(Level.INFO, format("Initializing bean %s", this.getClass().getSimpleName()));
        log.log(Level.INFO, format("%s=%s", EMPTY_NUMBER_MESSAGE_PROPERTY, emptyNumberMessage));
    }
}
