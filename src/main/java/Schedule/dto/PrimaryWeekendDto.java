package Schedule.dto;

import lombok.Data;

@Data
public class PrimaryWeekendDto {

    private String[] date;

    public boolean isItWeekend(String day) {
        for (String dataStr: date) {
            if (dataStr.equals(day)) return true;
        }
        return false;
    }
}
