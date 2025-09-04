package dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrimaryWeekendDto {

    private final LocalDate date;
    private final List<EmployeeDto> employeeDtos;
}
