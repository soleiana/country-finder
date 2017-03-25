package com.demo.core

import com.demo.exceptions.ValidationException
import spock.lang.Specification

class ValidatedNumberStringSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final PHONE_NUMBER = '+371 12345678'
    static final INVALID_PHONE_NUMBER = '+371 1234567A'

    def validationRule = Mock(BasicValidationRule)
    ValidatedNumberString phoneNumberString

    def "should apply validation rule"() {

        given: "basic validation rule is provided"
            phoneNumberString = createValidatedNumberString(PHONE_NUMBER)

        when: "apply rule"
            def result = phoneNumberString.apply(validationRule)

        then: "pass rule"
            1 * validationRule.apply(PHONE_NUMBER) >> true

        and: "result is success"
            result
    }

    def "should throw exception if validation rule throws exception"() {

        given: "basic validation rule is provided"
            phoneNumberString = createValidatedNumberString(INVALID_PHONE_NUMBER)

        when: "apply rule"
            phoneNumberString.apply(validationRule)

        then: "invoke rule and throw ValidationException"
            1 * validationRule.apply(INVALID_PHONE_NUMBER) >> {
                throw new ValidationException(INVALID_ITU_T_FORMAT)
            }

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
    }

   def createValidatedNumberString(String phoneNumber) {
       new ValidatedNumberString(phoneNumber)
   }

}
