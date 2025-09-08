package util;

import dto.CompanyDto;
import dto.EmployeeDto;
import dto.PrimaryWeekendDto;
import dto.ScheduleDto;
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
                        / companyDto.getEmployeeDtos().size()
        );
    }

    private void setEmployeeMonthShifts() {
        Collection<EmployeeDto> employees = companyDto.getEmployeeDtos();
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
        Collection<EmployeeDto> employees = companyDto.getEmployeeDtos();
        Set<LocalDate> days = employeeCountPerDay.keySet();
        List<ScheduleDto> schedule = new ArrayList<>();

        point:
        for (LocalDate day: days) {
            List<String> employeePerDay = new ArrayList<>();
            for (EmployeeDto employee: employees) {
                employee.setShiftsInARow(employee.getShiftsInARow() + 1);
                employee.setMonthShifts(employee.getMonthShifts() - 1);

                if (canWork(employee, day)) employeePerDay.add(employee.getName());
                if (employee.getShiftsInARow() == 4) employee.setShiftsInARow(0);

                if (employeePerDay.size() == employeeCountPerDay.get(day)) {
                    schedule.add(
                            new ScheduleDto(day.format(DateTimeFormatter.ofPattern("dd/MM")), employeePerDay)
                    );
                    continue point;
                }
            }
        }
        return schedule;
    }

    private boolean canWork(EmployeeDto employee, LocalDate day) {
        return employee.getShiftsInARow() < 3 &&
                employee.getMonthShifts() != 0 &&
                employee.getPrimaryWeekendDtos()
                        .stream()
                        .map(PrimaryWeekendDto::getDate)
                        .anyMatch(date -> date.equals(day));
    }
}
