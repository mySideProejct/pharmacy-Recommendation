package com.example.project.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {

    /* "total_count": 1   --> 이와 같은 json 내용을 받을 때, 스네이크 표기법일 때, @JsonProperty로 mapping이 가능하다.*/
    @JsonProperty("total_count")
    private Integer totalCount;
}
