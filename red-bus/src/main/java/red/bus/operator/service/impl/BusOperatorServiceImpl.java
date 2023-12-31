package red.bus.operator.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.entity.TicketCost;
import red.bus.operator.payload.BusOperatorDto;
import red.bus.operator.repository.BusOperatorRepository;
import red.bus.operator.repository.TicketCostRepository;
import red.bus.operator.service.BusOperatorService;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class BusOperatorServiceImpl implements BusOperatorService {

    private final BusOperatorRepository busOperatorRepository;
    private final ModelMapper modelMapper;
    private TicketCostRepository ticketCostRepository;
    @Autowired
    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository, ModelMapper modelMapper
    ,TicketCostRepository ticketCostRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.modelMapper = modelMapper;
        this.ticketCostRepository=ticketCostRepository;
    }

    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto) {
        String busId = UUID.randomUUID().toString();
        busOperatorDto.setBusId(busId);
        // Create TicketCost entity and set values from BusOperatorDto
        TicketCost ticketCost = new TicketCost();
        String ticketId = UUID.randomUUID().toString();
        ticketCost.setBusId(busId);
        ticketCost.setTicketId(ticketId);
        ticketCost.setBusNumber(busOperatorDto.getTicketCost().getBusNumber());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
//        // Adjust discountAmount based on code
//        double discountPercentage = 0.0;
//
//        if ("Firstbook".equalsIgnoreCase(busOperatorDto.getTicketCost().getCode())) {
//            // 25% less for "Firstbook"
//            discountPercentage = 0.25;
//        } else if ("SpecialDiscount".equalsIgnoreCase(busOperatorDto.getTicketCost().getCode())) {
//            // 50% less for "SpecialDiscount"
//            discountPercentage = 0.50;
//        }else{
//            discountPercentage=1;
//        }
//
//        // Calculate and set discountAmount
//        double originalCost = busOperatorDto.getTicketCost().getCost();
//        double discountAmount = originalCost * discountPercentage;
//        ticketCost.setDiscountAmount(discountAmount);

        // Set TicketCost entity in BusOperatorDto
        busOperatorDto.setTicketCost(ticketCost);
        ticketCostRepository.save(ticketCost);
        BusOperator busOperator = mapToEntity(busOperatorDto);
        BusOperator savedBusSchedule = busOperatorRepository.save(busOperator);
        return mapToDto(savedBusSchedule);
    }

    BusOperator mapToEntity(BusOperatorDto busOperatorDto) {
        BusOperator busOperator = modelMapper.map(busOperatorDto, BusOperator.class);
        busOperator.setDepartureCity(busOperatorDto.getDepartureCity().toUpperCase());
        busOperator.setArrivalCity(busOperatorDto.getArrivalCity().toUpperCase());
        // Set the TicketCost entity in the BusOperator entity
        busOperator.setTicketCost(busOperatorDto.getTicketCost());
        busOperatorRepository.save(busOperator);
        return busOperator;
    }

    BusOperatorDto mapToDto(BusOperator busOperator) {
        BusOperatorDto busOperatorDto = modelMapper.map(busOperator, BusOperatorDto.class);
        return busOperatorDto;
    }
}

