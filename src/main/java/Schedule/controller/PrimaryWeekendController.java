package Schedule.controller;

import Schedule.dto.PrimaryWeekendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import Schedule.service.PrimaryWeekendService;


@RestController
@RequestMapping("/work_schedule/primary_weekend")
@RequiredArgsConstructor
public class PrimaryWeekendController {

    private final PrimaryWeekendService service;

    //Изменить входные данный на модели и настроить из взаимодействие с HTML

    @PostMapping("/employee/{id}")
    private void add(@PathVariable Long employeeId, @RequestBody PrimaryWeekendDto primaryWeekendDto) {
        service.add(employeeId, primaryWeekendDto);
    }
}
