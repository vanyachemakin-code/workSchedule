package Schedule.dto;

import lombok.Data;


@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private PrimaryWeekendDto primaryWeekends;
    private CompanyDto companyDto;
    private int shiftsInARow = 0;
    private int monthShifts;
}
