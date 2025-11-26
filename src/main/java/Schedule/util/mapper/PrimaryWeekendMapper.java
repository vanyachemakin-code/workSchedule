package Schedule.util.mapper;

import Schedule.dto.PrimaryWeekendDto;
import Schedule.entity.PrimaryWeekend;
import Schedule.model.PrimaryWeekendModel;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class PrimaryWeekendMapper {

    private final ModelMapper mapper = new ModelMapper();

    public PrimaryWeekend dtoToEntity (PrimaryWeekendDto primaryWeekendDto) {
        return mapper.map(primaryWeekendDto, PrimaryWeekend.class);
    }

    public PrimaryWeekendDto entityToDto (PrimaryWeekend primaryWeekend) {
        return mapper.map(primaryWeekend, PrimaryWeekendDto.class);
    }

    public PrimaryWeekendDto modelToDto(PrimaryWeekendModel primaryWeekendModel) {
        return mapper.map(primaryWeekendModel, PrimaryWeekendDto.class);
    }

}
