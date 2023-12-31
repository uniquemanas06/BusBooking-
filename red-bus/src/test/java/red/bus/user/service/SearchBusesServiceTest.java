package red.bus.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.payload.BusOperatorDto;
import red.bus.operator.repository.BusOperatorRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SearchBusesServiceTest {

    @Mock
    private BusOperatorRepository busOperatorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SearchBusesService searchBusesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSearchBusBy() {
        // Mock data
        String departureCity = "CityA";
        String arrivalCity = "CityB";
        LocalDate departureDate = LocalDate.now();
        List<BusOperator> mockBusOperators = Arrays.asList(new BusOperator(), new BusOperator());
        List<BusOperatorDto> mockBusOperatorDtos = Arrays.asList(new BusOperatorDto(), new BusOperatorDto());

        // Mock behavior of dependencies
        when(busOperatorRepository.findByDepartureCityAndArrivalCityAndDepartureDate(departureCity, arrivalCity, departureDate))
                .thenReturn(mockBusOperators);
        when(modelMapper.map(mockBusOperators.get(0), BusOperatorDto.class)).thenReturn(mockBusOperatorDtos.get(0));
        when(modelMapper.map(mockBusOperators.get(1), BusOperatorDto.class)).thenReturn(mockBusOperatorDtos.get(1));

        // Call the method to be tested
        List<BusOperatorDto> result = searchBusesService.searchBusBy(departureCity, arrivalCity, departureDate);

        // Assertions
        assertEquals(2, result.size()); // Adjust the expected size based on your mock data
        // Add more assertions as needed
    }
}
