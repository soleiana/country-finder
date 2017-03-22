package com.demo.core

import com.demo.exceptions.FormatException
import org.springframework.core.env.Environment
import spock.lang.Specification

class FormattingOperationsSpec extends Specification {

    static final EMPTY_NUMBER_MESSAGE_PROPERTY = 'phone.number.empty_number_message'
    static final INTERNATIONAL_CALL_PREFIX_PROPERTY = 'phone.number.international_call_prefix'
    static final EMPTY_NUMBER_MESSAGE = 'phone number is empty'
    static final INTERNATIONAL_CALL_PREFIX = '+'

    def environment = Stub(Environment)
    FormattingOperations operations =  new FormattingOperations(environment)

    def setup() {
        environment.getRequiredProperty(INTERNATIONAL_CALL_PREFIX_PROPERTY) >> INTERNATIONAL_CALL_PREFIX
        environment.getRequiredProperty(EMPTY_NUMBER_MESSAGE_PROPERTY) >> EMPTY_NUMBER_MESSAGE
        operations.initialize()
    }

    def "should return formatted phone number"() {

        given: "non empty phone number"
            def expectedPhoneNumber = operations.apply(phoneNumber)

        expect: "space normalized phone number with the leading plus"
            expectedPhoneNumber == formattedPhoneNumber
        where:
            phoneNumber             || formattedPhoneNumber
            '+371 12345678'         || '+371 12345678'
            '371  123\t  45  678'   || '+371 123 45 678'
            ' 371 12345678 '        || '+371 12345678'
            '\t371 12345678\t'      || '+371 12345678'
            '+  371 12345678'       || '+ 371 12345678'
            ' +371 12345678'        || '+371 12345678'
    }

    def "should throw FormatException if empty phone number"() {

        when: "there is empty, containing only space chars, and/or '+' phone number"
            operations.apply(phoneNumber)

        then: "throw FormatException"
            def exception = thrown(FormatException)
            exception.message == EMPTY_NUMBER_MESSAGE

        where:
            phoneNumber     | _
            ''              | _
            ' '             | _
            '   '           | _
            '+'             | _
            ' + '           | _
            ' \t '          | _
    }
}
