package com.example.project.api.service


import spock.lang.Specification

import java.nio.charset.StandardCharsets




class KakaoUriBuilderServiceTest extends Specification {

    private KakaoUriBuilderService kakaoUriBuilderService

    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    /*
    통합테스트는 @SpringBootTest를 띄우고 하는 것이다.

    하지만 아래는 단위 테스트를 한 것이다.
    */

    // unit test를 시행한 것임!
    // 한글 주소를 입력 했을 때, uri를 잘 만들어 오는지 확인
    def "build uri by address search - 한글 파라미터의 경우 정상적으로 인코딩"() {
        given:
        String address = "서울 성북구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address)   // 그루비언어는 동적타입 선언이 가능하다!!!
        def decodedResult = URLDecoder.decode(uri.toString(), charset)

        then:
        println(uri) // https://dapi.kakao.com/v2/local/search/address.json?query=%EC%84%9C%EC%9A%B8%20%EC%84%B1%EB%B6%81%EA%B5%AC
        println decodedResult
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 성북구"
    }
}
