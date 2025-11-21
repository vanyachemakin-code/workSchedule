package Schedule.util.mapper;

import Schedule.dto.EmployeeDto;
import Schedule.entity.Employee;
import Schedule.model.EmployeeModel;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class EmployeeMapper {

    private final ModelMapper mapper = new ModelMapper();

    public Employee dtoToEntity (EmployeeDto employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }

    public EmployeeDto entityToDto (Employee employee) {
        mapper.typeMap(Employee.class, EmployeeDto.class)
                .addMappings(m ->
                        m.map(Employee::getCompany, EmployeeDto::setCompanyDto)
                );
        return mapper.map(employee, EmployeeDto.class);
    }

    public EmployeeDto modelToDto (EmployeeModel employeeModel) {
        return mapper.map(employeeModel, EmployeeDto.class);
    }

    public EmployeeModel dtoToModel(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, EmployeeModel.class);
    }
}
