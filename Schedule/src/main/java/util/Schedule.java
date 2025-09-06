package util;

import dto.CompanyDto;
import dto.EmployeeDto;
import dto.ScheduleDto;
import lombok.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class Schedule {

    private FileWriter writer;
    private final Map<LocalDate, Integer> employeeCountPerDay = new HashMap<>();
    private final Month month;
    private final CompanyDto companyDto;

    public Schedule(Month month, CompanyDto companyDto) {
        this.month = month;
        this.companyDto = companyDto;
        try {
            writer = new FileWriter(
                    "/Users/vanyachemakin/IdeaProjects/workSchedule/Schedule/schedule/schedule.txt"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getEmployeeMonthShifts() {
        return Math.round((float) HolidayChecker.getDaysInMonth(month) / companyDto.getEmployeeDtos().size());
    }

    private void setEmployeeMonthShifts() {
        Collection<EmployeeDto> employees = companyDto.getEmployeeDtos();
        for (EmployeeDto employee: employees) employee.setMonthShifts(getEmployeeMonthShifts());
    }

    private void setEmployeeCountPerDay() {
        for (LocalDate day : HolidayChecker.getDays(month)) {
            if (HolidayChecker.isWeekend(day)) employeeCountPerDay.put(day, companyDto.getStandardEmployeePerDay() + 1);
            else employeeCountPerDay.put(day, companyDto.getStandardEmployeePerDay());
        }
    }

    private void scheduleWriter(String dayShifts) {
        try {
            writer.write(dayShifts);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //доработать
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
                if (employee.getShiftsInARow() < 3 && employee.getMonthShifts() != 0) {
                    employeePerDay.add(employee.getName());
                }
                if (employeePerDay.size() == employeeCountPerDay.get(day)) {
                    schedule.add(new ScheduleDto(day.format(DateTimeFormatter.ofPattern("dd/MM")), employeePerDay));
                    continue point;
                }
                if (employee.getShiftsInARow() == 4) employee.setShiftsInARow(0);
            }
        }
        return schedule;
    }
}
