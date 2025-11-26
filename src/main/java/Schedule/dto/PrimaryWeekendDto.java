package Schedule.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PrimaryWeekendDto {

    private Long id;
    private EmployeeDto employeeDto;
    private String date;
    private EmployeeDto employeeDto;

    public boolean isItWeekend(String day) {
        return date.equals(day);
    }
}
