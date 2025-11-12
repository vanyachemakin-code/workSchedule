package Schedule.model;


import lombok.Data;

@Data
public class EmployeeModel {

    private Long id = System.currentTimeMillis();
    private String name;
    private Long companyId;
}
