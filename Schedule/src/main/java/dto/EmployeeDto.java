package dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    private final String name;
    private final List<PrimaryWeekendDto> primaryWeekendDtos;
    private final CompanyDto companyDto;
}
