package Schedule.service;

import Schedule.dto.PrimaryWeekendDto;
import Schedule.entity.Company;
import Schedule.entity.Employee;
import Schedule.entity.PrimaryWeekend;
import Schedule.exception.CompanyNotFoundException;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.repository.CompanyRepository;
import Schedule.repository.PrimaryWeekendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import Schedule.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrimaryWeekendService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final PrimaryWeekendRepository weekendRepository;

    public void add(long employeeId, PrimaryWeekendDto primaryWeekendDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) throw new EmployeeNotFoundException();

        List<PrimaryWeekend> primaryWeekends = new ArrayList<>();
        for (String weekend: primaryWeekendDto.getDate()) {
            PrimaryWeekend primaryWeekend = new PrimaryWeekend();
            primaryWeekend.setDate(weekend);
            primaryWeekend.setEmployee(optionalEmployee.get());
            primaryWeekends.add(primaryWeekend);
            weekendRepository.save(primaryWeekend);
        }
        Employee employee = optionalEmployee.get();
        employee.setPrimaryWeekends(primaryWeekends);
        employeeRepository.save(employee);
    }

    public void deleteWeekends(long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isEmpty()) throw new CompanyNotFoundException();
        if (optionalCompany.get().getEmployees().isEmpty()) throw new EmployeeNotFoundException();

        List<Employee> employees = optionalCompany.get().getEmployees();
        for (Employee employee: employees) {
            employee.setPrimaryWeekends(List.of());
            employeeRepository.save(employee);

            List<PrimaryWeekend> primaryWeekends = employee.getPrimaryWeekends();
            for (PrimaryWeekend weekend: primaryWeekends) weekendRepository.delete(weekend);
        }
    }
}
