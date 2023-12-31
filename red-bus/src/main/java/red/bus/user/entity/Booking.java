package red.bus.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @Column(name = "booking_Id")
    private String bookingId;
    @Column(name = "bus_Id")
    private String busId;
    @Column(name = "ticket_Id")
    private String ticketId;
    @Column(name = "bus_Company")
    private String busCompany;
    @Column(name="bus_Number")
    private String busNumber;
    @Column(name = "starting_point")
    private String departureCity;
    @Column(name = "destination")
    private String arrivalCity;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "seat_number")
    private int seatNumber;
    private String email;
    private String mobile;
    private long cost;
    @Column(name="departure_date")
    private LocalDate departureDate;
    @Column(name="arrival_date")
    private LocalDate arrivalDate;
    @Column(name="departure_time")
    private LocalTime departureTime;
    @Column(name="arrival_time")
    private LocalTime arrivalTime;
}
