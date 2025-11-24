package Schedule.controller;

import Schedule.dto.CompanyDto;
import Schedule.model.EmployeeModel;
import Schedule.service.CompanyService;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Schedule.service.EmployeeService;

@Controller
@RequestMapping("/work_schedule")
@RequiredArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @PostMapping("/company/{companyId}/employee/create")
    private String create(@PathVariable Long companyId,
                          @ModelAttribute("employee") EmployeeModel employeeModel) {
        employeeService.create(companyId, EmployeeMapper.modelToDto(employeeModel));
        return "redirect:/work_schedule/company/" + companyId + "/employee/get_all";
    }

    @GetMapping("/company/{companyId}/employee/create")
    private String createForm(@PathVariable Long companyId, Model model) {
        model.addAttribute("companyId", companyId);
        model.addAttribute("employee", new EmployeeModel());
        return "employee/employee-form";
    }

    @PostMapping("/company/{companyId}/employee/delete/{id}")
    private String deleteById(@PathVariable Long companyId,
                              @PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/work_schedule/company/" + companyId + "/employee/get_all";
    }

    @PostMapping("/company/{companyId}/employee/delete_all")
    private String deleteAllEmployeeInCompany(@PathVariable Long companyId) {
        employeeService.deleteAllEmployeeInCompany(companyId);
        return "redirect:/work_schedule/company/" + companyId + "/employee/get_all";
    }

    @GetMapping("/employee/get/{id}")
    private String getById (@PathVariable Long id, Model model) {
        model.addAttribute("employeeId", id);
        model.addAttribute("employee", employeeService.getById(id));
        return "employee/employee-get-by-id";
    }

    @GetMapping("/company/{companyId}/employee/get_all")
    private String getAllEmployeeInCompany(@PathVariable Long companyId, Model model) {
        CompanyDto companyDto = companyService.getById(companyId);
        model.addAttribute("companyName", companyDto.getName());
        model.addAttribute("companyId", companyId);
        model.addAttribute("employees_in_company", employeeService.getAllEmployeesInCompany(companyId));
        return "employee/employee-list-in-company";
    }

    @GetMapping("/employee-list")
    private String getAll(Model model) {
        model.addAttribute("all_employees", employeeService.getAll());
        return "employee/all-employees-list";
    }

    @PostMapping("/company/{companyId}/employee/update/{id}")
    private String update(@PathVariable Long companyId,
                          @PathVariable Long id,
                          @ModelAttribute("employee") EmployeeModel employeeModel) {
        employeeService.update(companyId, id, EmployeeMapper.modelToDto(employeeModel));
        return "redirect:/work_schedule/company/" + companyId + "/employee/get_all";
    }

    @GetMapping("/company/{companyId}/employee/update/{id}")
    public String updateForm(@PathVariable Long companyId,
                             @PathVariable Long id,
                             Model model) {
        EmployeeModel employeeModel = EmployeeMapper.dtoToModel(employeeService.getById(id));
        if (employeeModel == null) return "redirect:/work_schedule/company/" + companyId + "/employee/get_all";

        model.addAttribute("employee", employeeModel);
        return "employee/employee-form";
    }
}
