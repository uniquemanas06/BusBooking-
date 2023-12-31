package red.bus.operator.service;

import org.springframework.web.bind.annotation.RequestBody;
import red.bus.operator.payload.BusOperatorDto;

public interface BusOperatorService {
    BusOperatorDto scheduleBus(@RequestBody BusOperatorDto busOperatorDto);
}
