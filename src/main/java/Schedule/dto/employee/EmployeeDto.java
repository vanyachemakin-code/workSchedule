package Schedule.dto.employee;

import Schedule.dto.CompanyDto;
import Schedule.dto.PrimaryWeekendDto;
import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDto {

    private String name;
    private PrimaryWeekendDto primaryWeekends;
    private CompanyDto companyDto;
    private int shiftsInARow = 0;
    private int monthShifts;
}
