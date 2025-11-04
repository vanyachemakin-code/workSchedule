package Schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrimaryWeekendDto {

    private Long id;
    private EmployeeDto employeeDto;
    private List<String> date;

    public boolean isItWeekend(String day) {
        for (String dataStr: date) {
            if (dataStr.equals(day)) return true;
        }
        return false;
    }
}
