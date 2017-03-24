package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

class FormattedPhoneNumberSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final INVALID_PHONE_NUMBER_FORMAT = 'invalid phone number format'

    def basicValidationRule = Mock(BasicValidationRule)
    def itu_tValidationRule = Mock(ITU_TValidationRule)
    def nanpValidationRule = Mock(NANPValidationRule)
    def eppValidationRule = Mock(EPPValidationRule)

    def finalValidationRules = [itu_tValidationRule, nanpValidationRule, eppValidationRule] as Set
    def validatedPhoneNumberFactory = Mock(ValidatedPhoneNumberFactory)

    def numberString = Mock(PhoneNumberString)
    def numberStringAsDigits = Mock(PhoneNumberString)

    FormattedPhoneNumber formattedPhoneNumber

    def setup() {
        def numberStringWithoutSpecialCharacters = Mock(PhoneNumberString)
        numberString.withoutSpecialCharacters() >> numberStringWithoutSpecialCharacters
        numberStringWithoutSpecialCharacters.withoutExtension() >> numberStringAsDigits

        formattedPhoneNumber = new FormattedPhoneNumber(numberString,
                validatedPhoneNumberFactory,
                basicValidationRule,
                finalValidationRules)
    }

    def "should throw ValidationException if phone number does not comply basic validation rule"() {
        given: "formatted ITU-T invalid phone number"

            numberStringAsDigits.apply(basicValidationRule) >> {
                throw new ValidationException(INVALID_ITU_T_FORMAT)
            }

        when: "validate"
            formattedPhoneNumber.validate()

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
        }

    def "should throw ValidationException if phone number passes basic validation, but fails all the final rules"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * numberStringAsDigits.apply(basicValidationRule) >> true

        and: "fail all the final validation rules"
            3 * numberString.apply(_ as FinalValidationRule) >> false

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_PHONE_NUMBER_FORMAT
    }

    def "should create validated phone number if it complies basic validation rule and ITU-T rule"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * numberStringAsDigits.apply(basicValidationRule) >> true

        then: "pass ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> true

        and: "no other rules are applied"
            0 * numberString.apply(nanpValidationRule)
            0 * numberString.apply(eppValidationRule)

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as PhoneNumberString)
    }

    def "should create validated phone number if it complies basic validation rule and EPP rule"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * numberStringAsDigits.apply(basicValidationRule) >> true

        then: "fail ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> false

        then: "fail NANP rule"
            1 * numberString.apply(nanpValidationRule) >> false

        then: "pass EPP rule"
            1 * numberString.apply(eppValidationRule) >> true

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as PhoneNumberString)
    }

    def "should create validated phone number if it complies basic validation rule and NANP rule"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * numberStringAsDigits.apply(basicValidationRule) >> true

        then: "fail ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> false

        then: "pass NANP rule"
            1 * numberString.apply(nanpValidationRule) >> true

        and: "no other rules are applied"
            0 * numberString.apply(eppValidationRule)

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as PhoneNumberString)
    }

}
