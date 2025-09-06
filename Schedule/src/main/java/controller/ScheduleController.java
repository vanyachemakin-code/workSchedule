package controller;

import dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.ScheduleService;

import java.time.Month;
import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/company/{id}")
    private Collection<ScheduleDto> get(@RequestBody String monthStr, @PathVariable long companyId) {
        Month month = Month.valueOf(monthStr);
        return service.get(month, companyId);
    }
}
