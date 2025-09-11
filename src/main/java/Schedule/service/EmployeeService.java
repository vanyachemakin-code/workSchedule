package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.employee.EmployeeDto;
import Schedule.dto.employee.EmployeeDtoCreate;
import Schedule.entity.Company;
import Schedule.entity.Employee;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.repository.EmployeeRepository;
import Schedule.util.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(long companyId, EmployeeDtoCreate employeeDtoCreate) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        CompanyDto companyDto = Mapper.mapCompanyToDto(optionalCompany.get());

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName(employeeDtoCreate.getName());
        employeeDto.setCompanyDto(companyDto);

        List<EmployeeDto> employeeDtoList = companyDto.getEmployees();
        employeeDtoList.add(employeeDto);
        companyDto.setEmployees(employeeDtoList);

        companyRepository.save(Mapper.mapCompanyDtoToEntity(companyDto));
        employeeRepository.save(Mapper.mapEmployeeDtoToEntity(employeeDto));
    }

    public Collection<EmployeeDto> getAllEmployeesInCompany(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        CompanyDto companyDto = Mapper.mapCompanyToDto(optionalCompany.get());
        return companyDto.getEmployees();
    }

    public EmployeeDto getById(long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();
        return Mapper.mapEmployeeToDto(optionalEmployee.get());
    }

    public Collection<EmployeeDto> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        if (employeeList.isEmpty()) throw new EmployeeNotFoundException();
        return employeeList.stream()
                .map(Mapper::mapEmployeeToDto)
                .toList();
    }

    public void deleteAll() {
        if (employeeRepository.findAll().isEmpty()) throw new EmployeeNotFoundException();
        employeeRepository.deleteAll();
    }

    public void deleteById(long id) {
        if (employeeRepository.findById(id).isEmpty()) throw new EmployeeNotFoundException();
        employeeRepository.deleteById(id);
    }

    public void deleteAllEmployeeInCompany(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        Company company = optionalCompany.get();
        company.setEmployees(List.of());
        companyRepository.save(company);
    }

    public void update(long companyId, EmployeeDto employeeDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        Company company = optionalCompany.get();
        company.getEmployees().add(Mapper.mapEmployeeDtoToEntity(employeeDto));
        companyRepository.save(company);
        employeeRepository.save(Mapper.mapEmployeeDtoToEntity(employeeDto));
    }
}
