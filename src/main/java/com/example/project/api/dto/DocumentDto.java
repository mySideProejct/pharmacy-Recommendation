package com.example.project.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("address_name")
    private String addressName;

    //경도
    @JsonProperty("x")
    private double longitude;

    //위도
    @JsonProperty("y")
    private double latitude;

    @JsonProperty("distance")
    private double distance;
}
