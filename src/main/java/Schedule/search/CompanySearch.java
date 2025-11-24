package Schedule.search;

import Schedule.dto.CompanyDto;
import Schedule.exception.EmployeeNotFoundException;
import Schedule.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CompanySearch {

    private final CompanyService companyService;

    public CompanyDto search(String value) {
        log.info("Search Company...");
        if (isId(value)) {
            log.info("by id");
            return companyService.getById(Long.parseLong(value));
        }
        if (isName(value)) {
            log.info("by name");
            return companyService.getAll()
                    .stream()
                    .filter(employeeDto -> employeeDto.getName().equals(value))
                    .findFirst()
                    .orElseThrow(EmployeeNotFoundException::new);
        }
        log.info("Company not found");
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
