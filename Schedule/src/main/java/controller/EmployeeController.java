package controller;

import dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/company?id={id}")
    private void create(@PathVariable long companyId, @RequestBody EmployeeDto employeeDto) {
        service.create(companyId, employeeDto);
    }

    @DeleteMapping("/delete?id={id}")
    private void deleteById(@PathVariable long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/delete/company?id={id}")
    private void deleteAllEmployeeInCompany(@PathVariable long companyId) {
        service.deleteAllEmployeeInCompany(companyId);
    }

    @DeleteMapping("delete_all")
    private void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("?id={id}")
    private EmployeeDto getById (@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/company?id={id}")
    private Collection<EmployeeDto> getAllEmployeeInCompany(@PathVariable long companyId) {
        return service.getAllEmployeesInCompany(companyId);
    }

    @GetMapping
    private Collection<EmployeeDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/update/company?id={id}")
    private void update(@PathVariable long companyId, @RequestBody EmployeeDto employeeDto) {
        service.update(companyId, employeeDto);
    }
}
