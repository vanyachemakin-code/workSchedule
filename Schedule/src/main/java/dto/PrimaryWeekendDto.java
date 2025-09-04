package dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrimaryWeekendDto {

    private LocalDate date;
    private List<EmployeeDto> employeeDtos;
}
