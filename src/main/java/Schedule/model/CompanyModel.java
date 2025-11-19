package Schedule.model;

import lombok.Data;

@Data
public class CompanyModel {

    private Long id;
    private String name;
    private int minEmployeePerDay;
    private int maxEmployeePerDay;
}
