
//ctrl+shift+t(for crate test class)
package red.bus.user.service;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import red.bus.operator.entity.BusOperator;
import red.bus.operator.entity.TicketCost;
import red.bus.user.entity.Booking;
import red.bus.user.payload.BookingDto;
import red.bus.user.payload.PassengerDetails;
import red.bus.operator.repository.BusOperatorRepository;
import red.bus.operator.repository.TicketCostRepository;
import red.bus.user.repository.BookingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @Mock
    private BusOperatorRepository busOperatorRepository;

    @Mock
    private TicketCostRepository ticketCostRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks // we can use also @Autowired
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    //@AfterEach
//    void tearDown(){
//        System.out.println("tearing down");
//        bookingRepository.deleteAll();
//    }
    @Test
    void testCreateBooking() throws StripeException {
        // Mock data
        String busId = "bus123";
        String ticketId = "ticket123";
        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setFirstName("John");
        passengerDetails.setLastName("Doe");
        passengerDetails.setEmail("john.doe@example.com");
        passengerDetails.setMobile("1234567890");

        BusOperator mockBusOperator = new BusOperator();
        mockBusOperator.setDepartureCity("CityA");
        mockBusOperator.setArrivalCity("CityB");
        mockBusOperator.setBusOperatorCompanyName("XYZ Bus Company");
        mockBusOperator.setBusNumber("BUS123");
        mockBusOperator.setDepartureDate(LocalDate.now());
        mockBusOperator.setArrivalDate(LocalDate.now().plusDays(1));
        mockBusOperator.setDepartureTime(LocalTime.parse("12:00"));
        mockBusOperator.setArrivalTime(LocalTime.parse("15:00"));

        TicketCost mockTicketCost = new TicketCost();
        mockTicketCost.setCost(50);

        // Mock behavior of the repositories
        when(busOperatorRepository.findById(busId)).thenReturn(Optional.of(mockBusOperator));
        when(ticketCostRepository.findById(ticketId)).thenReturn(Optional.of(mockTicketCost));
        when(ticketCostRepository.findByBusId(busId)).thenReturn(mockTicketCost);

        // Mock behavior of the booking repository save method
        when(bookingRepository.save(Mockito.any(Booking.class)))
                .thenAnswer(invocation -> {
                    Booking booking = invocation.getArgument(0);
                    booking.setBookingId(UUID.randomUUID().toString());
                    return booking;
                });

        // Call the method to be tested
        BookingDto result = bookingService.createBooking(busId, ticketId, passengerDetails);

        // Assertions
        assertEquals(mockBusOperator.getDepartureCity(), result.getDepartureCity());
        assertEquals(mockBusOperator.getArrivalCity(), result.getArrivalCity());
        assertEquals(mockBusOperator.getBusOperatorCompanyName(), result.getBusCompany());
        assertEquals(mockBusOperator.getBusNumber(), result.getBusNumber());
        assertEquals(mockTicketCost.getCost(), result.getCost());
        assertEquals(passengerDetails.getFirstName(), result.getFirstName());
        assertEquals(passengerDetails.getLastName(), result.getLastName());
        assertEquals(passengerDetails.getEmail(), result.getEmail());
        assertEquals(passengerDetails.getMobile(), result.getMobile());
        assertEquals(mockBusOperator.getDepartureDate(), result.getDepartureDate());
        assertEquals(mockBusOperator.getArrivalDate(), result.getArrivalDate());
        assertEquals(mockBusOperator.getDepartureTime(), result.getDepartureTime());
        assertEquals(mockBusOperator.getArrivalTime(), result.getArrivalTime());
        // Add more assertions as needed
    }
}
