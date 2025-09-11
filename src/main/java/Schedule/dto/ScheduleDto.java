package Schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ScheduleDto {

    private String date;
    private List<String> employees;
}
