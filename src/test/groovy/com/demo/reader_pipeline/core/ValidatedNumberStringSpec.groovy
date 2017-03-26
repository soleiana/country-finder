package com.demo.reader_pipeline.core

import com.demo.reader_pipeline.exceptions.SearchException
import com.demo.reader_pipeline.exceptions.ValidationException
import spock.lang.Specification

class ValidatedNumberStringSpec extends Specification {

    static final INVALID_ITU_T_FORMAT = 'invalid ITU-T format'
    static final INVALID_PHONE_NUMBER_FORMAT = 'phone number should contain only digits'

    static final PHONE_NUMBER_FOR_VALIDATION = '+37112345678'
    static final INVALID_PHONE_NUMBER_FOR_VALIDATION = '+371 1234567A'
    static final PHONE_NUMBER_FOR_SEARCH = '37112345678'
    static final INVALID_PHONE_NUMBER_FOR_SEARCH = '+3711234567'

    def validationRule = Mock(BasicValidationRule)
    def countrySearch = Mock(CountrySearch)

    ValidatedNumberString phoneNumberString

    def "should apply validation rule"() {

        given: "valid phone number"
            phoneNumberString = createValidatedNumberString(PHONE_NUMBER_FOR_VALIDATION)

        when: "apply basic validation rule"
            def result = phoneNumberString.apply(validationRule)

        then: "delegate validation"
            1 * validationRule.apply(PHONE_NUMBER_FOR_VALIDATION) >> true

        and: "result is success"
            result
    }

    def "should throw exception if validation rule throws exception"() {

        given: "invalid phone number"
            phoneNumberString = createValidatedNumberString(INVALID_PHONE_NUMBER_FOR_VALIDATION)

        when: "apply basic validation rule"
            phoneNumberString.apply(validationRule)

        then: "delegate validation and throw ValidationException"
            1 * validationRule.apply(INVALID_PHONE_NUMBER_FOR_VALIDATION) >> {
                throw new ValidationException(INVALID_ITU_T_FORMAT)
            }

        then: "ValidationException thrown"
            def exception = thrown(ValidationException)
            exception.message == INVALID_ITU_T_FORMAT
    }

    def "should apply country search"() {

        given: "valid phone number"
            phoneNumberString = createValidatedNumberString(PHONE_NUMBER_FOR_SEARCH)
            def country = new Country('Latvia')

        when: "apply country search"
            def result = phoneNumberString.apply(countrySearch)

        then: "delegate search"
            1 * countrySearch.apply(PHONE_NUMBER_FOR_SEARCH) >> country

        and: "country found"
            result == country
    }

    def "should throw exception if country search throws exception"() {

        given: "invalid phone number"
            phoneNumberString = createValidatedNumberString(INVALID_PHONE_NUMBER_FOR_SEARCH)

        when: "apply country search"
             phoneNumberString.apply(countrySearch)

        then: "delegate search and throw SearchException"
             1 * countrySearch.apply(INVALID_PHONE_NUMBER_FOR_SEARCH) >> {
            throw new SearchException(INVALID_PHONE_NUMBER_FORMAT)
        }

        then: "SearchException thrown"
            def exception = thrown(SearchException)
            exception.message == INVALID_PHONE_NUMBER_FORMAT
    }


   def createValidatedNumberString(String phoneNumber) {
       new ValidatedNumberString(phoneNumber)
   }

}
