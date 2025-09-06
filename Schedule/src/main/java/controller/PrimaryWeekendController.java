package controller;

import dto.PrimaryWeekendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.PrimaryWeekendService;

import java.util.Collection;

@RestController
@RequestMapping("/work_schedule/primary_weekend")
@RequiredArgsConstructor
public class PrimaryWeekendController {

    private final PrimaryWeekendService service;

    @PostMapping("/employee/{id}")
    private void add(@PathVariable long employeeId, @RequestBody PrimaryWeekendDto primaryWeekendDto) {
        service.add(employeeId, primaryWeekendDto);
    }

    @PostMapping("/employee/{id}")
    private void addMoreThanOne(@PathVariable long employeeId, @RequestBody Collection<PrimaryWeekendDto> primaryWeekendDtos) {
        service.addMoreThanOne(employeeId, primaryWeekendDtos);
    }
}
