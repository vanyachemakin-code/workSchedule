package Schedule.controller;

import Schedule.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Schedule.service.EmployeeService;

import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    //Изменить входные данный на модели и настроить из взаимодействие с HTML

    @PostMapping("/company/{companyId}")
    private void create(@PathVariable Long companyId, @RequestBody EmployeeDto employeeDto) {
        service.create(companyId, employeeDto);
    }

    @DeleteMapping("/delete/{id}")
    private void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/delete/company/{companyId}")
    private void deleteAllEmployeeInCompany(@PathVariable Long companyId) {
        service.deleteAllEmployeeInCompany(companyId);
    }

    @DeleteMapping("delete_all")
    private void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/{id}")
    private EmployeeDto getById (@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/company/{companyId}")
    private Collection<EmployeeDto> getAllEmployeeInCompany(@PathVariable Long companyId) {
        return service.getAllEmployeesInCompany(companyId);
    }

    @GetMapping
    private Collection<EmployeeDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/update/company/{companyId}")
    private void update(@PathVariable Long companyId, @RequestBody EmployeeDto employeeDtoRest) {
        service.update(companyId, employeeDtoRest);
    }
}
