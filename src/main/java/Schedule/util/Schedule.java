package Schedule.util;

import Schedule.dto.CompanyDto;
import Schedule.dto.ScheduleDto;
import Schedule.dto.employee.EmployeeDto;
import lombok.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class Schedule {

    private final Map<LocalDate, Integer> employeeCountPerDay = new HashMap<>();
    private final Month month;
    private final CompanyDto companyDto;

    private int getEmployeeMonthShifts() {
        return Math.round(
                (float) (HolidayChecker.getDaysInMonth(month) * companyDto.getMaxEmployeePerDay())
                        / companyDto.getEmployees().size()
        );
    }

    private void setEmployeeMonthShifts() {
        Collection<EmployeeDto> employees = companyDto.getEmployees();
        for (EmployeeDto employee: employees) employee.setMonthShifts(getEmployeeMonthShifts());
    }

    private void setEmployeeCountPerDay() {
        for (LocalDate day : HolidayChecker.getDays(month)) {
            if (HolidayChecker.isWeekend(day)) employeeCountPerDay.put(day, companyDto.getMaxEmployeePerDay());
            else employeeCountPerDay.put(day, companyDto.getMinEmployeePerDay());
        }
    }

    public List<ScheduleDto> generate() {
        setEmployeeMonthShifts();
        setEmployeeCountPerDay();
        Collection<EmployeeDto> employees = companyDto.getEmployees();
        Set<LocalDate> days = employeeCountPerDay.keySet();
        List<ScheduleDto> schedule = new ArrayList<>();

        point:
        for (LocalDate day: days) {
            String dayStr = day.format(DateTimeFormatter.ofPattern("dd/MM"));
            List<String> employeePerDay = new ArrayList<>();
            for (EmployeeDto employee: employees) {
                employee.setShiftsInARow(employee.getShiftsInARow() + 1);
                employee.setMonthShifts(employee.getMonthShifts() - 1);

                if (canWork(employee, dayStr)) employeePerDay.add(employee.getName());
                if (employee.getShiftsInARow() == 4) employee.setShiftsInARow(0);

                if (employeePerDay.size() == employeeCountPerDay.get(day)) {
                    schedule.add(
                            new ScheduleDto(dayStr, employeePerDay)
                    );
                    continue point;
                }
            }
        }
        return schedule;
    }

    private boolean canWork(EmployeeDto employee, String day) {
        return employee.getShiftsInARow() < 3 &&
                employee.getMonthShifts() != 0 &&
                !employee.getPrimaryWeekends().isItWeekend(day);
    }
}
