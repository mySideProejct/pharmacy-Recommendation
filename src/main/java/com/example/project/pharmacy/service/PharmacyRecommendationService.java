package com.example.project.pharmacy.service;


import com.example.project.api.dto.DocumentDto;
import com.example.project.api.dto.KakaoApiResponseDto;
import com.example.project.api.service.KakaoAddressSearchService;
import com.example.project.direction.dto.OutputDto;
import com.example.project.direction.entity.Direction;
import com.example.project.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    public List<OutputDto> recommendPharmacyList(String address) {

        // 위치 기반의 데이터 제공
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        // 카카오 api에서 주소 입력시 값이 넘어 오지 않는다면,
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendationService recommendPharmacyList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);


        // 공공기관 약국 데이터 및 거리계산 알고리즘
        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        // kakao 카테고리를 이용한 장소 검색 api 이용
//        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList).stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }


    private OutputDto convertToOutputDto(Direction direction) {
        /*
        Direction
        // 약국
        private String targetPharmacyName;
        private String targetAddress;
        private double targetLatitude;
        private double targetLongitude;

        // 고객 주소 와 약국 주소 사이의 거리
        private double distance;
        */

        /*
        OutputDto
        private String pharmacyName;    // 약국 명
        private String pharmacyAddress; // 약국 주소
        private String directionUrl;    // 길안내 url
        private String roadViewUrl;     // 로드뷰 url
        private String distance;        // 고객 주소와 약국 주소의 거리
        */

        String params = String.join(",", direction.getTargetPharmacyName(),
                String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));
        String result = UriComponentsBuilder.fromUriString(DIRECTION_BASE_URL + params).toUriString();// 이렇게되면 인코딩 된 string 값이 나온다.

        log.info("directions params: {}, url: {}", params, result);

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(result)
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude()+ ","+direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
