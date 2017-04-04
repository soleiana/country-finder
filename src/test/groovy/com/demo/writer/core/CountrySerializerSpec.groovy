package com.demo.writer.core

import com.demo.common.CountryStorage
import spock.lang.Specification

class CountrySerializerSpec extends Specification {

    def storage = Mock(CountryStorage)
    CountrySerializer serializer = new CountrySerializer(storage)

    def "should serialize code-country map"() {

        when: "apply country serializer"
            serializer.apply( _ as Map)

        then: "save code-country map to storage"
            1 * storage.save(_ as Map)
    }

}
