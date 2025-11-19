package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.EmployeeDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;

import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.util.BeanUtils;
import Schedule.util.mapper.CompanyMapper;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.repository.EmployeeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(Long companyId, EmployeeDto employeeDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        employeeDto.setCompanyDto(CompanyMapper.entityToDto(optionalCompany.get()));
        employeeRepository.save(EmployeeMapper.dtoToEntity(employeeDto));
    }

    public Collection<EmployeeDto> getAllEmployeesInCompany(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        CompanyDto companyDto = CompanyMapper.entityToDto(optionalCompany.get());
        return companyDto.getEmployees();
    }

    public EmployeeDto getById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();
        return EmployeeMapper.entityToDto(optionalEmployee.get());
    }

    public Collection<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream()
                .map(EmployeeMapper::entityToDto)
                .toList();
    }

    public void deleteById(Long id) {
        if (employeeRepository.findById(id).isEmpty()) throw new EmployeeNotFoundException();
        employeeRepository.deleteById(id);
    }

    public void deleteAllEmployeeInCompany(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        Company company = optionalCompany.get();
        company.setEmployees(List.of());
        companyRepository.save(company);
    }

    public void update(Long id, EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        Employee employee = optionalEmployee.get();
        BeanUtils.copyFields(EmployeeMapper.dtoToEntity(employeeDto), employee);
        employeeRepository.save(employee);
    }
}
