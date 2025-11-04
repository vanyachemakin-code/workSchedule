package Schedule.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

@UtilityClass
public class HolidayChecker {

    private static final Set<LocalDate> HOLIDAY = new HashSet<>(Arrays.asList(
            LocalDate.of(LocalDate.now().getYear(), 1, 1),
            LocalDate.of(LocalDate.now().getYear(), 1, 2),
            LocalDate.of(LocalDate.now().getYear(), 1, 3),
            LocalDate.of(LocalDate.now().getYear(), 1, 4),
            LocalDate.of(LocalDate.now().getYear(), 1, 5),
            LocalDate.of(LocalDate.now().getYear(), 1, 6),
            LocalDate.of(LocalDate.now().getYear(), 1, 7),
            LocalDate.of(LocalDate.now().getYear(), 1, 8),
            LocalDate.of(LocalDate.now().getYear(), 2, 23),
            LocalDate.of(LocalDate.now().getYear(), 3, 8),
            LocalDate.of(LocalDate.now().getYear(), 5, 1),
            LocalDate.of(LocalDate.now().getYear(), 5, 9),
            LocalDate.of(LocalDate.now().getYear(), 6, 12),
            LocalDate.of(LocalDate.now().getYear(), 9, 1),
            LocalDate.of(LocalDate.now().getYear(), 11, 4),
            LocalDate.of(LocalDate.now().getYear(), 12, 31)
    ));

    public boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().getValue() > 4
                | HOLIDAY.contains(date)
                | (date.getMonth().getValue() > 5 && date.getMonth().getValue() < 9);
    }

    public boolean isLeap() {
        return Year.now().isLeap();
    }

    public List<LocalDate> getDays(Month month) {
        List<LocalDate> days = new ArrayList<>();

        for (int day = 1; day <= month.length(isLeap()); day++) {
            days.add(LocalDate.of(LocalDate.now().getYear(), month, day));
        }
        return days;
    }

    public int getDaysInMonth(Month month) {
        return month.length(isLeap());
    }
}
