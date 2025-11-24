package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.util.Schedule;

import java.time.Month;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CompanyService companyService;
    private final PrimaryWeekendService weekendService;

    public Collection<ScheduleDto> get(Month month, Long companyId) {
        CompanyDto companyDto = companyService.getById(companyId);
        weekendService.deleteAllWeekendAfterGenerate(companyId);
        return new Schedule(month, companyDto).generate();
    }
}
