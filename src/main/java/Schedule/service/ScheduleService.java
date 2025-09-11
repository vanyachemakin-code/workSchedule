package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.ScheduleDto;
import Schedule.entity.Company;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.CompanyRepository;
import Schedule.util.Mapper;
import Schedule.util.Schedule;

import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CompanyRepository companyRepository;
    private final PrimaryWeekendService weekendService;

    public Collection<ScheduleDto> get(Month month, long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        if (optionalCompany.get().getEmployees().isEmpty()) throw new EmployeeNotFoundException();

        CompanyDto companyDto = Mapper.mapCompanyToDto(optionalCompany.get());
        Schedule schedule = new Schedule(month, companyDto);
        List<ScheduleDto> workSchedule = schedule.generate();
        weekendService.deleteWeekends(companyId);
        return workSchedule;
    }
}
