package dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    private String name;
    private List<PrimaryWeekendDto> primaryWeekendDtos;
    private CompanyDto companyDto;
    private int shiftsInARow = 0;
    private int monthShifts;
}
