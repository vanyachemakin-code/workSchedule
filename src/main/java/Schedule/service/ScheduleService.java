package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.ScheduleDto;
import Schedule.entity.Company;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.util.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.util.Schedule;

import java.time.Month;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CompanyRepository companyRepository;
    private final PrimaryWeekendService weekendService;

    public Collection<ScheduleDto> get(Month month, Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        if (optionalCompany.get().getEmployees().isEmpty()) throw new EmployeeNotFoundException();

        CompanyDto companyDto = CompanyMapper.entityToDto(optionalCompany.get());
        weekendService.deleteAllWeekendAfterGenerate(companyId);

        return new Schedule(month, companyDto).generate();
    }
}
