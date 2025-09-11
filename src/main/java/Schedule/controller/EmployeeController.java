package Schedule.controller;

import Schedule.dto.employee.EmployeeDto;
import Schedule.dto.employee.EmployeeDtoCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Schedule.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/company/{companyId}")
    private void create(@PathVariable long companyId, @RequestBody EmployeeDtoCreate employeeDtoRest) {
        service.create(companyId, employeeDtoRest);
    }

    @DeleteMapping("/delete/{id}")
    private void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/delete/company/{companyId}")
    private void deleteAllEmployeeInCompany(@PathVariable long companyId) {
        service.deleteAllEmployeeInCompany(companyId);
    }

    @DeleteMapping("delete_all")
    private void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/{id}")
    private EmployeeDto getById (@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/company/{companyId}")
    private Collection<EmployeeDto> getAllEmployeeInCompany(@PathVariable long companyId) {
        return service.getAllEmployeesInCompany(companyId);
    }

    @GetMapping
    private Collection<EmployeeDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/update/company/{companyId}")
    private void update(@PathVariable long companyId, @RequestBody EmployeeDto employeeDtoRest) {
        service.update(companyId, employeeDtoRest);
    }
}
