package Schedule.controller;

import Schedule.dto.PrimaryWeekendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.PrimaryWeekendService;


@Controller
@RequestMapping("/work_schedule/company/{companyId}")
@RequiredArgsConstructor
public class PrimaryWeekendController {

    private final PrimaryWeekendService service;

    @PostMapping("/employee/{employeeId}/weekend/add")
    private String add(@PathVariable Long employeeId, @ModelAttribute("weekend") PrimaryWeekendDto primaryWeekendDto) {
        service.add(employeeId, primaryWeekendDto);
        return "weekend/employee-weekends";
    }

    @GetMapping("/employee/{employeeId}/weekend/add")
    private String addForm(@PathVariable Long employeeId, Model model) {
        model.addAttribute("weekend", new PrimaryWeekendDto());
        return "weekend/weekend-form";
    }
}