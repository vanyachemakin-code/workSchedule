package Schedule.controller;

import Schedule.dto.EmployeeDto;
import Schedule.dto.PrimaryWeekendDto;
import Schedule.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.PrimaryWeekendService;

import java.text.MessageFormat;


@Controller
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
public class PrimaryWeekendController {

    private final PrimaryWeekendService weekendService;
    private final EmployeeService employeeService;

    @PostMapping("/company/{companyId}/employee/{employeeId}/weekend/add")
    private String add(@PathVariable Long companyId, @PathVariable Long employeeId, @ModelAttribute("weekend") PrimaryWeekendDto primaryWeekendDto) {
        weekendService.add(employeeId, primaryWeekendDto);
        return MessageFormat.format(
                "redirect:/work_schedule/company/{0}/employee/{1}/weekend/list",
                companyId,
                employeeId
        );
    }

    @GetMapping("/company/{companyId}/employee/{employeeId}/weekend/add")
    private String addForm(@PathVariable Long companyId, @PathVariable Long employeeId, Model model) {
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        model.addAttribute("employeeName", employeeDto.getName());
        model.addAttribute("companyId", employeeDto.getCompanyDto().getId());
        model.addAttribute("employeeId", employeeDto.getId());
        model.addAttribute("weekend", new PrimaryWeekendDto());
        return "weekend/weekend-form";
    }

    @GetMapping("/company/{companyId}/employee/{employeeId}/weekend/list")
    private String getEmployeeWeekends(@PathVariable Long employeeId, Model model) {
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        model.addAttribute("companyId", employeeDto.getCompanyDto().getId());
        model.addAttribute("employeeName", employeeDto.getName());
        model.addAttribute("weekendDates", employeeDto.getPrimaryWeekends().getDate());
        return "weekend/employee-weekends";
    }
}