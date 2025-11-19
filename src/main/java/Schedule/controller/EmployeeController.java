package Schedule.controller;

import Schedule.dto.EmployeeDto;
import Schedule.model.EmployeeModel;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.EmployeeService;

@Controller
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/company/{companyId}/employee/create")
    private String create(@PathVariable Long companyId, @ModelAttribute("employee") EmployeeDto employeeDto) {
        service.create(companyId, employeeDto);
        return "redirect:/work_schedule/company/" + companyId + "/employees";
    }

    @GetMapping("/company/{companyId}/employee/create")
    private String createForm(Model model) {
        model.addAttribute("employee", new EmployeeModel());
        return "employee/employee-form";
    }

    @GetMapping("/company/{companyId}/employee/delete/{id}")
    private String deleteById(@PathVariable Long companyId) {
        service.deleteById(companyId);
        return "redirect:/work_schedule/company/" + companyId + "/employees";
    }

    @GetMapping("/company/{companyId}/employee/delete_all")
    private String deleteAllEmployeeInCompany(@PathVariable Long companyId) {
        service.deleteAllEmployeeInCompany(companyId);
        return "redirect:/work_schedule/company/" + companyId + "/employees";
    }

    @GetMapping("/employee/get/{id}")
    private String getById (@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.getById(id));
        return "employee/employee-get-by-id";
    }

    @GetMapping("/company/{companyId}/employee/get_all")
    private String getAllEmployeeInCompany(@PathVariable Long companyId, Model model) {
        model.addAttribute("employees_in_company", service.getAllEmployeesInCompany(companyId));
        return "employee/employee-list-in-company";
    }

    @GetMapping("/employee-list")
    private String getAll(Model model) {
        model.addAttribute("all_employees", service.getAll());
        return "employee/all-employees-list";
    }

    @PutMapping("/company/{companyId}/employee/update/{id}")
    private String update(@PathVariable Long companyId, @PathVariable Long id, @RequestBody EmployeeModel employeeModel) {
        service.update(id, EmployeeMapper.modelToDto(employeeModel));
        return "redirect:/work_schedule/company/" + companyId + "/employees";
    }

    @GetMapping("/company/{companyId}/employee/update/{id}")
    public String updateForm(@PathVariable Long companyId, @PathVariable Long id, Model model) {
        EmployeeModel employeeModel = EmployeeMapper.dtoToModel(service.getById(id));
        if (employeeModel == null) return "redirect:/work_schedule/company/" + companyId + "/employees";

        model.addAttribute("employee", employeeModel);
        return "employee/employee-form";
    }
}
