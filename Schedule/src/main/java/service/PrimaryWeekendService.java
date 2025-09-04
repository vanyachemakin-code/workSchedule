package service;

import dto.PrimaryWeekendDto;
import entity.Employee;
import exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;
import util.Mapper;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrimaryWeekendService {

    private final EmployeeRepository employeeRepository;

    public void add(long employeeId, PrimaryWeekendDto primaryWeekendDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        Employee employee = optionalEmployee.get();
        employee.getWeekends().add(Mapper.mapWeekendDtoToEntity(primaryWeekendDto));
        employeeRepository.save(employee);
    }

    public void addMoreThanOne(long employeeId, Collection<PrimaryWeekendDto> primaryWeekendDtos) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        Employee employee = optionalEmployee.get();
        for (PrimaryWeekendDto primaryWeekendDto: primaryWeekendDtos) {
            employee.getWeekends().add(Mapper.mapWeekendDtoToEntity(primaryWeekendDto));
        }
        employeeRepository.save(employee);
    }
}
