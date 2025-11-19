package Schedule.service.employeeHelperService;

import Schedule.dto.EmployeeDto;
import Schedule.entity.Employee;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.repository.EmployeeRepository;
import Schedule.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmployeeServiceGetById {

    private final EmployeeRepository employeeRepository;

    public EmployeeDto getById(Long id) {
        log.info("Search Employee with ID {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();
        log.info("Employee found");
        return EmployeeMapper.entityToDto(optionalEmployee.get());
    }
}
