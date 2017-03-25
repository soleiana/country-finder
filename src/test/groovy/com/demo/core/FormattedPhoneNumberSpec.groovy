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

    def numberString = Mock(FormattedNumberString)
    def validatedNumberString = Mock(ValidatedNumberString)

    FormattedPhoneNumber formattedPhoneNumber

    def setup() {
        def numberStringWithoutSpecialCharacters = Mock(FormattedNumberString)
        def numberStringWithoutExtension = Mock(FormattedNumberString)

        1 * numberString.withoutSpecialCharacters() >> numberStringWithoutSpecialCharacters
        1 * numberStringWithoutSpecialCharacters.withoutExtension() >> numberStringWithoutExtension
        1 * numberStringWithoutExtension.build() >> validatedNumberString

        formattedPhoneNumber = createPhoneNumber()
    }

    def "should throw ValidationException if phone number does not comply basic validation rule"() {
        given: "formatted ITU-T invalid phone number"

            validatedNumberString.apply(basicValidationRule) >> {
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
            1 * validatedNumberString.apply(basicValidationRule) >> true

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
            1 * validatedNumberString.apply(basicValidationRule) >> true

        then: "pass ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> true

        and: "no other rules are applied"
            with(numberString) {
                0 * apply(nanpValidationRule)
                0 * apply(eppValidationRule)
            }

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as ValidatedNumberString)
    }

    def "should create validated phone number if it complies basic validation rule and EPP rule"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * validatedNumberString.apply(basicValidationRule) >> true

        then: "fail ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> false

        then: "fail NANP rule"
            1 * numberString.apply(nanpValidationRule) >> false

        then: "pass EPP rule"
            1 * numberString.apply(eppValidationRule) >> true

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as ValidatedNumberString)
    }

    def "should create validated phone number if it complies basic validation rule and NANP rule"() {

        when: "validate"
            formattedPhoneNumber.validate()

        then: "pass basic validation rule"
            1 * validatedNumberString.apply(basicValidationRule) >> true

        then: "fail ITU-T rule"
            1 * numberString.apply(itu_tValidationRule) >> false

        then: "pass NANP rule"
            1 * numberString.apply(nanpValidationRule) >> true

        and: "no other rules are applied"
            0 * numberString.apply(eppValidationRule)

        and: "create validated phone number"
            1 * validatedPhoneNumberFactory.of(_ as ValidatedNumberString)
    }

    def createPhoneNumber() {
        new FormattedPhoneNumber(numberString,
                validatedPhoneNumberFactory,
                basicValidationRule,
                finalValidationRules)
    }

}
