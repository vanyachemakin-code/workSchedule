package Schedule.controller;

import Schedule.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import Schedule.service.ScheduleService;

import java.time.Month;
import java.util.Collection;

@Controller
@RequestMapping("/work_schedule/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    //Настроить взаимодействие с HTML

    @GetMapping("/company/{id}")
    private Collection<ScheduleDto> get(@RequestBody String monthStr, @PathVariable Long companyId) {
        Month month = Month.valueOf(monthStr);
        return service.get(month, companyId);
    }
}
