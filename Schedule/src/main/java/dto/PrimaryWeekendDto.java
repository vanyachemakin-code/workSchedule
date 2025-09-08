package dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PrimaryWeekendDto {

    private LocalDate date;
    private EmployeeDto employeeDto;
}
