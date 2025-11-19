package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.util.BeanUtils;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.repository.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public void create(CompanyDto companyDto) {
        log.info("Adding Company with ID {}", companyDto.getId());
        companyRepository.save(CompanyMapper.dtoToEntity(companyDto));
        companyRepository.flush();
        log.info("Add Company with ID {} complete", companyDto.getId());
    }

    public void deleteById(Long id) {
        log.info("Deleting Company with ID {}", id);
        if (companyRepository.findById(id).isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteById(id);
        log.info("Delete Company with ID {} complete", id);
    }

    public void deleteAll() {
        log.info("Deleting all Company");
        if (companyRepository.findAll().isEmpty()) throw new CompanyNotFoundException();
        companyRepository.deleteAll();
        log.info("Delete all Company complete");

    }

    public CompanyDto getById(Long id) {
        log.info("Search Company with ID {}", id);
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        log.info("Company with ID {} found", optionalCompany.get().getId());
        return CompanyMapper.entityToDto(optionalCompany.get());
    }

    public Collection<CompanyDto> getAll() {
        log.info("Search all Company list");
        List<Company> companyList = companyRepository.findAll();
        if (companyList.isEmpty()) return new ArrayList<>();
        log.info("Company list found");
        return companyList.stream()
                .map(CompanyMapper::entityToDto)
                .toList();
    }

    public CompanyDto getByEmployeeId(Long id) {
        log.info("Search Employee with ID {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();
        log.info("Employee with ID {} found", optionalEmployee.get().getId());

        log.info("Search Company by Employee with ID {}", optionalEmployee.get().getId());
        Company company = optionalEmployee.get().getCompany();
        if (company == null) throw new CompanyNotFoundException();
        log.info("Company with ID {} found", company.getId());
        return CompanyMapper.entityToDto(company);
    }

    public void update(Long id, CompanyDto companyDto) {
        log.info("Search Company with ID {}", id);
        log.info(id.equals(companyDto.getId()) ?
                "Search ID {} and updated Company ID {1} equals" :
                "ID ERROR: search ID {} and updated Company ID {1} not equals",
                id, companyDto.getId());

        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        log.info("Company with ID {} found", id);

        log.info("Updating Company with ID {}", id);
        Company company = optionalCompany.get();
        BeanUtils.copyFields(CompanyMapper.dtoToEntity(companyDto), company);
        companyRepository.save(company);
        log.info("Update complete");
    }
}
