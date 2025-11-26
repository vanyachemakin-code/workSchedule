package Schedule.service;

import Schedule.dto.CompanyDto;
import Schedule.dto.EmployeeDto;
import Schedule.dto.PrimaryWeekendDto;

import Schedule.exception.WeekendsNotFoundException;
import Schedule.repository.PrimaryWeekendRepository;
import Schedule.util.mapper.PrimaryWeekendMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrimaryWeekendService {

    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final PrimaryWeekendRepository weekendRepository;

    public void add(Long employeeId, PrimaryWeekendDto primaryWeekendDto) {
        log.info("Adding weekend for Employee with ID {}", employeeId);
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        primaryWeekendDto.setEmployeeDto(employeeDto);
        weekendRepository.save(PrimaryWeekendMapper.dtoToEntity(primaryWeekendDto));
        log.info("Add weekend complete");
    }

    public List<PrimaryWeekendDto> getWeekendsByEmployeeId(Long employeeId) {
        log.info("Search Weekends for Employee with ID {}", employeeId);
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        List<PrimaryWeekendDto> weekends = employeeDto.getPrimaryWeekendDtos();
        log.info("Weekends found");
        return weekends;
    }

    public void deleteById(Long id) {
        log.info("Deleting weekend by ID {}", id);
        if (weekendRepository.findById(id).isEmpty()) throw new WeekendsNotFoundException();
        weekendRepository.deleteById(id);
        log.info("Delete complete");
    }

    @Transactional
    public void deleteWeekends(Long employeeId) {
        log.info("Deleting weekends for Employee with ID {}", employeeId);
        EmployeeDto employeeDto = employeeService.getById(employeeId);
        if (employeeDto.getPrimaryWeekendDtos().isEmpty()) throw new WeekendsNotFoundException();
        weekendRepository.deleteAllByEmployeeId(employeeDto.getId());
        log.info("Delete complete");
    }

    public void deleteAllWeekendAfterGenerate(Long companyId) {
        log.info("Deleting all weekends after generation");
        CompanyDto companyDto = companyService.getById(companyId);
        for (EmployeeDto employeeDto: companyDto.getEmployees()) {
            deleteWeekends(employeeDto.getId());
        }
        log.info("Delete complete");
    }
}
