package red.bus.user.controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.bus.operator.payload.BusOperatorDto;
import red.bus.operator.service.BusOperatorService;
import red.bus.user.service.SearchBusesService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class FindBusesController {

  SearchBusesService searchBusesService;
    public FindBusesController(SearchBusesService searchBusesService,BusOperatorService busOperatorService) {

        this.searchBusesService = searchBusesService;
    }

    //http://localhost:8080/api/user/searchBuses?departureCity=CityA&arrivalCity=CityB&departureDate=2023-01-01
    @GetMapping("/searchBuses")
    public List<BusOperatorDto> searchBuses(@RequestParam String departureCity,
                                            @RequestParam String arrivalCity,
                                            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy")  LocalDate departureDate){
        List<BusOperatorDto> busOperatorDtos = searchBusesService.searchBusBy(departureCity, arrivalCity, departureDate);
        return  busOperatorDtos;
    }


}

