package com.example.project.direction.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

// 기본 생성자가 있으면, setter가 필요! 그게 아니라면 없어도됨
@Getter
@AllArgsConstructor
public class InputDto {
    private String address;
}
