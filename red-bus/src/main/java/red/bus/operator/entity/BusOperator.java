package red.bus.operator.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bus_operators")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperator {
    @Id
    @Column(name="bus_id")
    private String busId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="bus_id") //Refer to the column in the TicketCost table
    private TicketCost ticketCost;
    @Column(name="bus_number")
    private String busNumber;
    @Column(name="bus_operator_company_name")
    private String busOperatorCompanyName;
    @Column(name="bus_diver_name")
    private String driverName;
    @Column(name="support_staff")
    private String supportStaff;
    @Column(name="number_of_seats")
    private int numberOfSeats;
    @Column(name="departure_city")
    private String departureCity;
    @Column(name="arrival_city")
    private String arrivalCity;
    @Column(name="departure_time")
    private LocalTime departureTime;
    @Column(name="arrival_time")
    private LocalTime arrivalTime;
    @Column(name="departure_date")
    private LocalDate departureDate;
    @Column(name="arrival_date")
    private LocalDate arrivalDate;
    @Column(name="total_travel_time")
    private String totalTravelTime;
    @Column(name="bus_type")
    private String busType;
    @Column(name="amenities")
    private String amenities;
}
