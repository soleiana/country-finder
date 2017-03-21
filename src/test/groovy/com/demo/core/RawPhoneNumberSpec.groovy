package com.demo.core

import com.demo.exceptions.FormatException
import spock.lang.Specification

class RawPhoneNumberSpec extends Specification {

    final EMPTY_NUMBER_MESSAGE = "phone number is empty"
    RawPhoneNumber rawPhoneNumber

    def "should return formatted phone number"() {

        given: "non empty phone number"
            rawPhoneNumber = new RawPhoneNumber(phoneNumber)

        expect: "trimmed phone number without the leading plus"
            formattedPhoneNumber == rawPhoneNumber.format().getNumber()
        where:
            phoneNumber         || formattedPhoneNumber
            '+371 12345678'     || '371 12345678'
            '371 12345678'      || '371 12345678'
            ' 371 12345678 '    || '371 12345678'
            '\t371 12345678\t'  || '371 12345678'
            '+  371 12345678'   || '371 12345678'
            ' +371 12345678'    || '371 12345678'
    }

    def "should throw FormatException if empty phone number"() {

        given: "empty, containing only space chars, and/or '+' phone number"
            rawPhoneNumber = new RawPhoneNumber(phoneNumber)
        when:
            rawPhoneNumber.format()

        then: "expect FormatException"
            Exception exception = thrown(FormatException)
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
