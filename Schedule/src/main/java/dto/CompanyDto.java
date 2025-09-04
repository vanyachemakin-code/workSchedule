package dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private final String name;
    private int standardEmployeePerDay;
    private final List<EmployeeDto> employeeDtos;
}
