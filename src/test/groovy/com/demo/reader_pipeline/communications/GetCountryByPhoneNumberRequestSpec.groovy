package com.demo.reader_pipeline.communications

import com.demo.reader_pipeline.core.FormattedPhoneNumber
import com.demo.reader_pipeline.core.RawPhoneNumber
import com.demo.reader_pipeline.core.RawPhoneNumberFactory
import com.demo.reader_pipeline.core.ValidatedPhoneNumber
import spock.lang.Specification

class GetCountryByPhoneNumberRequestSpec extends Specification {

    def PHONE_NUMBER = '+123 123 45678'
    RawPhoneNumberFactory rawPhoneNumberFactory = Mock()
    RawPhoneNumber rawPhoneNumber = Mock()
    FormattedPhoneNumber formattedPhoneNumber = Mock()
    ValidatedPhoneNumber validatedPhoneNumber = Mock()
    com.demo.common_context.Country country = Mock()

    GetCountryByPhoneNumberRequest request =
            new GetCountryByPhoneNumberRequest(PHONE_NUMBER, rawPhoneNumberFactory)

    def "should process phone number and find country by the country code"() {

        given: "valid international phone number from web"

        when: "request to find country by phone number executes"
            request.execute()

        then: "it creates initial (raw) string from phone number"
            1 * rawPhoneNumberFactory.of(_ as String) >> rawPhoneNumber

        then: "formats raw string for subsequent processing"
            1 * rawPhoneNumber.format() >> formattedPhoneNumber

        then: "validates formatted phone number against several international phone number formats"
            1 * formattedPhoneNumber.validate() >> validatedPhoneNumber

        then: "finds country in country register by its calling code"
            1 * validatedPhoneNumber.findCountry() >> country

        then: "transforms country to web model"
            1 * country.transform()
    }
}
