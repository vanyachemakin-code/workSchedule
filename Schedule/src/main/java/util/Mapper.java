package util;

import dto.CompanyDto;
import dto.EmployeeDto;
import dto.PrimaryWeekendDto;
import entity.Company;
import entity.Employee;
import entity.PrimaryWeekend;
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

    public static PrimaryWeekendDto mapWeekendToDto(PrimaryWeekend primaryWeekend) {
        return MAPPER.map(primaryWeekend, PrimaryWeekendDto.class);
    }

    public static PrimaryWeekend mapWeekendDtoToEntity(PrimaryWeekendDto primaryWeekendDto) {
        return MAPPER.map(primaryWeekendDto, PrimaryWeekend.class);
    }
}
