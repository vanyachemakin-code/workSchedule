package Schedule.service;

import Schedule.search.CompanySearch;
import Schedule.search.EmployeeSearch;
import Schedule.search.SearchMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final EmployeeSearch employeeSearch;
    private final CompanySearch companySearch;

    public Object search(String value, SearchMode mode) {
        if (mode.equals(SearchMode.COMPANY)) return companySearch.search(value);
        if (mode.equals(SearchMode.EMPLOYEE)) return employeeSearch.search(value);
        return null;
    }
}
