package Schedule.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyDto {

    private Long id;
    private String name;
    private int minEmployeePerDay;
    private int maxEmployeePerDay;
    private List<EmployeeDto> employees = List.of();
}
