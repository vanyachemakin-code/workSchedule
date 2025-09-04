package util;

import dto.CompanyDto;
import dto.EmployeeDto;
import lombok.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class Schedule {

    private FileWriter writer;
    private final Map<LocalDate, Integer> employeeCountPerDay = new HashMap<>();
    private final Month month;
    private final CompanyDto companyDto;

    public Schedule(Month month, CompanyDto companyDto) {
        this.month = month;
        this.companyDto = companyDto;
        setEmployeeCountPerDay();
        try {
            writer = new FileWriter(
                    "/Users/vanyachemakin/IdeaProjects/workSchedule/Schedule/schedule/schedule.txt"
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getEmployeeShifts() {
        return Math.round((float) HolidayChecker.getDaysInMonth(month) / companyDto.getEmployeeDtos().size());
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

    //нужно реализовать
    public void generate() {
        Collection<EmployeeDto> employees = companyDto.getEmployeeDtos();
        Set<LocalDate> days = employeeCountPerDay.keySet();
        int shiftsInARow = 0;
        int employeeShifts = getEmployeeShifts();
        point:
        for (LocalDate day: days) {
            StringBuilder dayShifts = new StringBuilder(employeeCountPerDay.get(day));
            for (EmployeeDto employee: employees) {
                shiftsInARow++;
                employeeShifts--;
                if (shiftsInARow < 3 && employeeShifts != 0) {
                    dayShifts.append(day)
                            .append(": ")
                            .append(employee.getName())
                            .append("/");
                }
                if (dayShifts.length() == employeeCountPerDay.get(day)) {
                    dayShifts.append("\n");
                    scheduleWriter(dayShifts.toString());
                    continue point;
                }
            }
        }
    }
}
