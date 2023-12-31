package red.bus.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.payload.BusOperatorDto;
import red.bus.operator.repository.BusOperatorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchBusesService {
    private BusOperatorRepository busOperatorRepository;

    private final ModelMapper modelMapper;

    public SearchBusesService(BusOperatorRepository busOperatorRepository,ModelMapper modelMapper) {
        this.busOperatorRepository = busOperatorRepository;
        this.modelMapper = modelMapper;
    }

    public List<BusOperatorDto> searchBusBy(String departureCity, String arrivalCity, LocalDate departureDate){
        List<BusOperator> busAvailable = busOperatorRepository.findByDepartureCityAndArrivalCityAndDepartureDate(departureCity, arrivalCity, departureDate);
        List<BusOperatorDto> availableBuses = busAvailable.stream().map(busOperator -> mapToDto(busOperator)).collect(Collectors.toList());
     return  availableBuses;
    }


    BusOperator mapToEntity(BusOperatorDto busOperatorDto){
        BusOperator busOperator = modelMapper.map(busOperatorDto, BusOperator.class);
        return busOperator;
    }
    BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto = modelMapper.map(busOperator, BusOperatorDto.class);
        return busOperatorDto;
    }

}
