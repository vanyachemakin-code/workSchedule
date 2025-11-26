package Schedule.dto;

import lombok.Data;

import java.util.List;


@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private CompanyDto companyDto;
    private List<PrimaryWeekendDto> primaryWeekendDtos;
    private int shiftsInARow = 0;
    private int monthShifts;
}
