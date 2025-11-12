package Schedule.controller;

import Schedule.dto.EmployeeDto;
import Schedule.model.EmployeeModel;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.EmployeeService;

@RestController
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/company/{companyId}/employee/create")
    private String create(@PathVariable Long companyId, @ModelAttribute("employee") EmployeeDto employeeDto) {
        service.create(companyId, employeeDto);
        return "redirect:/work_schedule/company/{companyId}/employees";
    }

    @GetMapping("/company/{companyId}/employee/create")
    private String createForm(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        return "employee-form";
    }

    @DeleteMapping("/company/{companyId}/employee/delete/{id}")
    private String deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/work_schedule/company/{companyId}/employees";
    }

    @DeleteMapping("/company/{companyId}/employee/delete_all")
    private String deleteAllEmployeeInCompany(@PathVariable Long companyId) {
        service.deleteAllEmployeeInCompany(companyId);
        return "redirect:/work_schedule/company/{companyId}/employees";
    }

    @GetMapping("/employee/get/{id}")
    private String getById (@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.getById(id));
        return "employee-get-by-id";
    }

    @GetMapping("/company/{companyId}/employee/get_all")
    private String getAllEmployeeInCompany(@PathVariable Long companyId, Model model) {
        model.addAttribute("employees_in_company", service.getAllEmployeesInCompany(companyId));
        return "employee-list-in-company";
    }

    @GetMapping
    private String getAll(Model model) {
        model.addAttribute("all_employees", service.getAll());
        return "all-employees-list";
    }

    @PutMapping("/company/{companyId}/employee/update/{id}")
    private String update(@PathVariable Long id, @RequestBody EmployeeModel employeeModel) {
        service.update(id, EmployeeMapper.modelToDto(employeeModel));
        return "redirect:/work_schedule/company/{companyId}/employees";
    }

    @GetMapping("/company/{companyId}/employee/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        EmployeeModel employeeModel = EmployeeMapper.dtoToModel(service.getById(id));
        if (employeeModel == null) return "redirect:/contacts";

        model.addAttribute("employee", employeeModel);
        return "employee-form";
    }
}
