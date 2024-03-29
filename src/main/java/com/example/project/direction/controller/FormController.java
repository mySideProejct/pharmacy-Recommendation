package com.example.project.direction.controller;


import com.example.project.direction.dto.InputDto;
import com.example.project.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {

    // 해당 컨트롤러를 단위 테스트 하기 위해선 의존받은 것에 대해 mocking이 필요하다.
    private final PharmacyRecommendationService pharmacyRecommendationService;
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public /*ModelAndView*/String postDirection(@ModelAttribute InputDto inputDto, Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("output");
//        modelAndView.addObject("outputFormList",
//                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));
//
//        return modelAndView;
        model.addAttribute("outputFormList",
                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress()));

        return "output";
    }
}
