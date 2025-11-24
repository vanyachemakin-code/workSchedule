package Schedule.search;

import Schedule.dto.EmployeeDto;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class EmployeeSearch {

    private final EmployeeService employeeService;

    public EmployeeDto search(String value) {
        log.info("Search Employee...");
        if (isId(value)) {
            log.info("by id");
            return employeeService.getById(Long.parseLong(value));
        }
        if (isName(value)) {
            log.info("by name");
            return employeeService.getAll()
                    .stream()
                    .filter(employeeDto -> employeeDto.getName().equals(value))
                    .findFirst()
                    .orElseThrow(EmployeeNotFoundException::new);
        }
        log.info("Employee not found");
        return null;
    }

    private boolean isId(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isName(String value) {
        String[] words = value.trim().split("//s+");
        for (String word: words) {
            return word.chars().allMatch(Character::isLetter);
        }
        return false;
    }
}
