package Schedule.util;

import Schedule.dto.CompanyDto;
import Schedule.dto.employee.EmployeeDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;
import org.modelmapper.ModelMapper;

public class Mapper {

    private static final ModelMapper MAPPER = new ModelMapper();

    public static CompanyDto mapCompanyToDto(Company company) {
        return MAPPER.map(company, CompanyDto.class);
    }

    public static Company mapCompanyDtoToEntity(CompanyDto companyDto) {
        return MAPPER.map(companyDto, Company.class);
    }

    public static EmployeeDto mapEmployeeToDto(Employee employee) {
        return MAPPER.map(employee, EmployeeDto.class);
    }

    public static Employee mapEmployeeDtoToEntity(EmployeeDto employeeDto) {
        return MAPPER.map(employeeDto, Employee.class);
    }
}
