package Schedule.controller;


import Schedule.dto.CompanyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Schedule.service.CompanyService;

import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @PostMapping
    private void create(@RequestBody CompanyDto companyDto) {
        service.create(companyDto);
    }

    @DeleteMapping("/delete/{id}")
    private void deleteById(long id) {
        service.deleteById(id);
    }

    @DeleteMapping("/delete_all")
    private void deleteAll() {
        service.deleteAll();
    }

    @GetMapping("/{id}")
    private CompanyDto getById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping("/employee/{id}")
    private CompanyDto getByEmployeeId(@PathVariable long id) {
        return service.getByEmployeeId(id);
    }

    @GetMapping()
    private Collection<CompanyDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/update/{id}")
    private void update(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        service.update(id, companyDto);
    }
}
