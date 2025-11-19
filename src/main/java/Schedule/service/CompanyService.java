package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.EmployeeDto;
import Schedule.entity.Company;
import Schedule.exception.CompanyNotFoundException;
import Schedule.service.employeeHelperService.EmployeeServiceGetById;
import Schedule.util.BeanUtils;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
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

    private final EmployeeServiceGetById employeeHelperService;
    private final CompanyRepository companyRepository;

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
        EmployeeDto employeeDto = employeeHelperService.getById(id);
        log.info("Search Company by Employee with ID {}", employeeDto.getId());
        return getById(employeeDto.getCompanyDto().getId());
    }

    public void update(Long id, CompanyDto companyDto) {
        log.info("Updating Company with ID {}", id);
        log.debug(id.equals(companyDto.getId()) ?
                "Search ID {} and updated Company ID {1} equals" :
                "ID ERROR: search ID {} and updated Company ID {1} not equals",
                id, companyDto.getId());

        CompanyDto companyBeforeUpdate = getById(id);
        BeanUtils.copyFields(companyDto, companyBeforeUpdate);
        companyRepository.save(CompanyMapper.dtoToEntity(companyBeforeUpdate));
        log.info("Update complete");
    }
}
