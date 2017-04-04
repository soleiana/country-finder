package com.demo.reader.communications

import com.demo.reader.core.FormattedPhoneNumber
import com.demo.reader.core.RawPhoneNumber
import com.demo.reader.core.RawPhoneNumberFactory
import com.demo.reader.core.ValidatedPhoneNumber
import spock.lang.Specification

class SearchEngineSpec extends Specification {

    def PHONE_NUMBER = '+123 123 45678'
    RawPhoneNumberFactory rawPhoneNumberFactory = Mock()
    RawPhoneNumber rawPhoneNumber = Mock()
    FormattedPhoneNumber formattedPhoneNumber = Mock()
    ValidatedPhoneNumber validatedPhoneNumber = Mock()
    com.demo.common.Country country = Mock()

    SearchEngine searchEngine =
            new SearchEngine(PHONE_NUMBER, rawPhoneNumberFactory)

    def "should process phone number and find country by the country code"() {

        given: "valid international phone number from web"

        when: "run search"
            searchEngine.start()

        then: "create initial (raw) string from phone number"
            1 * rawPhoneNumberFactory.of(_ as String) >> rawPhoneNumber

        then: "format raw string for subsequent processing"
            1 * rawPhoneNumber.format() >> formattedPhoneNumber

        then: "validate formatted phone number against several international phone number formats"
            1 * formattedPhoneNumber.validate() >> validatedPhoneNumber

        then: "find country in country register by its calling code"
            1 * validatedPhoneNumber.findCountry() >> country

        then: "transforms country to web model"
            1 * country.transform()
    }
}
