package red.bus.operator.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.bus.operator.payload.BusOperatorDto;
import red.bus.operator.service.BusOperatorService;

@RestController
@RequestMapping("/api/bus-operator")
public class BusOperatorController {

    private final BusOperatorService busOperatorService;

    @Autowired
    public BusOperatorController(BusOperatorService busOperatorService) {
        this.busOperatorService = busOperatorService;
    }

    @PostMapping
    public ResponseEntity<BusOperatorDto> addBusOperator(@RequestBody BusOperatorDto busOperatorDTO) {
       BusOperatorDto createdBusOperatorDTO = busOperatorService.scheduleBus(busOperatorDTO);
        return new ResponseEntity<>(createdBusOperatorDTO, HttpStatus.CREATED);
    }

}
