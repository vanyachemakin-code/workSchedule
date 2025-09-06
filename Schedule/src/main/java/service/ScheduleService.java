package service;

import dto.CompanyDto;
import dto.ScheduleDto;
import entity.Company;
import exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.CompanyRepository;
import util.Mapper;
import util.Schedule;

import java.time.Month;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final CompanyRepository companyRepository;

    public Collection<ScheduleDto> get(Month month, long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();

        CompanyDto companyDto = Mapper.mapCompanyToDto(optionalCompany.get());

        Schedule schedule = new Schedule(month, companyDto);
        return schedule.generate();
    }
}
