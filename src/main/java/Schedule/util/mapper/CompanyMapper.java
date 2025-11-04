package Schedule.util.mapper;

import Schedule.dto.CompanyDto;
import Schedule.entity.Company;
import Schedule.model.CompanyModel;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class CompanyMapper {

    private final ModelMapper mapper = new ModelMapper();

    public Company dtoToEntity (CompanyDto companyDto) {
        return mapper.map(companyDto, Company.class);
    }

    public CompanyDto entityToDto (Company company) {
        return mapper.map(company, CompanyDto.class);
    }

    public Company modelToEntity (CompanyModel companyModel) {
        return mapper.map(companyModel, Company.class);
    }
}
