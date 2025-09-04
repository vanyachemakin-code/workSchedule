package dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private String name;
    private int standardEmployeePerDay;
    private List<EmployeeDto> employeeDtos;
}
