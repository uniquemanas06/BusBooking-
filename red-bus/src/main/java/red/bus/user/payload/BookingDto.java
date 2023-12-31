package red.bus.user.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private String bookingId;
    private String busId;
    private String ticketId;
    private String busCompany;
    private String busNumber;
    private String departureCity;
    private String arrivalCity;
    private String firstName;
    private String lastName;
    private int seatNumber;
    private String email;
    private String mobile;
    private long cost;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String message;
}
