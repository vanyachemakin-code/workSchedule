package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.util.BeanUtils;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.repository.EmployeeRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(CompanyDto companyDto) {
        companyRepository.save(CompanyMapper.dtoToEntity(companyDto));
        companyRepository.flush();
    }

    public void deleteById(Long id) {
        if (companyRepository.findById(id).isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteById(id);
    }

    public void deleteAll() {
        if (companyRepository.findAll().isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteAll();

    }

    public CompanyDto getById(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        return CompanyMapper.entityToDto(optionalCompany.get());
    }

    public Collection<CompanyDto> getAll() {
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) throw new CompanyNotFoundException();
        return companyList.stream()
                .map(CompanyMapper::entityToDto)
                .toList();
    }

    public CompanyDto getByEmployeeId(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        Company company = optionalEmployee.get().getCompany();
        if (company == null) throw new CompanyNotFoundException();
        return CompanyMapper.entityToDto(company);
    }

    public void update(Long id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        Company company = optionalCompany.get();
        BeanUtils.copyFields(CompanyMapper.dtoToEntity(companyDto), company);
        companyRepository.save(company);
    }
}
