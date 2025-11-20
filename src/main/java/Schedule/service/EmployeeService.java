package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.EmployeeDto;
import Schedule.entity.Employee;

import Schedule.exception.EmployeeNotFoundException;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import Schedule.repository.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeService {

    private final CompanyService companyService;
    private final EmployeeRepository employeeRepository;

    public void create(Long companyId, EmployeeDto employeeDto) {
        log.info("Adding Employee with ID {} for Company with ID {}", employeeDto.getId(), companyId);
        CompanyDto companyDto = companyService.getById(companyId);

        employeeDto.setCompanyDto(companyDto);
        employeeRepository.save(EmployeeMapper.dtoToEntity(employeeDto));
        log.info("Add Employee complete");
    }

    public Collection<EmployeeDto> getAllEmployeesInCompany(Long companyId) {
        log.info("Search all Employees in Compony with ID {}", companyId);
        CompanyDto companyDto = companyService.getById(companyId);
        List<EmployeeDto> employees = companyDto.getEmployees();
        log.info("Employees found");
        return employees;
    }

    public EmployeeDto getById(Long id) {
        log.info("Search Employee with ID {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();
        log.info("Employee found");
        return EmployeeMapper.entityToDto(optionalEmployee.get());
    }

    public Collection<EmployeeDto> getAll() {
        log.info("Search all Employees");
        List<Employee> employeeList = employeeRepository.findAll();
        log.info("Employee list found");
        return employeeList.stream()
                .map(EmployeeMapper::entityToDto)
                .toList();
    }

    public void deleteById(Long id) {
        log.info("Deleting Employee with ID {}", id);
        if (employeeRepository.findById(id).isEmpty()) throw new EmployeeNotFoundException();
        employeeRepository.deleteById(id);
        log.info("Delete complete");
    }

    @Transactional
    public void deleteAllEmployeeInCompany(Long companyId) {
        log.info("Deleting all Employees in Company with ID {}", companyId);
        employeeRepository.deleteAllByCompanyId(companyId);
        log.info("Delete complete");
    }

    public void update(Long companyId,Long id, EmployeeDto employeeDto) {
        log.info("Updating Employee with ID {} in Company with ID {}", id, companyId);
        log.debug(!id.equals(employeeDto.getId()) ?
                "ID ERROR: Search Employee ID and Updated Employee ID not equals" :
                "IDs equals");

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
        employee.setName(employeeDto.getName());
        employeeRepository.save(employee);
        log.info("Update complete");
    }
}
