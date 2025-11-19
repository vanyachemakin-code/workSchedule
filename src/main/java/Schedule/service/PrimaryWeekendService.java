package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.EmployeeDto;
import Schedule.dto.PrimaryWeekendDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;

import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.exception.WeekendsNotFoundException;
import Schedule.repository.CompanyRepository;
import Schedule.repository.PrimaryWeekendRepository;
import Schedule.util.mapper.CompanyMapper;
import Schedule.util.mapper.EmployeeMapper;
import Schedule.util.mapper.PrimaryWeekendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.EmployeeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrimaryWeekendService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;
    private final PrimaryWeekendRepository weekendRepository;
    private final CompanyRepository companyRepository;

    public void add(Long employeeId, PrimaryWeekendDto primaryWeekendDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        EmployeeDto employeeDto = EmployeeMapper.entityToDto(optionalEmployee.get());
        employeeDto.setPrimaryWeekends(primaryWeekendDto);

        employeeService.update(
                employeeDto.getCompanyDto().getId(),
                employeeDto
        );
        weekendRepository.save(PrimaryWeekendMapper.dtoToEntity(primaryWeekendDto));
    }

    public void deleteWeekends(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        EmployeeDto employeeDto = EmployeeMapper.entityToDto(optionalEmployee.get());
        if (employeeDto.getPrimaryWeekends() == null) throw new WeekendsNotFoundException();

        weekendRepository.delete(PrimaryWeekendMapper.dtoToEntity(
                employeeDto.getPrimaryWeekends()
        ));
    }

    public void deleteAllWeekendAfterGenerate(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        CompanyDto companyDto = CompanyMapper.entityToDto(optionalCompany.get());
        for (EmployeeDto employeeDto: companyDto.getEmployees()) {
            deleteWeekends(employeeDto.getId());
        }
    }
}
