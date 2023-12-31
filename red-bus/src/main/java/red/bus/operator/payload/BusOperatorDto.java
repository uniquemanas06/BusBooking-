package red.bus.operator.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.entity.TicketCost;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorDto {
  BusOperator busOperator = new BusOperator();
    private String busId;

    private String busNumber;

    private String busOperatorCompanyName;
    private String driverName;

    private String supportStaff;

    private int numberOfSeats;

    private String departureCity;

    private String arrivalCity;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;

    @JsonFormat(pattern= "dd/MM/yyyy")
    private LocalDate departureDate;

    @JsonFormat(pattern= "dd/MM/yyyy")
    private LocalDate arrivalDate;

    private String totalTravelTime;
    private String busType;
    private String amenities;

    private TicketCost ticketCost;
}