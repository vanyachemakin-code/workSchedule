package service;

import dto.EmployeeDto;
import entity.Company;
import entity.Employee;
import exception.CompanyNotFoundException;
import exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.CompanyRepository;
import repository.EmployeeRepository;
import util.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(long companyId, EmployeeDto employeeDto) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        Employee employee = Mapper.mapEmployeeDtoToEntity(employeeDto);
        employee.setCompany(optionalCompany.get());
        employeeRepository.save(Mapper.mapEmployeeDtoToEntity(employeeDto));

        List<Employee> employeeList = optionalCompany.get().getEmployees();
        employeeList.add(employee);
        optionalCompany.get().setEmployees(employeeList);
        companyRepository.save(optionalCompany.get());
    }

    public Collection<EmployeeDto> getAllEmployeesInCompany(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        Collection<Employee> employees = optionalCompany.get().getEmployees();
        return employees.stream()
                .map(Mapper::mapEmployeeToDto)
                .toList();
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
        employeeRepository.deleteAll();;
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
