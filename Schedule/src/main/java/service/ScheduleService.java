package service;

import dto.CompanyDto;
import org.springframework.stereotype.Service;
import util.Schedule;

import java.time.Month;

@Service
public class ScheduleService {

    public void get(Month month, CompanyDto companyDto) {
        Schedule schedule = new Schedule(month, companyDto);
        schedule.generate();
    }
}
