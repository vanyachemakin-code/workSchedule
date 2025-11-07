package Schedule.controller;

import Schedule.dto.PrimaryWeekendDto;
import Schedule.model.PrimaryWeekendModel;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.PrimaryWeekendService;


@RestController
@RequestMapping("/work_schedule/company/{companyId}")
@RequiredArgsConstructor
public class PrimaryWeekendController {

    private final PrimaryWeekendService service;

    @PostMapping("/employee/{id}")
    private String add(@PathVariable Long employeeId, @ModelAttribute("weekend") PrimaryWeekendDto primaryWeekendDto) {
        service.add(employeeId, primaryWeekendDto);
        return "employee-weekends";
    }

    private String addForm(@PathVariable Long id, Model model) {
        model.addAttribute("weekend", new PrimaryWeekendModel());
        return "weekend-form";
    }
}
