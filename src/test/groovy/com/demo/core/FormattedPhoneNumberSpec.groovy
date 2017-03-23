package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

class FormattedPhoneNumberSpec extends Specification {

    static final String INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final FORMATTED_ITU_T_VALID_PHONE_NUMBER = '+37126394806'
    static final FORMATTED_BASIC_VALID_PHONE_NUMBER = '+37112345(678)'
    static final FORMATTED_BASIC_AND_NANP_VALID_PHONE_NUMBER = '+1-(234)567.890'
    static final FORMATTED_BASIC_AND_EPP_VALID_PHONE_NUMBER = '+371.26394806x1234-a'

    def basicValidationRule = Mock(BasicValidationRule)
    def itu_tValidationRule = Mock(ITU_TValidationRule)
    def nanpValidationRule = Mock(NANPValidationRule)
    def eppValidationRule = Mock(EPPValidationRule)

    def finalValidationRules = [itu_tValidationRule, nanpValidationRule, eppValidationRule] as Set
    def validatedPhoneNumberFactory = Mock(ValidatedPhoneNumberFactory)
    FormattedPhoneNumber formattedPhoneNumber
    PhoneNumberString phoneNumberString


    def "should throw ValidationException if phone number does not comply basic validation rule"() {

        given: "formatted ITU-T invalid phone number"
            phoneNumberString = new PhoneNumberString(FORMATTED_BASIC_AND_NANP_VALID_PHONE_NUMBER)
            formattedPhoneNumber = createFormattedPhoneNumber()

        when: "validate"
            formattedPhoneNumber.validate()

        then: "apply basic validation rule and throw ValidationException"
            thrown(ValidationException)
                1 * basicValidationRule.apply(_  as String) >> {
                    throw new ValidationException(INVALID_ITU_T_FORMAT)
        }
    }

    def "should throw ValidationException if phone number passes basic validation, but fails all the final rules"() {

        given: "formatted basic and EPP valid phone number"
            phoneNumberString = new PhoneNumberString(FORMATTED_BASIC_VALID_PHONE_NUMBER)
            formattedPhoneNumber = createFormattedPhoneNumber()

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * basicValidationRule.apply(_  as String) >> true

        and: "fail all the final validation rules"
            1 * itu_tValidationRule.apply(_  as String) >> false
            1 * nanpValidationRule.apply(_  as String) >> false
            1 * eppValidationRule.apply(_  as String) >> false

        then: "throw ValidationException"
            thrown(ValidationException)

    }

    def "should create validated phone number if it complies basic validation rule and ITU-T rule"() {

        given: "formatted ITU-T valid phone number"
            phoneNumberString = new PhoneNumberString(FORMATTED_ITU_T_VALID_PHONE_NUMBER)
            formattedPhoneNumber = createFormattedPhoneNumber()

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * basicValidationRule.apply(_  as String) >> true

        then: "pass ITU-T rule"
            1 * itu_tValidationRule.apply(_  as String) >> true

        and: "no other rules are applied"
            0 * nanpValidationRule.apply(_ as String)
            0 * eppValidationRule.apply(_ as String)

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(FORMATTED_ITU_T_VALID_PHONE_NUMBER)

    }

    def "should create validated phone number if it complies basic validation rule and EPP rule"() {

        given: "formatted basic and EPP valid phone number"
            phoneNumberString = new PhoneNumberString(FORMATTED_BASIC_AND_EPP_VALID_PHONE_NUMBER)
            formattedPhoneNumber = createFormattedPhoneNumber()

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * basicValidationRule.apply(_  as String) >> true

        then: "fail ITU-T rule"
            1 * itu_tValidationRule.apply(_  as String) >> false

        then: "fail NANP rule"
            1 * nanpValidationRule.apply(_  as String) >> false

        then: "pass EPP rule"
            1 * eppValidationRule.apply(_  as String) >> true

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(FORMATTED_BASIC_AND_EPP_VALID_PHONE_NUMBER)
    }

    def "should create validated phone number if it complies basic validation rule and NANP rule"() {

        given: "formatted basic and EPP valid phone number"
            phoneNumberString = new PhoneNumberString(FORMATTED_BASIC_AND_NANP_VALID_PHONE_NUMBER)
            formattedPhoneNumber = createFormattedPhoneNumber()

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * basicValidationRule.apply(_  as String) >> true

        then: "fail ITU-T rule"
            1 * itu_tValidationRule.apply(_  as String) >> false

        then: "pass NANP rule"
            1 * nanpValidationRule.apply(_  as String) >> true

        and: "no other rules are applied"
            0 * eppValidationRule.apply(_ as String)

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(FORMATTED_BASIC_AND_NANP_VALID_PHONE_NUMBER)
    }

    def createFormattedPhoneNumber() {
        new FormattedPhoneNumber(phoneNumberString,
                validatedPhoneNumberFactory,
                basicValidationRule,
                finalValidationRules)
    }
}
