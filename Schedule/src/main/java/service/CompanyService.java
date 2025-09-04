package service;

import dto.CompanyDto;
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
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(CompanyDto companyDto) {
        companyRepository.save(Mapper.mapCompanyDtoToEntity(companyDto));
    }

    public void deleteById(long id) {
        if (companyRepository.findById(id).isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteById(id);
    }

    public void deleteAll() {
        if (companyRepository.findAll().isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteAll();

    }

    public CompanyDto getById(long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        return Mapper.mapCompanyToDto(optionalCompany.get());
    }

    public Collection<CompanyDto> getAll() {
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) throw new CompanyNotFoundException();
        return companyList.stream()
                .map(Mapper::mapCompanyToDto)
                .toList();
    }

    public CompanyDto getByEmployeeId(long id) {
        if (employeeRepository.findById(id).isEmpty()) throw new EmployeeNotFoundException();
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) throw new CompanyNotFoundException();

        for (Company company: companyList) {
            for (Employee employee: company.getEmployees()) {
                if (employee.getId().equals(id)) return Mapper.mapCompanyToDto(company);
            }
        }
        throw new CompanyNotFoundException();
    }

    public void update(long id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        Company company = optionalCompany.get();
        company.setName(companyDto.getName());
        companyRepository.save(company);
    }
}
